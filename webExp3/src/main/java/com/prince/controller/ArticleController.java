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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
}
