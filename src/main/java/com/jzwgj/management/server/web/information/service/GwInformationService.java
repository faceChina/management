package com.jzwgj.management.server.web.information.service;

import com.jzwgj.management.exception.GwInformationException;
import com.jzwgj.management.server.web.information.domain.GwInformation;
import com.zjlp.face.util.page.Pagination;


public interface GwInformationService {
	
	/**
	 * 新增资讯
	 * @param gwInformation
	 * @return
	 * @throws GwInformationException
	 */
	Long add(GwInformation gwInformation) throws GwInformationException;
	
	/**
	 * 根据ID获取资讯
	 * @param ids
	 * @return
	 * @throws GwInformationException
	 */
	GwInformation getGwInformation(Long id) throws GwInformationException;
	
	/**
	 * 修改资讯
	 * @param gwInformation
	 * @return
	 * @throws GwInformationException
	 */
	boolean edit(GwInformation gwInformation) throws GwInformationException;
	
	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteById(Long[] ids);

	Pagination<GwInformation> findGwInformationPageList(GwInformation gwInformation, Pagination<GwInformation> pagination);

	/**
	 * 排序
	 * @param sort
	 * @param ids
	 */
	void updateSort(Integer sort, String ids);
}
