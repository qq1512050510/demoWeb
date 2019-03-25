package com.winter.pagefilter;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.PageInfo;

public class PageFilterBoot<T> implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pageNum;
    private int pageSize;
    private int size;
    private long total;
    private int pages;
    private List<T> rows;
	public PageFilterBoot(PageInfo<T> pageInfo)
	{
		this.setRows(pageInfo.getList()); 
		this.setTotal(pageInfo.getTotal());
		this.setPageSize(pageInfo.getPageSize());
		this.setSize(pageInfo.getSize());
		this.setPages(pageInfo.getPages());
		
	}
	
  

	public int getPageNum() {
		return pageNum;
	}



	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}



	public int getPageSize() {
		return pageSize;
	}



	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}



	public int getSize() {
		return size;
	}



	public void setSize(int size) {
		this.size = size;
	}




	public long getTotal() {
		return total;
	}



	public void setTotal(long total) {
		this.total = total;
	}



	public int getPages() {
		return pages;
	}



	public void setPages(int pages) {
		this.pages = pages;
	}



	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
    
    
}
