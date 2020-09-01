package com.example.demo.model;

import java.util.Arrays;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FileUpload {

	@Id
	public ObjectId _id;
	@Indexed(unique = false)
	private String name;
	private String userId;
	private byte[] FileContent;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public byte[] getFileContent() {
		return FileContent;
	}

	public void setFileContent(byte[] bs) {
		FileContent = bs;
	}

	@Override
	public String toString() {
		return "FileUpload [_id=" + _id + ", name=" + name + ", userId=" + userId + ", FileContent="
				+ Arrays.toString(FileContent) + "]";
	}

}
