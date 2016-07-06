package com.jzwgj.management.server.web.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jzwgj.management.exception.ShuaLianUserException;
import com.jzwgj.management.server.web.activity.service.SlcoinRecordService;
import com.jzwgj.management.server.web.user.dao.ShuaLianUserDao;
import com.jzwgj.management.server.web.user.domain.ShuaLianUser;
import com.jzwgj.management.server.web.user.service.ShuaLianUserService;
import com.zjlp.face.account.service.AccountService;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.service.MemberCardService;
import com.zjlp.face.web.server.user.bankcard.business.BankCardBusiness;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.businesscard.business.BusinessCardBusiness;
import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;
import com.zjlp.face.web.server.user.user.domain.vo.UserVoObj;

@Service("shuaLianUserService")
public class ShuaLianUserServiceImpl implements ShuaLianUserService {
	
	Logger _logger = Logger.getLogger(getClass());
	
	@Autowired
	private ShuaLianUserDao shuaLianUserDao;
	
	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private BusinessCardBusiness businessCardBusiness;
	
	@Autowired
	private MemberCardService memberCardService;
	
	@Autowired(required=false)
	private AccountService accountService;
	
	@Autowired
	private SlcoinRecordService slcoinRecordService;
	
	@Autowired
	private BankCardBusiness bankCardBusiness;
	
	@Override
	public Pagination<ShuaLianUser> findUserPageList(ShuaLianUser shuaLianUser, Pagination<ShuaLianUser> pagination) throws ShuaLianUserException {
		try {
			UserVoObj userVoObj = new UserVoObj(shuaLianUser.getUser());
			userVoObj.setRegisterSourceType(User.REGISTERTYPE_DEFAULT);
			
			if(StringUtils.isNotBlank(shuaLianUser.getCreateTimeStart())){
				userVoObj.setCreateTimeStart(shuaLianUser.getCreateTimeStart() + " 00:00:00");
			}
			
			if(StringUtils.isNotBlank(shuaLianUser.getCreateTimeEnd())){
				userVoObj.setCreateTimeEnd(shuaLianUser.getCreateTimeEnd() + " 23:59:59");
			}
			
			Pagination<User> userPage = new Pagination<User>();
			userPage.setTotalRow(pagination.getTotalRow());
			userPage.setCurPage(pagination.getCurPage());
			userPage.setFromPage(pagination.getFromPage());
			userPage.setPageSize(pagination.getPageSize());
			userPage.setShowPageNum(pagination.getShowPageNum());
			userPage.setShowPageSize(pagination.getShowPageSize());
			userPage.setStart(pagination.getStart());
			userPage.setToPage(pagination.getToPage());
			
			userPage = userBusiness.findUserPageList(userVoObj, userPage);
			
			pagination.setTotalRow(userPage.getTotalRow());
			pagination.setCurPage(userPage.getCurPage());
			pagination.setFromPage(userPage.getFromPage());
			pagination.setPageSize(userPage.getPageSize());
			pagination.setShowPageNum(userPage.getShowPageNum());
			pagination.setShowPageSize(userPage.getShowPageSize());
			pagination.setStart(userPage.getStart());
			pagination.setToPage(userPage.getToPage());
			
			List<ShuaLianUser> shuaLianUserList = new ArrayList<ShuaLianUser>();
			
			for(User user : userPage.getDatas()){
				ShuaLianUser newShuaLianUser = new ShuaLianUser();
				newShuaLianUser.setUser(user);
				newShuaLianUser.setUserId(user.getId());
				newShuaLianUser = this.getShuaLianUserOthersInfo(newShuaLianUser);
				shuaLianUserList.add(newShuaLianUser);
			}
			
			pagination.setDatas(shuaLianUserList);
			return pagination;
		} catch (UserException e) {
			throw new ShuaLianUserException("查询刷脸用户列表失败:" + e.getMessage(),e);
		} catch (Exception e) {
			throw new ShuaLianUserException("查询刷脸用户列表失败:" + e.getMessage(),e);
		}
	}
	
	/**
	 * 查询刷脸用户其他数据
	 * @param shuaLianUser
	 * @return
	 */
	private ShuaLianUser getShuaLianUserOthersInfo(ShuaLianUser shuaLianUser){
		Long userId = shuaLianUser.getUserId();
		
		Integer shopNum = shuaLianUserDao.getUserShopNum(userId);
		UserVo userVo = userBusiness.getCountInvitationAmount(userId);
		
		shuaLianUser.setShopNum(shopNum);
		shuaLianUser.setInvitationNum(userVo.getCountMyInvitationAmount());
		shuaLianUser.setBisInvitationNum(userVo.getCountBisInvitationAmount());
		
		List<MemberCard> memberCardList = memberCardService.findMemberCardByUserId(userId);
		
		if(null!=memberCardList && memberCardList.size()>0){
			MemberCard memberCard = memberCardList.get(0);
			shuaLianUser.setMemberCard(memberCard);
		}
		
		BusinessCard businessCard = businessCardBusiness.getBusinessCardByUserId(userId);
		if(businessCard != null){
			shuaLianUser.setBusinessCard(businessCard);
		}
		
		return shuaLianUser;
	}
	
	@Override
	public ShuaLianUser getUserByUserId(Long userId) throws ShuaLianUserException {
		User user = userBusiness.getUserById(userId);
		ShuaLianUser shuaLianUser = new ShuaLianUser();
		shuaLianUser.setUser(user);
		shuaLianUser.setUserId(userId);
		shuaLianUser = getShuaLianUserOthersInfo(shuaLianUser);
		return shuaLianUser;
	}
	
	@Override
	@Transactional
	public void update(ShuaLianUser shuaLianUser) throws ShuaLianUserException {
		Long userId = shuaLianUser.getUserId();
		
		User user = new User();
		user.setId(userId);
		if(shuaLianUser.getUser().getMyInvitationCode()!=null && "".equals(shuaLianUser.getUser().getMyInvitationCode().trim())){
			user.setMyInvitationCode(null);
		}else{
			user.setMyInvitationCode(shuaLianUser.getUser().getMyInvitationCode());
		}
		user.setEmail(shuaLianUser.getUser().getEmail());
		userBusiness.updateShuaLianUser(user);

		BusinessCard newBusinessCard = shuaLianUser.getBusinessCard();
		if(!isNull(newBusinessCard)){
			newBusinessCard.setUserId(userId);
			BusinessCard oldBusinessCard = businessCardBusiness.getBusinessCardByUserId(userId);
			if(null != oldBusinessCard){
				newBusinessCard.setId(oldBusinessCard.getId());
				businessCardBusiness.updateBusinessCard(newBusinessCard);
			}else{
				businessCardBusiness.addBusinessCard(newBusinessCard);
			}
		}
	}
	
	private Boolean isNull(BusinessCard businessCard){
		if(businessCard == null) return true;
		if(StringUtils.isBlank(businessCard.getCompanyName()) && StringUtils.isBlank(businessCard.getIndustryProvide()) 
																&& StringUtils.isBlank(businessCard.getIndustryRequirement())
																&& StringUtils.isBlank(businessCard.getPosition())){
			return true;
		}
		
		return false;
	}
	
	@Override
	public Map<String, Integer> getUserNumMap() {
		Integer userSum = shuaLianUserDao.getUserSum();
		Integer todayAddUserNum = shuaLianUserDao.getTodayAddUserNum();
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("sum", userSum);
		map.put("today", todayAddUserNum);
		return map;
	}

	@Override
	public Boolean checkLpNo(String lpNo,Long userId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lpNo", lpNo);
		map.put("userId", userId);
		
		Integer count = shuaLianUserDao.getUserBylpNo(map);
		if(count > 0){
			return true;
		}
		return false;
	}

	@Override
	public ShuaLianUser userAccountInfo(Long userId) {
		ShuaLianUser shuaLianUser = new ShuaLianUser();
		User user = userBusiness.getUserById(userId);
		shuaLianUser.setUser(user);
		shuaLianUser.setUserId(userId);
		shuaLianUser.setAccount(accountService.getAccountByUserId(userId));
		shuaLianUser.setTotalSlcoin(slcoinRecordService.getHistoryTotalSlcoin(userId));
		
		Integer zf = bankCardBusiness.getCardNumber(userId,BankCard.USERFOR_PAY); //支付
		Integer tx = bankCardBusiness.getCardNumber(userId,BankCard.USERFOR_SETTLE); //提现
		shuaLianUser.setBankcardNumber(zf+tx);
		List<BankCard> listPay = bankCardBusiness.findPayCardList(userId); //支付
		List<BankCard> listSettle = bankCardBusiness.findSettleCardList(userId); //提现
		listPay.addAll(listSettle);
		if (null != listPay && listPay.size() > 0) {
			for (BankCard bankCard:listPay) {
				bankCard.setBankCard(this._getBankCard(bankCard.getBankCard()));
			}
		}
		shuaLianUser.setBankCardList(listPay);
		
		return shuaLianUser;
	}
	
	private String _getBankCard (String bankCard) {
		return bankCard.substring(bankCard.length()-4,bankCard.length());
	}
}
