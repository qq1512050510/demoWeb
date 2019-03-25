package com.winter.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/main")  
    public String index(Model model) {  
      model.addAttribute("loginName", "admin");  
      model.addAttribute("loginId", "27");  
      return "test";
    }
    //thymeleaf Demo
    @RequestMapping(value = "/greeting")
    public ModelAndView test(ModelAndView mv) {
        mv.setViewName("/greeting");
        mv.addObject("title","欢迎使用Thymeleaf!!!!!");
        return mv;
    }
    //thymeleaf Demo
    @RequestMapping(value = "/index")
    public ModelAndView index(ModelAndView mv) {
        mv.setViewName("/index");
        mv.addObject("loginName", "admin");
        mv.addObject("loginId", "27");
        return mv;
    }
}
