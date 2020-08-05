package com.example.demo.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "messagemaster")
public class MessageMaster implements Serializable {
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 4049634572972929591L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chatid")
	private int chatId;

	@Column(name = "senderid")
	private int senderId;

	@Column(name = "receiverid")
	private int receiverId;

	@Column(name = "groupid")
	private String groupId;

	@Column(name = "seperateid")
	private String seperateid;

	@Column(name = "isenable")
	private int isenable;
	
	@Column(name = "sendername")
	private String senderName;
	
	@Column(name = "receivername")
	private String receiverName;


	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public int getIsEnable() {
		return isenable;
	}

	public void setIsEnable(int isEnable) {
		this.isenable = isEnable;
	}

	public int getChatId() {
		return chatId;
	}

	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSeperateid() {
		return seperateid;
	}

	public void setSeperateid(String seperateid) {
		this.seperateid = seperateid;
	}

}
