package com.msb.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.msb.common.utils.PageUtils;
import com.msb.mall.member.entity.MemberEntity;
import com.msb.mall.member.vo.MemberLoginVO;
import com.msb.mall.member.vo.MemberReigerVO;
import com.msb.mall.member.vo.SocialUser;

import java.util.Map;

/**
 * 会员
 *
 * @author wj
 * @email 1402083402@qq.com.com
 * @date 2022-06-23 21:19:45
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(MemberReigerVO vo);

    MemberEntity login(MemberLoginVO vo);

    /**
     * 社交登录
     * @param vo
     * @return
     */
    MemberEntity login(SocialUser vo);
}

