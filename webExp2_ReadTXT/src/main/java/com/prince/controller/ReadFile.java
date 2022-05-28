/**
 * @AUTHOR Prince
 * @TIME 2021/5/21 11:33
 */

package com.prince.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/readFile")
public class ReadFile {

    @ResponseBody
    @RequestMapping(value = "/txt",method = RequestMethod.POST)
    public Map<String,Object> txt(@RequestParam(name="txt")MultipartFile file){
        Map<String,Object> res = new HashMap<>(3);
        try {
            res.put("context",new String(file.getBytes()));
            res.put("title",file.getOriginalFilename());
            res.put("status",1);
        } catch (IOException e) {
            res.put("status",0);
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping(value = "test")
    @ResponseBody
    public String test(){
        return "Hello";
    }
}
