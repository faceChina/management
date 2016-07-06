package com.jzwgj.management.server.sys.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwgj.management.mapper.CAttachmentMapper;
import com.jzwgj.management.server.sys.dao.AttachmentDao;
import com.jzwgj.management.server.sys.domain.CAttachment;

@Repository
public class AttachmentDaoImpl implements AttachmentDao {
	
	@Autowired
	private SqlSession sqlSession;

	public void add(CAttachment cAttachment) {
		sqlSession.getMapper(CAttachmentMapper.class).insertSelective(cAttachment);
	}

	public void edit(CAttachment cAttachment) {
		sqlSession.getMapper(CAttachmentMapper.class).updateByPrimaryKeySelective(cAttachment);

	}

	public CAttachment getById(Long id) {
		return sqlSession.getMapper(CAttachmentMapper.class).selectByPrimaryKey(id);
	}

	public List<CAttachment> findList(CAttachment cAttachment) {
		return sqlSession.getMapper(CAttachmentMapper.class).selectList(cAttachment);
	}

	public List<CAttachment> findPageList(CAttachment cAttachment, Integer start,
			Integer pageSize) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pojo", cAttachment);
		map.put("start", start);
		map.put("pageSize", pageSize);
		return sqlSession.getMapper(CAttachmentMapper.class).selectPageList(map);
	}

	public void delete(Long id) {
		sqlSession.getMapper(CAttachmentMapper.class).deleteByPrimaryKey(id);
	}

	public Integer getCount(CAttachment cAttachment) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pojo", cAttachment);
		return sqlSession.getMapper(CAttachmentMapper.class).selectCount(map);
	}

	public void editTableId(String tempTableId, String tableId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tableId", tableId);
		map.put("tempTableId", tempTableId);
		sqlSession.getMapper(CAttachmentMapper.class).editTableId(map);
	}

	@Override
	public CAttachment getTitleImg(Long tableId) {
		return sqlSession.getMapper(CAttachmentMapper.class).getTitleImg(tableId);
	}

	@Override
	public CAttachment getQrImg(Long tableId) {
		return sqlSession.getMapper(CAttachmentMapper.class).getQrImg(tableId);
	}

	@Override
	public void deleteByTableIdAndType(Long tableId, Integer type) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tableId", tableId);
		map.put("type", type);
		sqlSession.getMapper(CAttachmentMapper.class).deleteByTableIdAndType(map);
	}
}
