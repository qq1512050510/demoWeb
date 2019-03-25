package com.winter.mapper;

import com.winter.model.SqlConnector;
import com.winter.model.SqlConnectorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SqlConnectorMapper {
    long countByExample(SqlConnectorExample example);

    int deleteByExample(SqlConnectorExample example);

    int insert(SqlConnector record);

    int insertSelective(SqlConnector record);

    List<SqlConnector> selectByExample(SqlConnectorExample example);

    int updateByExampleSelective(@Param("record") SqlConnector record, @Param("example") SqlConnectorExample example);

    int updateByExample(@Param("record") SqlConnector record, @Param("example") SqlConnectorExample example);
}