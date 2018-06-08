package com.example.web.controller;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;







@Controller
@RequestMapping(value="/mytest")
public class UploadController {
	
	
    @ResponseBody
    @RequestMapping(value="/upload")
    public  void upload(@RequestParam("test") MultipartFile file,
    		@RequestParam("totalChunks") int totalChunks, //总块数
    		@RequestParam("totalSize") int totalSize,	//总大小
    		@RequestParam("chunkNumber") int chunkNumber,//索引 第几块
    		@RequestParam("chunkSize") int chunkSize,	//每块大小
    		@RequestParam("currentChunkSize") int currentChunkSize //当前块大小
    		){
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
        long downloadSize = 0;
        if(dest.exists()) {
        	downloadSize = dest.length();
        }else{
        	try {
				file.transferTo(dest);
				return ;
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        System.out.println("本地文件大小："+downloadSize);
        if(downloadSize==totalSize) {
        	return;
        }
        try {
        	InputStream ins = file.getInputStream();     
            RandomAccessFile raFile = new RandomAccessFile(filePath + fileName, "rw"); 
            int start=(chunkNumber-1)*chunkSize;
            raFile.seek(start);           
            byte[] buffer = new byte[4096];  
            int len = -1;  
            while((len = ins.read(buffer))!=-1){  
                raFile.write(buffer,0,len);  
            }  
            raFile.close();  
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }	
}

