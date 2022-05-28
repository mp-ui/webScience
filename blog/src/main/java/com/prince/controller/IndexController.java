/**
 * @AUTHOR Prince
 * @TIME 2021/6/1 16:57
 */

package com.prince.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping({"/","/index.jsp"})
    public String index(){
        return "index";
    }

}
