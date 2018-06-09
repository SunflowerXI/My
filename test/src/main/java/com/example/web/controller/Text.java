package com.example.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;





public class Text {

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) {
		String sr = Text.sendPost("http://demo.infinitsea.com/rp/data/uploadRepairRecord",
				"jsonString={'authToken':'079ad6989409456a9878bf41ca8d2d7a','repairRecord':{'orderCode':'WX000120170517001','licensePlate':'皖A7D111','vin':'ED456745231311331','enginenumber':'','brand':'','vehicleType':'阿斯顿-马丁 ','carType':'','licenseNo':'','repairFactory':'因为科技有限公司','factoryContactTel':'','htbh':'','hgzbh':'','zjyxm':'','zjysfz':'','jdryxm':'','scrsfz':'','scrdh':'','qcrsfz':'','scdwdz':'','zmcltx':'','jdrysfzh':'','txfzbpj':'','enterDate':'2017-05-17','releaseDate':'2017-05-17','mileage':'25000','repairType':'汽车小修','nextRepairMileage':'','nextRepairDate':'','checkCircs':'','maintainPeriodMileage':'50000','maintainPeriodDay':'','majorRepairer':'','technicalDirector':'','reworkRecord':'','careUnit':'','faultDescription':'','faultCause':'','jczje':'','jgzje':'','materialsum':'331.59','mlaborsum':'30','otherexpsum':'0','repairTotalAmount':'361.59','dataFrom':'','labourPrice':'0','repairItem':[{'repairItemName':'清洗进排气','labourHour':'1','laborhourprice':'20','labourAmount':'20'},{'repairItemName':'更换空气滤清器','labourHour':'1','laborhourprice':'10','labourAmount':'10'}],'repairAccessory':[{'partCode':'001001-00002','partName':'五福金牛X5脚垫','partsattribute':'10','partsmanufacture':'无','partsort':'99','partQuantity':'1','specification':'','partPrice':'130','partAmount':'130','caruserparts':'0'},{'partCode':'001001-00001','partName':'JMC威威脚垫','partsattribute':'10','partsmanufacture':'无','partsort':'99','partQuantity':'1','specification':'','partPrice':'120','partAmount':'120','caruserparts':'0'},{'partCode':'001001-000002','partName':'大力脚垫','partsattribute':'10','partsmanufacture':'无','partsort':'99','partQuantity':'1','specification':'','partPrice':'81.6','partAmount':'81.6','caruserparts':'0'}],'processItem':[],'testItem':[],'otherItem':[]}}");
		System.out.println(sr);
	}
}
