package com.msb.mall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.msb.mall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 支付宝的配置文件
 */
//@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    // 商户appid 沙箱账号: tklalf8880@sandbox.com
    public static String APPID = "2021000121649000";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCPbiHd0ryhSOl0DL+o5Kp5Hpx2QHNQ00t17SyB9dO95FoFwGF3jwR3Wu3wIzG11tUwq2mhyHi42OU1WdqnoXEMYJbjdLFKfEpvdBeqMzwLLWe91RtanLag/D+AsehrQS5ho1qyoKoQi5taMJyCILmW4JMGKaqSjL+98sM4txxZMdIM//YUmT/WxCy9Gp2VNSZUJEzzxNxW5JZ5ecH/6ua+O+fuTUFmLTFU4/96f+oJi0ntj8VS5tj96hjWpmTXNVqLku4IHwdnqI9zL/mESfjAVCcUP3rAFbbAGtFTiOZJrsbwVWiFuOg9RHftBPs9TOKf3VzM3vSR1so79OXbQ4INAgMBAAECggEAEn9dlsIK2bIRiYnrXdi/s4cjHo0JUi8t2DOSPeB6Qk8k3QEGkOi85revX/zp+E2QiQpSQS8dgplWVh8Ud/H2JmZ+jy8JflWxM9aBBs48vCRK8Mx/DpyPYQunFO3cz5hEZwsuM0NNK30Vt/f090NJLBAiZfURrnzAbbrVOodC/K2iqWKXDsAQTi42DwcxtVEW8lHwDdaG1ExB0FXhC4H62b5Oh6brrr9Gjp2FrfQoGNuPKmopOnLQACWvpkA4MpHtyaXbzCq2tSmpTNK01EV8IEVJR9QojmIWZMdOCaNpzOsyTWQIHnHdiUjFyKWPothSxDXIh0yqmpR+ewZFz8HSAQKBgQDLigOiyh3W8uXQAAo5OL/UJSGiUlhiPnIWAGT36YRvjoVtbsmuInOgESNul+D2iOrO8Jq7us2tH6t9ICJ7aqdkX85GY3pdXa/Mg1VOVYwyiXFStOEJwPNVRPNSjSgcnLiRRFLuSEPeOeD168M46Vv7DnFHsQZZo6upcvRD6gF6LQKBgQC0Zf3jD6CiAda45lcAwaiuw4T1BsCJwpODXNyIIyaqr377Oe1dDs/SBnIMep1QZb2tki39s9duaeKJa6VGv/c7+Au6mmPhaREo/sGKI2dg/zu1zsmm1WCCeDHzTBbHfBsveajl/ieAKjWeT+f5j+nzLskbYEMp6nCjhpY7v1pzYQKBgQCVikL9mW2u/SqnVUAtpmoZ3WYGsmVJWQPM8mE8W473v0h8jEcU7y7wTWcw29lmiFWr0M5+WtK9/WSrO67Z6hQuBM2ynWfLrQd6RIuqT+P/RUfDMi2cUuz0mwIChr8Ptn1MDVy4wNNMNE35oEwARkNUAtprh75YoY1/o3ufYg/PyQKBgEz6HG0QVQ3sVPCPMV7cRBtWyNw5Zl0iTwnFzUC2JyC8RCX1YOjNkjZfk7jpdUlCML7o2oEghXPS8NyMt3jKHzhSrsaQbMBZsf3ZP0KvusQ0BQmeVIPBFy51FXNo62tx9LO8aSrC2Z4h5MFJ/YWLOFwRgbURzXcMV8Yffum9zF3hAoGAJtZ2qExp0q0tp/OfldGTfIVPv0R405aWPihD8F3ZMDDD22uH6tjxSU1WsExVsxRa46LDVwxxr3G9m2sbOFgjNQ6hXC++BzbMNcuWLtt+RLwLxXcSf6CYWRICC36R3c6PvFbJhJwOYjr2fi2fbPZSGSgrNpCtnO3tzsOJxRJh8so=";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://order.msb.com/payed/notify";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://order.msb.com/orderPay/returnUrl";
    // 请求网关地址
    public static String URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjMH8HXe1QQERhii0RHyzp/ETNKqc2NG+SM8gRHyY3QrftYBVYLgSGov6rVh5fo08znCJeZe1xkaLxll3iqFXpsDJbNE4zl2hTsROWWSGScKqUPk8KEuaCknAvYNQYllcTEJy4OzF2KYzrZfxRO/AQwPSB6wZwWdjWbnpv6birnprEcQnvW6/592+vr/1BWFncfbnSLo5nCX9v4BPIspWxl1PIpLbrH5fWtblYWYGHWqLWVOTeWcITzzVq1nhvAZgMq14X8kE8Hgz5cQyFlf+zVcEWaXwhoNTfAGY6P6j1sGq0goXliohPbDBWKmKgbblK7uYbZj9pSwuyLhUPQy6twIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";

    public String pay(PayVo payVo){
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(URL,
                APPID,
                RSA_PRIVATE_KEY,
                FORMAT,
                CHARSET,
                ALIPAY_PUBLIC_KEY,
                SIGNTYPE);
        AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();

        // 封装请求支付信息
        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
        model.setOutTradeNo(payVo.getOut_trader_no());
        model.setSubject(payVo.getSubject());
        model.setTotalAmount(payVo.getTotal_amount());
        model.setBody(payVo.getBody());
        model.setTimeoutExpress("5000");
        model.setProductCode("111111");
        alipay_request.setBizModel(model);
        // 设置异步通知地址
        alipay_request.setNotifyUrl(notify_url);
        // 设置同步地址
        alipay_request.setReturnUrl(return_url);

        // form表单生产
        String form = "";
        try {
            // 调用SDK生成表单
            form = client.pageExecute(alipay_request).getBody();
            return form;
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return  null;
    }
}
