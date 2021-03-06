package com.msb.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.common.utils.PageUtils;
import com.msb.mall.product.entity.AttrEntity;
import com.msb.mall.product.vo.AttrGroupRelationVO;
import com.msb.mall.product.vo.AttrResponseVo;
import com.msb.mall.product.vo.AttrVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author wj
 * @email 1402083402@qq.com.com
 * @date 2022-06-23 17:11:08
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVO vo);

    PageUtils queryBasePage(Map<String, Object> params, Long catelogId, String attrType);


    AttrResponseVo getAttrInfo(Long attrId);

    void updateBaseAttr(AttrVO attr);




    void removeByIdsDetails(Long[] attrIds);


    List<AttrEntity> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrGroupRelationVO[] vos);

    PageUtils getNoAttrRelation(Map<String, Object> params, Long attrgroupId);

    List<Long> selectSearchAttrIds(List<Long> attrIds);
}

