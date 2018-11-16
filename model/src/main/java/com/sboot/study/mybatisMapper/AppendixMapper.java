package com.sboot.study.mybatisMapper;

import com.sboot.study.entity.Appendix;

public interface AppendixMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Appendix record);

    int insertSelective(Appendix record);

    Appendix selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Appendix record);

    int updateByPrimaryKey(Appendix record);
}