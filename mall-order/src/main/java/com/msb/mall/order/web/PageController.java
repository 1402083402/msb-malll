package com.msb.mall.order.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Jason
 * @date 2022/8/13
 * hello ashen one
 */
@Controller
public class PageController {
    @GetMapping("/{page}.html")
    public String goPage(@PathVariable("page") String page){
        return page;
    }
}
