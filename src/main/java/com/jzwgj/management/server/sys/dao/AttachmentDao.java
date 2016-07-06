package com.jzwgj.management.server.sys.dao;

import java.util.List;

import com.jzwgj.management.server.sys.domain.CAttachment;

public interface AttachmentDao {

	void add(CAttachment cAttachment);
	
	void edit(CAttachment cAttachment);
	
	CAttachment getById(Long id);
	
	List<CAttachment> findList(CAttachment cAttachment);
	
	List<CAttachment> findPageList(CAttachment cAttachment,Integer start,Integer pageSize);

	void delete(Long id);

	Integer getCount(CAttachment cAttachment);

	void editTableId(String tempTableId, String tableId);

	CAttachment getTitleImg(Long tableId);
	
	CAttachment getQrImg(Long tableId);

	void deleteByTableIdAndType(Long tableId, Integer type);

}
