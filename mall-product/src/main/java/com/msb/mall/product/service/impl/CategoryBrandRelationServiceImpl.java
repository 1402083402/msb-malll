package com.msb.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.msb.mall.product.entity.BrandEntity;
import com.msb.mall.product.entity.CategoryEntity;
import com.msb.mall.product.service.BrandService;
import com.msb.mall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.msb.common.utils.PageUtils;
import com.msb.common.utils.Query;

import com.msb.mall.product.dao.CategoryBrandRelationDao;
import com.msb.mall.product.entity.CategoryBrandRelationEntity;
import com.msb.mall.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {
    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryBrandRelationDao relationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();
        BrandEntity brandEntity = brandService.getById(brandId);
        CategoryEntity categoryEntity = categoryService.getById(catelogId);
        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrandName(Long brandId, String name) {
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        entity.setBrandId(brandId);
        entity.setBrandName(name);
        this.update(entity,new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId));
    }

    @Override
    public void updateCatelogName(Long catId, String name) {
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        entity.setCatelogName(name);
        entity.setCatelogId(catId);
        this.update(entity,new UpdateWrapper<CategoryBrandRelationEntity>().eq("catelog_id",catId));
    }

    @Override
    public List<CategoryBrandRelationEntity> categroyBrandRelation(Long catId) {
        // 找到所有的品牌信息
        List<CategoryBrandRelationEntity> list = relationDao
                .selectList(new QueryWrapper<CategoryBrandRelationEntity>()
                        .eq("catelog_id", catId));
        /*List<BrandEntity> collect = list.stream().map((m) -> {
            BrandEntity entity = new BrandEntity();
            entity.setName(m.getBrandName());
            entity.setBrandId(m.getBrandId());
            return entity;
        }).collect(Collectors.toList());*/
        return list;
    }

}