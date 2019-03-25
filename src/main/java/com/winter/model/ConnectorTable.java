package com.winter.model;

public class ConnectorTable {
    private String conid;

    private String conurl;

    private String conusername;

    private String conpassword;

    private String consqltype;

    private String condrivername;

    private String sortids;
    
    public String getSortids() {
		return sortids;
	}

	public void setSortids(String sortids) {
		this.sortids = sortids;
	}

	public String getConid() {
        return conid;
    }

    public void setConid(String conid) {
        this.conid = conid;
    }

    public String getConurl() {
        return conurl;
    }

    public void setConurl(String conurl) {
        this.conurl = conurl;
    }

    public String getConusername() {
        return conusername;
    }

    public void setConusername(String conusername) {
        this.conusername = conusername;
    }

    public String getConpassword() {
        return conpassword;
    }

    public void setConpassword(String conpassword) {
        this.conpassword = conpassword;
    }

    public String getConsqltype() {
        return consqltype;
    }

    public void setConsqltype(String consqltype) {
        this.consqltype = consqltype;
    }

    public String getCondrivername() {
        return condrivername;
    }

    public void setCondrivername(String condrivername) {
        this.condrivername = condrivername;
    }
}