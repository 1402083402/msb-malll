package com.msb.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.common.utils.PageUtils;
import com.msb.mall.order.entity.OrderEntity;
import com.msb.mall.order.vo.OrderConfirmVo;

import java.util.Map;

/**
 * 订单
 *
 * @author wj
 * @email 1402083402@qq.com.com
 * @date 2022-06-23 21:21:21
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderConfirmVo confirmOrder();
}

