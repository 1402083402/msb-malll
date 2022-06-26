package com.msb.mall.order.fegin;

import com.msb.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Jason
 * @date 2022/6/24
 * hello ashen one
 */

/**
 * @ FeignClient指明我们要从注册中心发现的服务名称
 */
@FeignClient(name = "mall-product")
public interface ProductService {
    @GetMapping("/product/brand/all")
    public R queryAllBrand();
}
