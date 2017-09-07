package com.hardware.model;

import com.support.model.base.BaseModel;

import javax.persistence.*;

/**
 * 友情链接模型
 * 
 * @author shenpeng
 * 
 */
@Entity
@Table(name = "friend_link")
@org.hibernate.annotations.Table(appliesTo = "friend_link", comment = "友情链接模型")
public class FriendLink extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 链接地址
	 */
	@Column(columnDefinition = "varchar(128) not null comment '链接地址'")
	private String linkPath;
	/**
	 * 链接标题
	 */
	@Column(columnDefinition = "varchar(128) not null comment '链接标题'")
	private String linkTitle;

	/**
	 * 链接LOGO文件ID
	 */
	@Column(columnDefinition = "varchar(32) not null comment '链接LOGO文件ID'")
	private String linkLogoId;

	/**
	 * 所属网站ICON
	 */
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "linkLogoId", insertable = false, updatable = false)
	private FileInfo linkLogo;

	/**
	 * 统计点击数
	 */
	@Column(columnDefinition = "int(1) default 0 comment '统计点击数'")
	private Long clickNum;

	/**
	 * 获取linkPath属性
	 * 
	 * @return linkPath
	 */
	public String getLinkPath() {
		return linkPath;
	}

	/**
	 * 设置linkPath属性
	 * 
	 * @param linkPath
	 */
	public void setLinkPath(String linkPath) {
		this.linkPath = linkPath;
	}

	/**
	 * 获取linkTitle属性
	 * 
	 * @return linkTitle
	 */
	public String getLinkTitle() {
		return linkTitle;
	}

	/**
	 * 设置linkTitle属性
	 * 
	 * @param linkTitle
	 */
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	/**
	 * 获取linkLogoId属性
	 * 
	 * @return linkLogoId
	 */
	public String getLinkLogoId() {
		return linkLogoId;
	}

	/**
	 * 设置linkLogoId属性
	 * 
	 * @param linkLogoId
	 */
	public void setLinkLogoId(String linkLogoId) {
		this.linkLogoId = linkLogoId;
	}

	/**
	 * 获取clickNum属性
	 * 
	 * @return clickNum
	 */
	public Long getClickNum() {
		return clickNum;
	}

	/**
	 * 设置clickNum属性
	 * 
	 * @param clickNum
	 */
	public void setClickNum(Long clickNum) {
		this.clickNum = clickNum;
	}

	/**
	 * 获取linkLogo属性
	 * 
	 * @return linkLogo
	 */
	public FileInfo getLinkLogo() {
		return linkLogo;
	}

	/**
	 * 设置linkLogo属性
	 * 
	 * @param linkLogo
	 */
	public void setLinkLogo(FileInfo linkLogo) {
		this.linkLogo = linkLogo;
	}

}
