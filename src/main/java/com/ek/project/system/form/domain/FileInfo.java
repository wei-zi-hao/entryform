package com.ek.project.system.form.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author eric
 */
public class FileInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	  /**
     * id
     */
    private String id;

    /**
     * 相对路径
     */
    private String path;

    /**
     * 文件名
     */
    private String name;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 大小|字节B
     */
    private Integer size;

    /**
     * IP
     */
    private String ip;

    /**
     * 创建时间
     */
    private Date createTime;



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getSuffix() {
		return suffix;
	}



	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}



	public Integer getSize() {
		return size;
	}



	public void setSize(Integer size) {
		this.size = size;
	}



	public String getIp() {
		return ip;
	}



	public void setIp(String ip) {
		this.ip = ip;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileInfo [id=");
		builder.append(id);
		builder.append(", path=");
		builder.append(path);
		builder.append(", name=");
		builder.append(name);
		builder.append(", suffix=");
		builder.append(suffix);
		builder.append(", size=");
		builder.append(size);
		builder.append(", ip=");
		builder.append(ip);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append("]");
		return builder.toString();
	}









	
    

	
}
