/**
 * @AUTHOR Prince
 * @TIME 2021/6/1 21:34
 */

package com.prince.controller;

import com.prince.dao.UserMapper;
import com.prince.entity.User;
import com.prince.entity.UserExample;
import com.prince.util.BlogResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserMapper userMapper;

    @PostMapping("/login")
    public BlogResult login(@RequestParam String username,@RequestParam String password){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(userExample);
        if(users == null || users.size() == 0){
            return new BlogResult(0,"账号或密码错误！");
        }else{
            return new BlogResult(1,"登录成功！",users.get(0));
        }
    }

}
