package com.winter.service;

import java.util.List;

import com.winter.model.Animal;
import com.winter.model.AnimalEntity;  
  
public interface AnimalService {  
  
    public List<Animal> getAllAnimals();  
      
    public int insertOne(Animal entity);  
}