package com.jzwgj.management.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.jzwgj.management.server.web.activity.domain.SlcoinInvitationRegister;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class SimpleExcelWrite {
	
	public void createExcel(OutputStream os,List<SlcoinInvitationRegister> list) throws WriteException,IOException{
		//创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //创建新的一页
        WritableSheet sheet = workbook.createSheet("First Sheet",0);
        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
        Label createTimeLabel = new Label(0,0,"受邀注册时间");
        sheet.addCell(createTimeLabel);
        Label nickNameLabel = new Label(1,0,"受邀者昵称");
        sheet.addCell(nickNameLabel);
        Label loginAccountLabel = new Label(2,0,"注册账号");
        sheet.addCell(loginAccountLabel);
        Label amountLabel = new Label(3,0,"注册送颜值");
        sheet.addCell(amountLabel);
        
        Label invitationNickNameLabel = new Label(4,0,"邀请者昵称");
        sheet.addCell(invitationNickNameLabel);
        Label invitationLoginAccountLabel = new Label(5,0,"邀请账号");
        sheet.addCell(invitationLoginAccountLabel);
        Label invitationAmountLabel = new Label(6,0,"邀请者送颜值");
        sheet.addCell(invitationAmountLabel);
        
        Label superNickNameLabel = new Label(7,0,"邀请者上级");
        sheet.addCell(superNickNameLabel);
        Label superLoginAccountLabel = new Label(8,0,"上级邀请账号");
        sheet.addCell(superLoginAccountLabel);
        Label superAmountLabel = new Label(9,0,"送颜值");
        sheet.addCell(superAmountLabel);
        
        for(int i=0;i<list.size();i++){
        	SlcoinInvitationRegister slcoinInvitationRegister = list.get(i);
        	Date createTime = slcoinInvitationRegister.getCreateTime();
			String nickName = slcoinInvitationRegister.getNickName();
			String loginAccount = slcoinInvitationRegister.getLoginAccount();
			Integer amount = slcoinInvitationRegister.getAmount();
			
			String invitationNickName = slcoinInvitationRegister.getInvitationNickName();
		    String invitationLoginAccount = slcoinInvitationRegister.getInvitationLoginAccount();
			Integer invitationAmount = slcoinInvitationRegister.getInvitationAmount();

		    String superNickName = slcoinInvitationRegister.getSuperNickName();
		    String superLoginAccount = slcoinInvitationRegister.getSuperLoginAccount();
		    Integer superAmount = slcoinInvitationRegister.getSuperAmount();
		    
		    Label createTimeLabelData = new Label(0,i+1,DateFormatUtils.format(createTime, "yyyy-MM-dd HH:mm:ss"));
		    Label nickNameLabelData = new Label(1,i+1,nickName);
		    Label loginAccountLabelData = new Label(2,i+1,loginAccount);
		    Label amountLabelData = new Label(3,i+1,amount.toString());
		    
		    Label invitationNickNameLabelData = new Label(4,i+1,invitationNickName==null?"":invitationNickName);
		    Label invitationLoginAccountLabelData = new Label(5,i+1,invitationLoginAccount==null?"":invitationLoginAccount);
		    Label invitationAmountLabelData = new Label(6,i+1,invitationAmount==null?"":invitationAmount.toString());
		    
		    Label superNickNameLabelData = new Label(7,i+1,superNickName==null?"":superNickName);
		    Label superLoginAccountLabelData = new Label(8,i+1,superLoginAccount==null?"":superLoginAccount);
		    Label superAmountLabelData = new Label(9,i+1,superAmount==null?"":superAmount.toString());
		    
		    sheet.addCell(createTimeLabelData);
		    sheet.addCell(nickNameLabelData);
		    sheet.addCell(loginAccountLabelData);
		    sheet.addCell(amountLabelData);
		    
		    sheet.addCell(invitationNickNameLabelData);
		    sheet.addCell(invitationLoginAccountLabelData);
		    sheet.addCell(invitationAmountLabelData);
		    
		    sheet.addCell(superNickNameLabelData);
		    sheet.addCell(superLoginAccountLabelData);
		    sheet.addCell(superAmountLabelData);
        }
        
        //把创建的内容写入到输出流中，并关闭输出流
        workbook.write();
        workbook.close();
        os.close();
    }
}