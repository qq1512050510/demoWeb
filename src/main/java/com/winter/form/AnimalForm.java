package com.winter.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

public class AnimalForm {

	private long id;

	@NotEmpty(message = "动物名: 不能为空")
	private String oname;

	@Range(min = 1, message = "数量: 必须大于0")
	@NotNull(message = "数量: 不能为空")
	private int ocount;

	@Size(max = 10, message = "备注: 长度不能超过10个字符")
	private String memo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

	public int getOcount() {
		return ocount;
	}

	public void setOcount(int ocount) {
		this.ocount = ocount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}