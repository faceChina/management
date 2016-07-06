package com.jzwgj.management.component.metaq;

import org.apache.log4j.Logger;

import com.jzwgj.management.component.metaq.log.MetaLog;
import com.jzwgj.metaq.client.MetaQProviderClinet;

public class MetaAnsyHelper implements Runnable {

	private Logger _logger = Logger.getLogger("metaqInfoLog");

	private String message;

	private String topic;

	private MetaQProviderClinet provider;

	public MetaAnsyHelper(MetaQProviderClinet provider, String topic, String message) {
		this.message = message;
		this.topic = topic;
		this.provider = provider;
	}

	@Override
	public void run() {
		try {
			_logger.info("management metaq 推送："+topic+",message"+message);
			boolean isSuccess = provider.send(topic, message);
			if (!isSuccess) {
				throw new Exception(MetaLog.getString("Producer.Sender.exception", topic, message));
			}
			_logger.info(MetaLog.getString("Producer.Sender.info", topic, message));
		} catch (Exception e) {
			new ReSendTimer(topic, message, provider);
		}
	}
}
