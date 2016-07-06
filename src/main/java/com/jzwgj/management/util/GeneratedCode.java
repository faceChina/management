package com.jzwgj.management.util;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.zjlp.face.file.service.ImageService;

public class GeneratedCode {

	@Autowired
	private ImageService imageService;

	public String generatedCode(String codeUrl){
		try {
			byte[] b = null;
			
			byte[] qrbyte = QRCodeUtil.encode(codeUrl, b, true);
			
			if(null == qrbyte){
				return null;
			}
			
			StopWatch uploadClock = new StopWatch();
			uploadClock.start(); // 计时开始
			
			//上传二维码
			String flag = imageService.upload(qrbyte);
			
			uploadClock.stop();
			
			JSONObject jsonObject = JSONObject.fromObject(flag);
			if(!"SUCCESS".equals(jsonObject.get("flag").toString())){
				return null;
			}
			if(StringUtils.isBlank(jsonObject.getString("path"))){
				return null;
			}
			
			return jsonObject.getString("path");
		} catch (Exception e) {
			return null;
		}
	}
}
