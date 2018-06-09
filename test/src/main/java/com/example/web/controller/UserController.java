package com.example.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.web.service.UserService;
import com.example.web.entity.User;





@RestController 
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/redis")  
	public String findRedis() {  
	    return userService.findRedis();  
	}  
	
	@RequestMapping("/redisUser")  
	public String findRedisUser(@RequestBody User user) {  
	    return userService.getOne(user.getUserid()).toString(); 
	}  
	
	@ResponseBody
    @RequestMapping(value="/register")
    public Map<String,Object> register(@RequestBody User user) {
    	Map<String,Object> map = new HashMap<String,Object>();
    	try {
    		String regEx = "^[A-Za-z0-9]{0,30}$";
        	Pattern pattern = Pattern.compile(regEx);
        	Matcher matcher = pattern.matcher(user.getUserid());
        	if(!matcher.matches()) {
        		map.put("code", "-1");
        		map.put("msg", "用户名不正确,请输入a-zA-Z0-9的用户名!");
        		return map;
        	}
        	user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()).getBytes()));
        	map = userService.register(user);
        	return map;
		} catch (Exception e) {
			map.put("code","-99");
			map.put("msg", e.toString());
			return map;
		} finally {
			user=null;
		}
    }
    
    @ResponseBody
    @RequestMapping(value="/list")
    public List<User> list() {
        return userService.list();
    }
    
    @ResponseBody
    @RequestMapping(value="/load")
    public String load() {
         FileInputStream in=null;  
         File file = new File("D:\\a.txt");  
         BufferedReader reader = null;  
         String string="";
         try {  
             reader = new BufferedReader(new FileReader(file));  
             String tempString = null;  
             int line = 1;  
             // 一次读入一行，直到读入null为文件结束  
             while ((tempString = reader.readLine()) != null) {  
            	 string+=tempString+"\r\n";
                 // 显示行号  
                 line++;  
             }  
             reader.close();  
         } catch (IOException e) {  
             e.printStackTrace();  
         } finally {  
             if (reader != null) {  
                 try {  
                     reader.close();  
                 } catch (IOException e1) {  
                 }  
             }  
         }  
        return string;
    }
    
    @ResponseBody
    @RequestMapping(value="/upload")
    public  String upload(@RequestParam("test") MultipartFile file){
    	if (file.isEmpty()) {
            return "文件为空";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        System.out.println("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = "D://test//";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }
}

