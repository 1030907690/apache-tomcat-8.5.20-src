package com.zzq.oss;

public class UploadFile {
	
	private String ossPath;
	
	private String fileAbsolutePath;

	public UploadFile() {
	}
	
	public UploadFile(String ossPath,String fileAbsolutePath ) {
		this.ossPath = ossPath;
		this.fileAbsolutePath = ossPath;
	}
	
	public String getOssPath() {
		return ossPath;
	}

	public void setOssPath(String ossPath) {
		this.ossPath = ossPath;
	}

	public String getFileAbsolutePath() {
		return fileAbsolutePath;
	}

	public void setFileAbsolutePath(String fileAbsolutePath) {
		this.fileAbsolutePath = fileAbsolutePath;
	}
	
	

}
