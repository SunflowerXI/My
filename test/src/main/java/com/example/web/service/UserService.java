package com.example.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.web.entity.User;

@Service(value ="userService")
public interface UserService {
	
	public List<User> list();

	public Map<String,Object> register(User user);
	
	public String findRedis();
	
	public User getOne(String userid);
}
