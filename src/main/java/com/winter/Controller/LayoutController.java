package com.winter.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


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