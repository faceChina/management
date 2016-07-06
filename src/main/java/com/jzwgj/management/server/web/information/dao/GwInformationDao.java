package com.jzwgj.management.server.web.information.dao;

import java.util.List;
import java.util.Map;

import com.jzwgj.management.server.web.information.domain.GwInformation;

public interface GwInformationDao {
	
	Long save(GwInformation gwInformation);
	
	GwInformation getByPrimaryKey(Long id);
	
	void update(GwInformation gwInformation);

	void deleteById(Long id);

	int getCount(GwInformation gwInformation);

	List<GwInformation> findGwInformationPageList(Map<String, Object> map);
	
	/**
	 * 查询最大排序
	 * @return
	 */
	Long getMaxSort();
	
	/**
	 * 查询最小排序
	 * @return
	 */
	Long getMinSort();
}
