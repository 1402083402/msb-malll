package com.msb.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.common.utils.PageUtils;
import com.msb.mall.ware.entity.PurchaseEntity;
import com.msb.mall.ware.vo.MergeVO;
import com.msb.mall.ware.vo.PurchaseDoneVO;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author wj
 * @email 1402083402@qq.com.com
 * @date 2022-06-23 21:17:51
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceive(Map<String, Object> params);

    Integer merge(MergeVO mergeVO);

    void received(List<Long> ids);

    void done(PurchaseDoneVO vo);
}

