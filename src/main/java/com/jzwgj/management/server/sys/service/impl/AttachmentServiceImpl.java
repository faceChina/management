package com.jzwgj.management.server.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzwgj.management.server.sys.dao.AttachmentDao;
import com.jzwgj.management.server.sys.domain.CAttachment;
import com.jzwgj.management.server.sys.service.AttachmentService;
import com.jzwgj.management.util.Pagination;
@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	private  AttachmentDao attachmentDao;
	
	public void add(CAttachment CAttachment) {
		Date date= new Date();
		CAttachment.setCreateTime(date);
		CAttachment.setUpdateTime(date);
		attachmentDao.add(CAttachment);
	}

	public void edit(CAttachment CAttachment) {
		CAttachment.setUpdateTime(new Date());
		attachmentDao.edit(CAttachment);
	}

	public CAttachment getById(Long id) {
		return attachmentDao.getById(id);
	}

	public List<CAttachment> findList(CAttachment CAttachment) {
		return  attachmentDao.findList(CAttachment);
	}

	public void delete(Long id) {
		attachmentDao.delete( id);
	}

	public void editTableId(String tempTableId, String tableId) {
		attachmentDao.editTableId(tempTableId,tableId);
	}

	public Pagination<CAttachment> findPageList(CAttachment cAttachment,
			Pagination<CAttachment> pagination) {
		Integer totalRow = attachmentDao.getCount(cAttachment);
		List<CAttachment> resultList = attachmentDao.findPageList(cAttachment, pagination.getStart(), pagination.getPageSize());
		pagination.setTotalRow(totalRow);
		pagination.setDatas(resultList);
		return pagination;
	}

	public void getByTable(CAttachment cAttachment) {
		
	}

	public CAttachment previewImg(String id) {
		return attachmentDao.getById(Long.valueOf(id));
	}

	public void editAll(String[] uaIds, String tableName, Long tableId) {
		for (String id : uaIds) {
			CAttachment ca = new CAttachment();
			ca.setId(Long.valueOf(id));
			ca.setTableId(tableId);
			ca.setTableName(tableName);
			this.edit(ca);
		}
	}

	public void save(CAttachment attachment) {
		if (null != attachment.getId()) {
			this.delete(attachment.getId());
		}
		this.add(attachment);
	}

	public void deleteAll(String[] ids) {
		for (String id : ids) {
			this.delete(Long.valueOf(id));
		}
	}

	@Override
	public CAttachment getTitleImg(String tableId,String type) {
		if ("qrImg".equals(type)) {
			return attachmentDao.getQrImg(Long.valueOf(tableId));
		}
		return attachmentDao.getTitleImg(Long.valueOf(tableId));
	}

	@Override
	public void deleteByTableIdAndType(Long tableId, Integer type) {
		attachmentDao.deleteByTableIdAndType(tableId,type);
	}

}
