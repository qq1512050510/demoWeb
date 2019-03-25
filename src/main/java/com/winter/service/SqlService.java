package com.winter.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.winter.model.SqlTable;

/**
 * Created by Administrator on 2017/8/16.
 */
public interface SqlService {

    int addSql(SqlTable user);

    List<SqlTable> findAllSql(int pageNum, int pageSize);
    
    List<SqlTable> getAllSql();

    PageInfo<SqlTable> findSqlPageFilter(int pageNum, int pageSize);

	int addSelective(SqlTable sqlTable);

	int executeSqlByIdAndDBId(String sqlId, String dbSourceId);

	int editSelective(SqlTable sqlTable);

	boolean deleteBySqlIds(List<String> sqlIds);

	PageInfo<SqlTable> findSqlPageFilterAndImportOrNot(int pageNum, int pageSize, String importOrNot);

	String exportSqlByIdAndDBId(String sqlId, String dbSourceId);

	int executeSqlBysortIdAndDBId(String sortId, String dbSourceId);

	PageInfo<SqlTable> findSqlPageFilterBySortId(String sortId);

	boolean executeOneSqlByIdAndDBId(String sqlId, String dbSourceId);

}