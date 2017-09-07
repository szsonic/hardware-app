package com.hardware.enums;

/**
 * 文件类型枚举类<br>
 * PHOTO("图片"), SOUND("声音"), VIDEO("视频"), DOCUMENT("文档")
 * 
 * @author shenpeng
 * 
 */
public enum FileType {

	PHOTO("图片"), SOUND("声音"), VIDEO("视频"), DOCUMENT("文档");

	// 枚举说明
	private String desc;

	/**
	 * 私有的构造方法
	 */
	private FileType(String desc) {
		this.desc = desc;
	}

	/**
	 * 获取说明内容
	 */
	public String getDesc() {
		return desc;
	}
}