/**
 * @AUTHOR Prince
 * @TIME 2021/5/22 19:44
 */

package com.prince.controller;

import com.prince.dao.ArticleMapper;
import com.prince.entity.Article;
import com.prince.entity.ArticleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    public ArticleMapper articleMapper;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> list(){
        Map<String,Object> res = new HashMap<>(1);
        List<Article> articles = articleMapper.selectByExampleWithBLOBs(new ArticleExample());
        res.put("data",articles);
        return res;
    }


    @RequestMapping(value = "/lists",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> lists(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        Map<String,Object> res = new HashMap<>(4);
        long total = articleMapper.countByExample(new ArticleExample());
        if(page <= 0){
            page = 1;
        }
        List<Article> articles = articleMapper.selectAllWithPage(page,limit);
        res.put("data",articles);
        res.put("total",total);
        res.put("page",page);
        res.put("count",articles.size());
        return res;
    }


    @RequestMapping("/{id}")
    public ModelAndView look(@PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();
        if(!id.matches("\\d+")){
            modelAndView.setViewName("404");
        }else{
            int idd = Integer.parseInt(id);
            Article article = articleMapper.selectByPrimaryKey(idd);
            if(article == null){
                modelAndView.setViewName("404");
            }else{
                modelAndView.setViewName("article");
                modelAndView.addObject("article",article);
            }
        }
        return modelAndView;
    }

    @GetMapping("/create")
    public String create(){
        return "create";
    }

    @PostMapping("/create1")
    @ResponseBody
    public HashMap<String,Object> create1(
            @RequestParam("title") String title,
            @RequestParam("content") String content
    ){
        Article article = new Article();
        article.setContent(content);
        article.setAuthorId(1);
        article.setTitle(title);
        int insert = articleMapper.insert(article);
        HashMap<String,Object> res = new HashMap<>(2);
        if(insert >= 1){
            res.put("status",1);
            //查询刚刚插入的articleId
            ArticleExample articleExample = new ArticleExample();
            articleExample.createCriteria().andTitleEqualTo(article.getTitle()).andAuthorIdEqualTo(article.getAuthorId());
            List<Article> articles = articleMapper.selectByExample(articleExample);
            if(articles.size() > 0){
                res.put("articleId",articles.get(articles.size() - 1).getId());
            }
        }else{
            res.put("status",0);
        }
        return res;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(@RequestParam("id") int id){
        int i = articleMapper.deleteByPrimaryKey(id);
        HashMap<String,Object> res = new HashMap<>();
        res.put("status",i);
        return res;
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();
        if(!id.matches("\\d+")){
            modelAndView.setViewName("404");
        }else{
            int idd = Integer.parseInt(id);
            Article article = articleMapper.selectByPrimaryKey(idd);
            if(article == null){
                modelAndView.setViewName("404");
            }else{
                modelAndView.setViewName("edit");
                modelAndView.addObject("article",article);
            }
        }
        return modelAndView;
    }

    @PostMapping("/edit1")
    @ResponseBody
    public HashMap<String,Object> edit1(
            @RequestParam("id") int id,
            @RequestParam("title") String title,
            @RequestParam("content") String content
    ){
        HashMap<String,Object> res = new HashMap<>();
        Article article = articleMapper.selectByPrimaryKey(id);
        if(article == null){
            res.put("status",0);
            res.put("msg","不存在这篇文章");
            return res;
        }
        article.setTitle(title);
        article.setContent(content);
        int i = articleMapper.updateByPrimaryKeyWithBLOBs(article);
        if(i >= 1){
            res.put("status",1);
            res.put("msg","修改成功");
        }else{
            res.put("status",0);
            res.put("msg","修改失败");
        }
        return res;
    }


}
