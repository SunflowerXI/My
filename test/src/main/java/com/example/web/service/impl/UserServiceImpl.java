package com.example.web.service.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.web.mapper.UserMapper;
import com.example.web.service.UserService;

import redis.clients.jedis.JedisCluster;

import com.example.web.entity.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired  
	private JedisCluster jedisCluster;

	@Override
	public List<User> list() {
		List<User> list = userMapper.list();
		return list;
	}

	@Override
	public Map<String,Object> register(User user) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			User selectByPrimaryKey = userMapper.selectByPrimaryKey(user.getUserid());
			if(selectByPrimaryKey!=null) {
				map.put("code","-2");
				map.put("msg","该用户名已被注册!");
				return map;
			}
			userMapper.insert(user);
			map.put("code","1");
			map.put("msg","注册用户成功...");
			return map;
		} catch (Exception e) {
			map.put("code","-99");
			map.put("msg",e.toString());
			return map;
		}
	}
	
	@Override
	public String findRedis() {  
	    jedisCluster.set("userName", "hello wenqy");  
	    return jedisCluster.get("userName");  
	}  
	
	@Cacheable(value="user",key="'user:'+#userid")
	public User getOne(String userid) {
		User user = userMapper.findByUserid(userid);
		return user;
	}
}
