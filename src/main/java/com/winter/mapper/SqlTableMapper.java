package com.winter.mapper;

import com.winter.model.SqlTable;
import com.winter.model.SqlTableExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SqlTableMapper {
    long countByExample(SqlTableExample example);

    int deleteByExample(SqlTableExample example);

    int insert(SqlTable record);

    int insertSelective(SqlTable record);

    List<SqlTable> selectByExample(SqlTableExample example);

    int updateByExampleSelective(@Param("record") SqlTable record, @Param("example") SqlTableExample example);

    int updateByExample(@Param("record") SqlTable record, @Param("example") SqlTableExample example);
    
	List<SqlTable> selectAll();
	
	List<SqlTable> selectSqlBySortId(@Param("sortId") int sortId);

}