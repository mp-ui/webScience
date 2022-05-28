/**
 * @AUTHOR Prince
 * @TIME 2021/6/1 18:36
 */

package com.prince.controller;

import com.alibaba.fastjson.JSON;
import com.prince.dao.ArticleMapper;
import com.prince.dao.CommentMapper;
import com.prince.dao.UserMapper;
import com.prince.entity.*;
import com.prince.util.BlogResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentMapper commentMapper;

    /**
     * 列出所有文章
     * @param name  筛选文章
     * @param page  当前页数
     * @param limit 每页限制条数
     * @return
     */
    @RequestMapping("/lists")
    @ResponseBody
    public HashMap<String,Object> lists(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit
    ){
        if(page == null || page <= 0){
            page = 1;
        }
        HashMap<String,Object> res = new HashMap<>(3);
        List<Article> query = null;
        if(limit == null){
            ArticleExample articleExample = new ArticleExample();
            if(name != null){
                name = name.trim();
                articleExample.createCriteria().andTitleLike("%" + name + "%");
            }
            query = articleMapper.selectByExample(articleExample);
            res.put("total",query.size());
        }else{
            int offset = (page - 1) * limit;
            if(name == null){
                query = articleMapper.selectAllByOffsetLimit(offset,limit);
                res.put("total",articleMapper.countByExample(new ArticleExample()));
            }else{
                name = name.trim();
                query = articleMapper.selectAllByNameOffsetLimit(name,offset,limit);
                ArticleExample articleExample = new ArticleExample();
                articleExample.createCriteria().andTitleLike("%" + name + "%");
                res.put("total",articleMapper.countByExample(articleExample));
            }
        }
        res.put("data",query);
        res.put("page",page);
        return res;
    }

    /**
     * 访问文章
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/{id}")
    public String look(@PathVariable String id, Model model){
        Article article = new Article();
        article.setId(-1);
        article.setTitle("<div style=\"color:red;font-size: 35px\">文章不存在</div>");
        article.setContent("<div style=\"color:blue;font-size: 80px\">您访问的文章不存在！！！</div>");
        if(id.matches("\\d+")){
            int idd = Integer.parseInt(id);
            Article article1 = articleMapper.selectByPrimaryKey(idd);
            if(article1 != null){
                //如果Article存在，则用查询到的替换原来的
                article = article1;
                //读取作者信息
                User user = userMapper.selectByPrimaryKey(article.getUid());
                if(user != null){
                    model.addAttribute("user",user);
                }
                //读取发布时间
                Date ctime = article.getCtime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = sdf.format(ctime);
                model.addAttribute("ctime",format);
                //浏览量+1
                article.setPageview(article.getPageview() + 1);
                articleMapper.updateByPrimaryKey(article);
            }
        }
        model.addAttribute("article",article);
        return "article";
    }

    @GetMapping("/create")
    public String create(){
        return "create";
    }

    @PostMapping("/create")
    @ResponseBody
    public BlogResult create1(@RequestParam String title, @RequestParam String content, @RequestParam(required = false) String description, HttpServletRequest request) throws UnsupportedEncodingException {
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
            return new BlogResult(0,"你还没登录呢！！不能新增文章！！！");
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
            return new BlogResult(0,"你还没登录呢！！不能新增文章！！！");
        }
        Article article = new Article();
        article.setTitle(title);
        article.setDescription(description);
        article.setContent(content);
        article.setUid(users.get(0).getId());
        article.setPageview(0);
        int insert = articleMapper.insert(article);
        if(insert > 0){
            //成功
            //找出刚刚插入的id，方便前端插入成功后跳转
            BlogResult blogResult = new BlogResult(1,"新增成功！");
            ArticleExample articleExample = new ArticleExample();
            articleExample.createCriteria().andTitleEqualTo(title).andUidEqualTo(users.get(0).getId());
            List<Article> articles = articleMapper.selectByExample(articleExample);
            if(articles == null || articles.size() == 0){
                return new BlogResult(0,"新增失败！");
            }
            Map<String,Object> m = new HashMap<>();
            m.put("article_id",articles.get(articles.size() - 1).getId());
            blogResult.setData(m);
            return blogResult;
        }else{
            return new BlogResult(0,"新增失败！");
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id,Model model){
        Article article = new Article();
        article.setId(-1);
        article.setTitle("<div style=\"color:red;font-size: 35px\">文章不存在</div>");
        article.setContent("<div style=\"color:blue;font-size: 80px\">您访问的文章不存在！！！</div>");
        if(id.matches("\\d+")){
            int idd = Integer.parseInt(id);
            Article article1 = articleMapper.selectByPrimaryKey(idd);
            if(article1 != null){
                //如果Article存在，则用查询到的替换原来的
                article = article1;
                model.addAttribute("article",article);
                return "edit";
            }
        }
        model.addAttribute("article",article);
        return "article";
    }

    @PostMapping("/edit")
    @ResponseBody
    public BlogResult edit1(@RequestParam int id,@RequestParam String title, @RequestParam String content, @RequestParam(required = false) String description, HttpServletRequest request) throws UnsupportedEncodingException {
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
            return new BlogResult(0,"你还没登录呢！！不能修改文章！！！");
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
            return new BlogResult(0,"你还没登录呢！！不能修改文章！！！");
        }
        Article article = articleMapper.selectByPrimaryKey(id);
        if(article == null){
            return new BlogResult(0,"这篇文章不存在！！！");
        }
        //判断是不是这篇文章的主人
        if(!article.getUid().equals(users.get(0).getId())){
            return new BlogResult(0,"你不是这篇文章的作者，无法修改！！！");
        }
        article.setTitle(title);
        article.setDescription(description);
        article.setContent(content);
        int update = articleMapper.updateByPrimaryKeyWithBLOBs(article);
        if(update > 0){
            return new BlogResult(1,"修改成功！");
        }else{
            return new BlogResult(0,"修改失败！");
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public BlogResult delete(@RequestParam int id,HttpServletRequest request) throws UnsupportedEncodingException {
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
            return new BlogResult(0,"你还没登录呢！！不能删除文章！！！");
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
            return new BlogResult(0,"你还没登录呢！！不能删除文章！！！");
        }
        Article article = articleMapper.selectByPrimaryKey(id);
        if(article == null){
            return new BlogResult(0,"这篇文章不存在！！！");
        }
        //判断是不是这篇文章的主人
        if(!article.getUid().equals(users.get(0).getId())){
            return new BlogResult(0,"你不是这篇文章的作者，无法删除！！！");
        }
        //删除文章之前先删除文章的评论
        {
            CommentExample commentExample = new CommentExample();
            commentExample.createCriteria().andAidEqualTo(id);
            commentMapper.deleteByExample(commentExample);
        }
        int delete = articleMapper.deleteByPrimaryKey(id);
        if(delete > 0){
            return new BlogResult(1,"删除成功！！！");
        }
        return new BlogResult(0,"删除失败！！！");
    }

}
