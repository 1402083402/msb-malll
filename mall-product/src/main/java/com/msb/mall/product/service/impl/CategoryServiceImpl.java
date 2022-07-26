package com.msb.mall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.mall.product.entity.BrandEntity;
import com.msb.mall.product.service.CategoryBrandRelationService;
import com.msb.mall.product.vo.Catalog2VO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.common.utils.PageUtils;
import com.msb.common.utils.Query;

import com.msb.mall.product.dao.CategoryDao;
import com.msb.mall.product.entity.CategoryEntity;
import com.msb.mall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }


    /**
     * 查询所有的类别数据，然后将数据封装为树形结构，便于前端使用
     *
     * @param params
     * @return
     */
    @Override
    public List<CategoryEntity> queryPageWithTree(Map<String, Object> params) {
        // 1.查询所有的商品分类信息
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        // 2.将商品分类信息拆解为树形结构【父子关系】
        // 第一步遍历出所有的大类  parent_cid = 0
        List<CategoryEntity> list = categoryEntities.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .map(categoryEntity -> {
                    // 根据大类找到多有的小类  递归的方式实现
                    categoryEntity.setChildren(getCategoryChildren(categoryEntity, categoryEntities));
                    return categoryEntity;
                }).sorted((entity1, entity2) -> {
                    return (entity1.getSort() == null ? 0 : entity1.getSort()) - (entity2.getSort() == null ? 0 : entity2.getSort());
                }).collect(Collectors.toList());
        // 第二步根据大类找到对应的所有的小类
        return list;
    }

    @Override
    public void removeCategoryByIds(List<Long> ids) {
        baseMapper.deleteBatchIds(ids);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);
        Collections.reverse(parentPath);
        return parentPath.toArray(new Long[parentPath.size()]);
    }

    @Transactional
    @Override
    public void updateDetail(CategoryEntity entity) {
        this.updateById(entity);
        if (!StringUtils.isEmpty(entity.getName())) {
            //同步更新级联数据
            categoryBrandRelationService.updateCatelogName(entity.getCatId(), entity.getName());
            //TODO 同步更新其它冗余数据
        }
    }

    /**
     * 查询所有商品的大类（一级分类）
     *
     * @return
     */
    @Override
    public List<CategoryEntity> getLeve1Category() {
        List<CategoryEntity> list = baseMapper.queryLeve1Category();
        return list;
    }


    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        paths.add(catelogId);
        CategoryEntity entity = this.getById(catelogId);
        if (entity.getParentCid() != 0) {
            findParentPath(entity.getParentCid(), paths);
        }
        return paths;
    }

    /**
     * 查找该大类下的所有的小类  递归查找
     *
     * @param categoryEntity   某个大类
     * @param categoryEntities 所有的类别数据
     * @return
     */
    private List<CategoryEntity> getCategoryChildren(CategoryEntity categoryEntity
            , List<CategoryEntity> categoryEntities) {
        List<CategoryEntity> collect = categoryEntities.stream().filter(entity -> {
            // 根据大类找到他的直属的小类
            // 注意 Long 数据比较 不在 -128 127之间的数据是 new Long() 对象
            return entity.getParentCid().equals(categoryEntity.getCatId());
        }).map(entity -> {
            // 根据这个小类递归找到对应的小小类
            entity.setChildren(getCategoryChildren(entity, categoryEntities));
            return entity;
        }).sorted((entity1, entity2) -> {
            return (entity1.getSort() == null ? 0 : entity1.getSort()) - (entity2.getSort() == null ? 0 : entity2.getSort());
        }).collect(Collectors.toList());
        return collect;
    }

    /**
     * 跟进父编号获取对应的子菜单信息
     *
     * @param list
     * @param parentCid
     * @return
     */
    private List<CategoryEntity> queryByParenCid(List<CategoryEntity> list, Long parentCid) {
        List<CategoryEntity> collect = list.stream().filter(item -> {
            return item.getParentCid().equals(parentCid);
        }).collect(Collectors.toList());
        return collect;
    }



    public  Map<String, List<Catalog2VO>> getCatelog2JSONWithRedisLock() {
        String keys = "catalogJSON";
        //先去缓存中查询有没有数据如果有就返回，否则就查询数据库
        return getCatelog2JSONDbWithRedisLock(keys);
    }

    private Map<String, List<Catalog2VO>> getCatelog2JSONDbWithRedisLock(String keys) {
        //从redis中获取父类的信息
        String catalogJSON = stringRedisTemplate.opsForValue().get(keys);
        if (!StringUtils.isEmpty(catalogJSON)) {
            //说明缓存命中
            //表示缓存命中了数据,那么从缓存中获取信息，然后返回
            Map<String, List<Catalog2VO>> stringListMap = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catalog2VO>>>() {
            });
            return stringListMap;
        }

        // 获取所有的分类数据
        List<CategoryEntity> list = baseMapper.selectList(new QueryWrapper<CategoryEntity>());
        // 获取所有的一级分类的数据
        List<CategoryEntity> leve1Category = this.queryByParenCid(list, 0l);
        // 把一级分类的数据转换为Map容器 key就是一级分类的编号， value就是一级分类对应的二级分类的数据
        Map<String, List<Catalog2VO>> map = leve1Category.stream().collect(Collectors.toMap(
                key -> key.getCatId().toString()
                , value -> {
                    // 根据一级分类的编号，查询出对应的二级分类的数据
                    List<CategoryEntity> l2Catalogs = this.queryByParenCid(list, value.getCatId());
                    List<Catalog2VO> Catalog2VOs = null;
                    if (l2Catalogs != null) {
                        Catalog2VOs = l2Catalogs.stream().map(l2 -> {
                            // 需要把查询出来的二级分类的数据填充到对应的Catelog2VO中
                            Catalog2VO catalog2VO = new Catalog2VO(l2.getParentCid().toString(), null, l2.getCatId().toString(), l2.getName());
                            // 根据二级分类的数据找到对应的三级分类的信息
                            List<CategoryEntity> l3Catelogs = this.queryByParenCid(list, l2.getCatId());
                            if (l3Catelogs != null) {
                                // 获取到的二级分类对应的三级分类的数据
                                List<Catalog2VO.Catalog3VO> catalog3VOS = l3Catelogs.stream().map(l3 -> {
                                    Catalog2VO.Catalog3VO catalog3VO = new Catalog2VO.Catalog3VO(l3.getParentCid().toString(), l3.getCatId().toString(), l3.getName());
                                    return catalog3VO;
                                }).collect(Collectors.toList());
                                // 三级分类关联二级分类
                                catalog2VO.setCatalog3List(catalog3VOS);
                            }
                            return catalog2VO;
                        }).collect(Collectors.toList());
                    }

                    return Catalog2VOs;
                }
        ));
        if (list == null) {
            //那就说明数据库中也不存在，防止缓存穿透
            stringRedisTemplate.opsForValue().set(keys, "1", 5, TimeUnit.SECONDS);

        } else {
            //从数据库中查询到的数据，我们要给缓存中也存储一份
            //防止缓存雪崩
            String json = JSON.toJSONString(map);
            stringRedisTemplate.opsForValue().set(keys, json, 10,TimeUnit.MINUTES);
        }
        return map;
    }

    /**
     * 查询所有分类数据，并且完成一级二级三级的关联
     *
     * @return
     */

    public synchronized Map<String, List<Catalog2VO>> getCatelog2JSONForDb() {
        String keys = "catalogJSON";
        //先去缓存中查询有没有数据如果有就返回，否则就查询数据库
        //从redis中获取父类的信息
        String catalogJSON = stringRedisTemplate.opsForValue().get(keys);
        if (!StringUtils.isEmpty(catalogJSON)) {
            //说明缓存命中
            //表示缓存命中了数据,那么从缓存中获取信息，然后返回
            Map<String, List<Catalog2VO>> stringListMap = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catalog2VO>>>() {
            });
            return stringListMap;
        }

        // 获取所有的分类数据
        List<CategoryEntity> list = baseMapper.selectList(new QueryWrapper<CategoryEntity>());
        // 获取所有的一级分类的数据
        List<CategoryEntity> leve1Category = this.queryByParenCid(list, 0l);
        // 把一级分类的数据转换为Map容器 key就是一级分类的编号， value就是一级分类对应的二级分类的数据
        Map<String, List<Catalog2VO>> map = leve1Category.stream().collect(Collectors.toMap(
                key -> key.getCatId().toString()
                , value -> {
                    // 根据一级分类的编号，查询出对应的二级分类的数据
                    List<CategoryEntity> l2Catalogs = this.queryByParenCid(list, value.getCatId());
                    List<Catalog2VO> Catalog2VOs = null;
                    if (l2Catalogs != null) {
                        Catalog2VOs = l2Catalogs.stream().map(l2 -> {
                            // 需要把查询出来的二级分类的数据填充到对应的Catelog2VO中
                            Catalog2VO catalog2VO = new Catalog2VO(l2.getParentCid().toString(), null, l2.getCatId().toString(), l2.getName());
                            // 根据二级分类的数据找到对应的三级分类的信息
                            List<CategoryEntity> l3Catelogs = this.queryByParenCid(list, l2.getCatId());
                            if (l3Catelogs != null) {
                                // 获取到的二级分类对应的三级分类的数据
                                List<Catalog2VO.Catalog3VO> catalog3VOS = l3Catelogs.stream().map(l3 -> {
                                    Catalog2VO.Catalog3VO catalog3VO = new Catalog2VO.Catalog3VO(l3.getParentCid().toString(), l3.getCatId().toString(), l3.getName());
                                    return catalog3VO;
                                }).collect(Collectors.toList());
                                // 三级分类关联二级分类
                                catalog2VO.setCatalog3List(catalog3VOS);
                            }
                            return catalog2VO;
                        }).collect(Collectors.toList());
                    }

                    return Catalog2VOs;
                }
        ));
        if (list == null) {
            //那就说明数据库中也不存在，防止缓存穿透
            stringRedisTemplate.opsForValue().set(keys, "1", 5, TimeUnit.SECONDS);

        } else {
            //从数据库中查询到的数据，我们要给缓存中也存储一份
            //防止缓存雪崩
            String json = JSON.toJSONString(map);
            stringRedisTemplate.opsForValue().set(keys, json, 10,TimeUnit.MINUTES);
        }
        return map;
    }

    /**
     * 查询出所有的二级和三级分类的数据
     * 并封装为Map<String, Catalog2VO>对象
     *
     * @return
     */
    //@Override
    public Map<String, List<Catalog2VO>> getCatelog2JSON() {
        String key = "catalogJSON";

        // 从Redis中获取分类的信息
        String catalogJSON = stringRedisTemplate.opsForValue().get(key);
        if (org.springframework.util.StringUtils.isEmpty(catalogJSON)) {

            // 缓存中没有数据，需要从数据库中查询
            Map<String, List<Catalog2VO>> catelog2JSONForDb = getCatelog2JSONWithRedisLock();
            if (catelog2JSONForDb == null) {
                //那就说明数据库中也不存在，防止缓存穿透
                stringRedisTemplate.opsForValue().set(key, "1", 5, TimeUnit.SECONDS);

            } else {
                //从数据库中查询到的数据，我们要给缓存中也存储一份
                //防止缓存雪崩
                String json = JSON.toJSONString(catelog2JSONForDb);
                stringRedisTemplate.opsForValue().set(key, json, 10,TimeUnit.MINUTES);
            }
            return catelog2JSONForDb;
        }

        // 表示缓存命中了数据，那么从缓存中获取信息，然后返回
        Map<String, List<Catalog2VO>> stringListMap = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catalog2VO>>>() {
        });
        return stringListMap;
    }

}