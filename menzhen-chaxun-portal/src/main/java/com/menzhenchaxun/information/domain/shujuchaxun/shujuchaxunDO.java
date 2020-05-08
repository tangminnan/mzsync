package com.menzhenchaxun.information.domain.shujuchaxun;

import java.io.Serializable;
import java.util.Date;

public class shujuchaxunDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//id
	private String SMECICustomerID;
	//姓名
	private String SMECIName;
	//性别
	private String SMECISex;
	//出生年月
	private Date SMECIBirthday;
	//地址
	private String SMECIZone;
	//手机号
	private String SMECIPhone;
	//会员卡号
	private String SMECIMemberId;
	//注册日期
	private Date SMECIRegisterDate;
	//身份证号
	private String SMECIIdentityCard;
	private Date SMECILevelUpDate;
	
	public String getSMECICustomerID() {
		return SMECICustomerID;
	}
	public void setSMECICustomerID(String sMECICustomerID) {
		SMECICustomerID = sMECICustomerID;
	}
	public String getSMECIName() {
		return SMECIName;
	}
	public void setSMECIName(String sMECIName) {
		SMECIName = sMECIName;
	}
	public String getSMECISex() {
		return SMECISex;
	}
	public void setSMECISex(String sMECISex) {
		SMECISex = sMECISex;
	}
	public Date getSMECIBirthday() {
		return SMECIBirthday;
	}
	public void setSMECIBirthday(Date sMECIBirthday) {
		SMECIBirthday = sMECIBirthday;
	}
	public String getSMECIZone() {
		return SMECIZone;
	}
	public void setSMECIZone(String sMECIZone) {
		SMECIZone = sMECIZone;
	}
	public String getSMECIPhone() {
		return SMECIPhone;
	}
	public void setSMECIPhone(String sMECIPhone) {
		SMECIPhone = sMECIPhone;
	}
	public String getSMECIMemberId() {
		return SMECIMemberId;
	}
	public void setSMECIMemberId(String sMECIMemberId) {
		SMECIMemberId = sMECIMemberId;
	}
	public Date getSMECIRegisterDate() {
		return SMECIRegisterDate;
	}
	public void setSMECIRegisterDate(Date sMECIRegisterDate) {
		SMECIRegisterDate = sMECIRegisterDate;
	}
	public String getSMECIIdentityCard() {
		return SMECIIdentityCard;
	}
	public void setSMECIIdentityCard(String sMECIIdentityCard) {
		SMECIIdentityCard = sMECIIdentityCard;
	}
	public Date getSMECILevelUpDate() {
		return SMECILevelUpDate;
	}
	public void setSMECILevelUpDate(Date sMECILevelUpDate) {
		SMECILevelUpDate = sMECILevelUpDate;
	}
	
	
	
	

}
