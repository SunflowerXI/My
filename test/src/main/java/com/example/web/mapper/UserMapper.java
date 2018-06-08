package com.example.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.web.entity.User;

@Mapper
public interface UserMapper {
	List<User> list();
	
	User findByUserid(String userid);
	
    int insert(User user);

    int insertSelective(User user);

    User selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User record);
    
}