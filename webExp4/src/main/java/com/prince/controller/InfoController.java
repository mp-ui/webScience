/**
 * @AUTHOR Prince
 * @TIME 2021/5/22 23:28
 */

package com.prince.controller;

import com.prince.dao.InfoMapper;
import com.prince.entity.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/info")
public class InfoController {

    @Autowired
    InfoMapper infoMapper;

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> get(){
        HashMap<String,Object> res = new HashMap<>(2);
        Info info = infoMapper.selectByPrimaryKey(1);
        if(info == null){
            res.put("status",0);
        }else{
            res.put("status",1);
            res.put("data",info);
        }
        return res;
    }

    @RequestMapping(value = "/loginTimePlusOne",method = RequestMethod.GET)
    @ResponseBody
    public void loginTimePlusOne(){
        Info info = infoMapper.selectByPrimaryKey(1);
        if(info != null){
            info.setLoginTime(info.getLoginTime() + 1);
            infoMapper.updateByPrimaryKey(info);
        }
    }

    @RequestMapping(value = "/visitTimePlusOne",method = RequestMethod.GET)
    @ResponseBody
    public void visitTimePlusOne(){
        Info info = infoMapper.selectByPrimaryKey(1);
        if(info != null){
            info.setVisitTime(info.getVisitTime() + 1);
            infoMapper.updateByPrimaryKey(info);
        }
    }



}
