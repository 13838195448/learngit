package com.mpyf.lening.interfaces.bean.Parame;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileUpload extends ParameBase{

	@JsonProperty("fileName")
	private String fileName;
	
	@JsonProperty("fileType")
	private String fileType;
	
	@JsonProperty("Content")
	private byte[] Content;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getContent() {
		return Content;
	}

	public void setContent(byte[] content) {
		Content = content;
	}

	
	
}
