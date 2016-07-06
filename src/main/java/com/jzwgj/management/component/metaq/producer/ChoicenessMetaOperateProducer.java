package com.jzwgj.management.component.metaq.producer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jzwgj.management.component.metaq.MetaAnsyHelper;
import com.jzwgj.management.component.metaq.log.MetaLog;
import com.jzwgj.management.server.web.fancy.domain.FancyMessageItem;
import com.jzwgj.management.server.web.fancy.domain.dto.FancyMessageDto;
import com.jzwgj.management.server.web.fancy.service.FancyMessageService;
import com.jzwgj.management.util.PropertiesUtil;
import com.jzwgj.metaq.client.MetaQProviderClinet;
import com.jzwgj.metaq.vo.BestFaceChoiceness;
import com.jzwgj.metaq.vo.FancyItem;
import com.jzwgj.metaq.vo.Topic;


@Component
public class ChoicenessMetaOperateProducer {

	private Logger _logger = Logger.getLogger("metaqInfoLog");
	
	@Autowired
	private MetaQProviderClinet metaQProviderClinet;
	@Autowired
	private FancyMessageService fancyMessageService;

	public void senderAnsy(Long id) {
		ExecutorService executor = null;
		try {
			_logger.info("调用的线程========" + id + "=========="
					+ Thread.currentThread().getName());
			// 推送开关
			String switchProducer = PropertiesUtil.getContexrtParam("SWITCH_METAQ_PRODUCER_FANCY");
			if (StringUtils.isBlank(switchProducer) || !"1".equals(switchProducer)) {
				_logger.warn(MetaLog.getString("Switch.Fancy"));
				return;
			}
			
			FancyMessageDto fancyMessage = fancyMessageService.getFancyMessage(id);
			
			if (null == fancyMessage) {
				return;
			}
			executor = Executors.newSingleThreadExecutor();
			BestFaceChoiceness choiceness = new BestFaceChoiceness();
			choiceness.setId(fancyMessage.getId());
			choiceness.setFancyType(fancyMessage.getType());
			choiceness.setTitle(fancyMessage.getName());
			choiceness.setStatus(fancyMessage.getStatus());
			choiceness.setUserType(2);
			choiceness.setTime(new Date().getTime()+"");
			List<FancyItem> choicenessItemList = new ArrayList<FancyItem>();
			List<FancyMessageItem> itemList = fancyMessage.getItemList();
			for (FancyMessageItem fancyMessageItem : itemList) {
				FancyItem item = new FancyItem();
				item.setAuthor(fancyMessageItem.getAuthor());
				item.setBrief(fancyMessageItem.getBrief());
				item.setContent(fancyMessageItem.getBrief());
				item.setFancyMessageId(fancyMessageItem.getFancyMessageId());
				item.setId(fancyMessageItem.getId());
				item.setLinkUrl(fancyMessageItem.getLink());
				item.setTitle(fancyMessageItem.getName());
				item.setPictureUrl(fancyMessageItem.getPicturePath());
				item.setType(fancyMessageItem.getType());
				choicenessItemList.add(item);
			}
			choiceness.setCullings(choicenessItemList);
			executor.execute(new MetaAnsyHelper(metaQProviderClinet, Topic.FANCYTOPIC, choiceness.toJson()));
			_logger.info("");
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
		} finally {
			if (null != executor) {
				executor.shutdown();
			}
		}
	}

	/**
	 * @param choicenessId
	 */
	public void ansyAll(List<Long> choicenessId) {
		for (Long id : choicenessId) {
			try {
				this.senderAnsy(id);
			} catch (Exception e) {
				// 单条发送失败下一条
				continue;
			}
		}
	}

	/**
	 * @param choicenessList
	 */
	public void ansyAllSalesOrder(List<BestFaceChoiceness> choicenessList) {
		for (BestFaceChoiceness choiceness : choicenessList) {
			if (null == choiceness || null == choiceness.getId()) {
				continue;
			}
			try {
				this.senderAnsy(choiceness.getId());
			} catch (Exception e) {
				// 单条发送失败下一条
				continue;
			}
		}
	}

}
