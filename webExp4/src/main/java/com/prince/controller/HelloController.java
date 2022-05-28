/**
 * @AUTHOR Prince
 * @TIME 2021/5/22 16:29
 */

package com.prince.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping("/world")
    @ResponseBody
    public String hello(){
        return "Hello World!";
    }
}
