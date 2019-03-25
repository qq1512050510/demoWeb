package com.winter.mapper;

import com.winter.model.ConnectorTable;
import com.winter.model.ConnectorTableExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConnectorTableMapper {
    long countByExample(ConnectorTableExample example);

    int deleteByExample(ConnectorTableExample example);

    int insert(ConnectorTable record);

    int insertSelective(ConnectorTable record);

    List<ConnectorTable> selectByExample(ConnectorTableExample example);

    int updateByExampleSelective(@Param("record") ConnectorTable record, @Param("example") ConnectorTableExample example);

    int updateByExample(@Param("record") ConnectorTable record, @Param("example") ConnectorTableExample example);

	List<ConnectorTable> selectAll();
}