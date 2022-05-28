package com.prince.dao;

import com.prince.entity.Request;
import com.prince.entity.RequestExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RequestMapper {
    long countByExample(RequestExample example);

    int deleteByExample(RequestExample example);

    int insert(Request record);

    int insertSelective(Request record);

    List<Request> selectByExample(RequestExample example);

    int updateByExampleSelective(@Param("record") Request record, @Param("example") RequestExample example);

    int updateByExample(@Param("record") Request record, @Param("example") RequestExample example);
}