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
@RequestMapping(value = "/layout")
public class LayoutController {

    
    @RequestMapping(path = "/layout", method = RequestMethod.GET)
    public ModelAndView showZooList(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("user_name", "admin");
        mav.setViewName("layout");
        return mav;
    }
    
}