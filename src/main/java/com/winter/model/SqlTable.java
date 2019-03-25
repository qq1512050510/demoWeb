package com.winter.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class SqlTable {
	
	@Range(min = 1, message="数量: 必须大于0")  
    @NotNull(message="不能为空") 
    private String sqlid;

	@NotEmpty(message=" 不能为空") 
    private String sqlcontent;

    private String sqldetails;

    private Integer tSortSortid;

    private String sqlsort;
    
    private String sqlorder;
    
	public String getSqlorder() {
		return sqlorder;
	}

	public void setSqlorder(String sqlorder) {
		this.sqlorder = sqlorder;
	}

	public String getSqlid() {
        return sqlid;
    }

    public void setSqlid(String sqlid) {
        this.sqlid = sqlid;
    }

    public String getSqlcontent() {
        return sqlcontent;
    }

    public void setSqlcontent(String sqlcontent) {
        this.sqlcontent = sqlcontent;
    }

    public String getSqldetails() {
        return sqldetails;
    }

    public void setSqldetails(String sqldetails) {
        this.sqldetails = sqldetails;
    }

    public Integer gettSortSortid() {
        return tSortSortid;
    }

    public void settSortSortid(Integer tSortSortid) {
        this.tSortSortid = tSortSortid;
    }

    public String getSqlsort() {
        return sqlsort;
    }

    public void setSqlsort(String sqlsort) {
        this.sqlsort = sqlsort;
    }
}