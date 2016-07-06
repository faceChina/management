package com.jzwgj.management.server.sys.domain;

import java.util.Date;

public class CAttachment {
	
    private Long id;

    private String name;

    private String memo;

    private String tableName;

    private Long tableId;
    
    private Integer type;
    
    private Integer isUser;

    private String path;

    private String fileType;

    private Long fileSize;

    private Date createTime;

    private Date updateTime;

    //查询参数
    private String attachFileIds;
    
    private String queryTypeFlag;
    
    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsUser() {
		return isUser;
	}

	public void setIsUser(Integer isUser) {
		this.isUser = isUser;
	}

	public String getQueryTypeFlag() {
		return queryTypeFlag;
	}

	public void setQueryTypeFlag(String queryTypeFlag) {
		this.queryTypeFlag = queryTypeFlag;
	}

	public String getAttachFileIds() {
		return attachFileIds;
	}

	public void setAttachFileIds(String attachFileIds) {
		this.attachFileIds = attachFileIds;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}