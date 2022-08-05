package com.msb.mall.product.dao;

import com.msb.mall.product.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.mall.product.vo.SpuItemGroupAttrVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 * 
 * @author wj
 * @email 1402083402@qq.com.com
 * @date 2022-06-23 17:11:08
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<SpuItemGroupAttrVo> getAttrgroupWithSpuId(@Param("spuId") Long spuId,@Param("catalogId") Long catalogId);
}
