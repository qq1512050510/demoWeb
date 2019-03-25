package com.winter.model;

public class SqlConnector {
    private String sqlid;

    private String conid;

    private String sqlstate;

    private String sqldialet;

    public String getSqlid() {
        return sqlid;
    }

    public void setSqlid(String sqlid) {
        this.sqlid = sqlid;
    }

    public String getConid() {
        return conid;
    }

    public void setConid(String conid) {
        this.conid = conid;
    }

    public String getSqlstate() {
        return sqlstate;
    }

    public void setSqlstate(String sqlstate) {
        this.sqlstate = sqlstate;
    }

    public String getSqldialet() {
        return sqldialet;
    }

    public void setSqldialet(String sqldialet) {
        this.sqldialet = sqldialet;
    }
}