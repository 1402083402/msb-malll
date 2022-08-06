package com.msb.mall.controller;

import com.alibaba.fastjson.JSON;

import com.msb.common.utils.HttpUtils;
import com.msb.common.utils.R;

import com.msb.mall.fegin.MemberFeginService;

import com.msb.mall.vo.SocialUser;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@Controller
public class OAuth2Controller {

    @Autowired
    private MemberFeginService memberFeginService;

    @RequestMapping("/oauth/weibo/success")
    public String weiboOAuth(@RequestParam("code") String code
                                , HttpSession session, HttpServletResponse response) throws Exception {
        System.out.println("code = " + code);
        Map<String,String> body = new HashMap<>();
        body.put("client_id","1709923922");
        body.put("client_secret","f1cb3fd632c4f4f4b496ed323907a643");
        body.put("grant_type","authorization_code");
        body.put("redirect_uri","http://auth.msb.com/oauth/weibo/success");
        body.put("code",code);
        // 根据Code获取对应的Token信息
        HttpResponse post = HttpUtils.doPost("https://api.weibo.com"
                , "/oauth2/access_token"
                , "post"
                , new HashMap<>()
                , null
                , body
        );
        int statusCode = post.getStatusLine().getStatusCode();
        if(statusCode != 200){
            // 说明获取Token失败,就调回到登录页面
            return "redirect:http://auth.msb.com/login.html";
        }
        // 说明获取Token信息成功
        String json = EntityUtils.toString(post.getEntity());
        SocialUser socialUser = JSON.parseObject(json, SocialUser.class);
        R r = memberFeginService.socialLogin(socialUser);
        if(r.getCode() != 0){
            // 登录错误
            return "redirect:http://auth.msb.com/login.html";
        }
        String entityJson=(String)r.get("entity");
        System.out.println("->>>>>"+entityJson);


        // 注册成功就需要调整到商城的首页
        return "redirect:http://mall.msb.com/home";
    }
}
