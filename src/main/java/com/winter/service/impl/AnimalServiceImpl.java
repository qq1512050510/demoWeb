package com.winter.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winter.mapper.AnimalMapper;
import com.winter.model.Animal;
import com.winter.service.AnimalService;  
  
  
@Service  
public class AnimalServiceImpl implements AnimalService {  
      
    @Autowired  
    private AnimalMapper dao;  
  
    @Override  
    public List<Animal> getAllAnimals() {  
        return dao.selectAll();  
    }  
  
    @Override  
    public int insertOne(Animal entity) {  
        return dao.insertSelective(entity);  
    }  
  
}
