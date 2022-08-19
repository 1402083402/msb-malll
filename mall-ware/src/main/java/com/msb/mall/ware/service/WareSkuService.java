package com.msb.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.common.dto.SkuHasStockDto;
import com.msb.common.utils.PageUtils;
import com.msb.mall.ware.entity.WareSkuEntity;
import com.msb.mall.ware.vo.WareSkuLockVO;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author wj
 * @email 1402083402@qq.com.com
 * @date 2022-06-23 21:17:51
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockDto> getSkusHasStock(List<Long> skuIds);

    Boolean orderLockStock(WareSkuLockVO vo);
}

