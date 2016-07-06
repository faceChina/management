package com.jzwgj.management.mapper;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.sys.domain.CAttachment;

public interface CAttachmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CAttachment record);

    int insertSelective(CAttachment record);

    CAttachment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CAttachment record);

    int updateByPrimaryKey(CAttachment record);

	List<CAttachment> selectPageList(Map<String, Object> map);

	List<CAttachment> selectList(CAttachment cAttachment);

	Integer selectCount(Map<String, Object> map);

	void editTableId(Map<String, Object> map);

	CAttachment getTitleImg(Long tableId);

	CAttachment getQrImg(Long tableId);

	void deleteByTableIdAndType(Map<String, Object> map);
}