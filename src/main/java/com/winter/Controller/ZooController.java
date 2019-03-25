package com.winter.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.winter.form.AnimalForm;
import com.winter.model.Animal;
import com.winter.service.AnimalService;


@Controller
@RequestMapping(value = "/zoo")
public class ZooController {

    @Autowired
    private AnimalService service;
    
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ModelAndView showZooList(){
        //从数据库取出数据
        List<Animal> animals = service.getAllAnimals();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("zoolist");
        mav.addObject("animalForm", new AnimalForm());
        mav.addObject("animalsList", animals);
        return mav;
    }
    
    @RequestMapping(path = "/list", params = {"save"}, method = RequestMethod.POST)
    public String doAdd(Model model, @Valid AnimalForm form, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("MSG", "出错啦！");
        }else{
            //保存数据到数据库
            service.insertOne(this.copyDataFromForm2Entity(form));
            model.addAttribute("MSG", "提交成功！");
        }
        //从数据库取出数据
        List<Animal> animals = service.getAllAnimals();
        model.addAttribute("animalsList", animals);
        return "zoolist";
    }
    
    //把form里的数据copy到entity中
    private Animal copyDataFromForm2Entity(AnimalForm form){
        Animal entity = new Animal();
        entity.setName(form.getOname());
        entity.setCount(Integer.valueOf(form.getOcount()));
        entity.setMemo(form.getMemo());
        return entity;
    }
}