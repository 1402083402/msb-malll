package com.msb.mall.fegin;

import com.msb.common.utils.R;

import com.msb.mall.vo.LoginVo;
import com.msb.mall.vo.SocialUser;
import com.msb.mall.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("mall-member")
public interface MemberFeginService {
    @PostMapping("/member/member/register")
    public R register(@RequestBody UserRegisterVo vo);

    @RequestMapping("/member/member/login")
    public R login(@RequestBody LoginVo vo);
    @RequestMapping("/member/member/oauth2/login")
    public R socialLogin(@RequestBody SocialUser vo);

}
