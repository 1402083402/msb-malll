package com.msb.mall.product.dao;

import com.msb.mall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author wj
 * @email 1402083402@qq.com.com
 * @date 2022-06-23 17:11:08
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
