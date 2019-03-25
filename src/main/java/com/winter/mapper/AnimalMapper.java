package com.winter.mapper;

import com.winter.model.Animal;
import com.winter.model.AnimalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AnimalMapper {
    long countByExample(AnimalExample example);

    int deleteByExample(AnimalExample example);

    int insert(Animal record);

    int insertSelective(Animal record);

    List<Animal> selectByExample(AnimalExample example);

    int updateByExampleSelective(@Param("record") Animal record, @Param("example") AnimalExample example);

    int updateByExample(@Param("record") Animal record, @Param("example") AnimalExample example);

	List<Animal> selectAll();
}