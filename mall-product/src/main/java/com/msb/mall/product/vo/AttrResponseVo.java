package com.msb.mall.product.vo;

import lombok.Data;

/**
 * @author Jason
 * @date 2022/7/6
 * hello ashen one
 */
@Data
public class AttrResponseVo extends AttrVO{
    private String catelogName;
    private  String groupName;
    private Long[] catelogPath;
}
