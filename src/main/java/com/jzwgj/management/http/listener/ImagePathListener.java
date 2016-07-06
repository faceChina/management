package com.jzwgj.management.http.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jzwgj.management.util.PropertiesUtil;

@Component
public class ImagePathListener implements ServletContextListener {

	private Logger log = Logger.getLogger(ImagePathListener.class);
	private static final String ROOT_PICURL_NAME = "picUrl";
	private static final String ROOT_PICURL = "ROOT_PICURL";
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		//获得全局变量
        ServletContext servletContext = servletContextEvent.getServletContext();
        //设置全局变量属性
		String picUrl = PropertiesUtil.getContexrtParam(ROOT_PICURL);
		log.info("图片基础路径："+picUrl);
        servletContext.setAttribute(ROOT_PICURL_NAME, picUrl);
	}
	
}
