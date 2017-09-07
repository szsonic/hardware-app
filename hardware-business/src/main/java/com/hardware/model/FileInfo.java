package com.hardware.model;

import com.hardware.enums.FileType;
import com.support.model.base.BaseModel;

import javax.persistence.*;

/**
 * 文件资源信息模型（包含了系统中所有的资源文件如：图片，声音，视频等信息的资源文件信息路径）
 * 
 * @author shenpeng
 * 
 */
@Entity
@Table(name = "file_info")
@org.hibernate.annotations.Table(appliesTo = "file_info", comment = "文件资源信息模型（包含了系统中所有的资源文件如：图片，声音，视频等信息的资源文件信息路径）")
public class FileInfo extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 文件路径
	 */
	@Column(length=128)
	private String filePath;
	/**
	 * 文件名称
	 */
	@Column(length=64)
	private String fileName;
	/**
	 * 文件类型（PHOTO图片SOUND声音VIDEO视频）
	 */
	@Enumerated(EnumType.STRING)
	@Column(length=32)
	private FileType fileType;
	
	private String fullPathName;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public String getFullPathName() {
		return fullPathName;
	}

	public void setFullPathName(String fullPathName) {
		this.fullPathName = fullPathName;
	}

}
