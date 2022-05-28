/**
 * @AUTHOR Prince
 * @TIME 2021/6/2 9:04
 */

package com.prince.controller;

import com.alibaba.fastjson.JSON;
import com.prince.dao.ArticleCommentMapper;
import com.prince.dao.ArticleMapper;
import com.prince.dao.CommentMapper;
import com.prince.dao.UserMapper;
import com.prince.entity.*;
import com.prince.util.BlogResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    UserMapper userMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    ArticleCommentMapper articleCommentMapper;

    @PostMapping("/comment")
    public BlogResult comment(@RequestParam Integer id, @RequestParam String text, HttpServletRequest request) throws UnsupportedEncodingException {
        List<User> users = null;
        Cookie[] cookies = request.getCookies();
        Cookie cookieUser = null;
        for (Cookie cookie : cookies) {
            if("user".equals(cookie.getName())){
                cookieUser = cookie;
                break;
            }
        }
        if(cookieUser == null){
            return new BlogResult(0,"你还没登录呢！！不能评论！！！");
        }
        String stringUser = cookieUser.getValue();
        //URL解码
        stringUser = URLDecoder.decode(stringUser,"UTF-8");
        Map<String, Object> map = JSON.parseObject(stringUser);
        if(map.containsKey("username") && map.containsKey("password")){
            //检查是否已经登录
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUsernameEqualTo((String) map.get("username")).andPasswordEqualTo((String) map.get("password"));
            users = userMapper.selectByExample(userExample);
            if(users == null || users.size() == 0){
                return new BlogResult(0,"登录失败，请重新登录后再试！！！");
            }
        }else{
            return new BlogResult(0,"你还没登录呢！！不能评论！！！");
        }
        Article article = articleMapper.selectByPrimaryKey(id);
        if(article == null){
            return new BlogResult(0,"这篇文章不存在！！！");
        }
        Comment comment = new Comment();
        comment.setAid(id);
        comment.setUid(users.get(0).getId());
        comment.setText(text);
        int insert = commentMapper.insert(comment);
        if(insert > 0){
            return new BlogResult(1,"评论成功！");
        }else{
            return new BlogResult(0,"评论失败！！");
        }
    }

    @RequestMapping("/list")
    public BlogResult list(@RequestParam Integer aid){
        List<ArticleComment> articleComments = articleCommentMapper.selectAllByAid(aid);
        if(articleComments == null){
            return new BlogResult(0,"查询失败！");
        }
        return new BlogResult(1,"查询成功！",articleComments);
    }

}
