/**
 * @AUTHOR Prince
 * @TIME 2021/5/23 0:17
 */

package com.prince.controller;

import com.prince.dao.UserMapper;
import com.prince.entity.User;
import com.prince.entity.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    InfoController infoController;

    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(@RequestParam String username,@RequestParam String password){
        HashMap<String,Object> res = new HashMap<>(2);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() == 0){
            res.put("status",0);
            res.put("msg","用户名或密码错误！");
        }else{
            infoController.loginTimePlusOne();
            res.put("status",1);
        }
        return res;
    }

}
