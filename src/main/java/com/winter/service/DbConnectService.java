package com.winter.service;

import java.sql.Connection;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.winter.model.ConnectorTable;

/**
 * Created by Administrator on 2018/5/29.
 */
public interface DbConnectService {
	
	public void GetDbConnectSSO() throws Exception;
	public Connection getConnection();
	public boolean executeSqlBatch(List<String> sqlBatch);
	public boolean executeSql(String sqlBatch);
	
	public boolean GetDbConnectState();
	
	public List<ConnectorTable> getAllConnector();
	public PageInfo<ConnectorTable> findSqlPageFilter(int pageNum, int pageSize);
	public int addSelective(ConnectorTable connectorTable);
	public int editSelective(ConnectorTable connectorTable);
	public boolean deleteByConIds(List<String> dbIds);

}