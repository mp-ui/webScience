/**
 * @AUTHOR Prince
 * @TIME 2021/6/2 9:20
 */

package com.prince.dao;

import com.prince.entity.ArticleComment;

import java.util.List;

public interface ArticleCommentMapper {
    List<ArticleComment> selectAllByAid(Integer aid);
}
