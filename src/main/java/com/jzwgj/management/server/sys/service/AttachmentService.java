package com.jzwgj.management.server.sys.service;

import java.util.List;

import com.jzwgj.management.server.sys.domain.CAttachment;
import com.jzwgj.management.util.Pagination;

public interface AttachmentService {
	
	void add(CAttachment CAttachment);
	
	void edit(CAttachment CAttachment);
	
	void delete(Long id);
	
	CAttachment getById(Long id);
	
	List<CAttachment> findList(CAttachment CAttachment);

	void editTableId(String tempTableId, String tableId);

	Pagination<CAttachment> findPageList(CAttachment cAttachment,
			Pagination<CAttachment> pagination);

	void getByTable(CAttachment cAttachment);

	CAttachment previewImg(String uaId);

	void editAll(String[] uaIds, String tableName, Long tableId);

	void save(CAttachment attachment);

	void deleteAll(String[] ids);

	CAttachment getTitleImg(String tableId,String type);

	void deleteByTableIdAndType(Long tableId, Integer type);
}
