package com.winter.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.winter.form.AnimalForm;
import com.winter.model.Animal;
import com.winter.model.SqlTable;
import com.winter.service.AnimalService;
import com.winter.service.SqlService;

@RestController
@RequestMapping("/data")
public class testTable {

	@Autowired
	private SqlService sqlService;
	
	@Autowired
	private AnimalService service;

	@RequestMapping("/main")
	public String index(Model model) {
		model.addAttribute("loginName", "admin");
		model.addAttribute("loginId", "27");
		return "test";
	}

	// thymeleaf Demo
	@RequestMapping(value = "/greeting")
	public ModelAndView test(ModelAndView mv) {
		mv.setViewName("/greeting");
		mv.addObject("title", "欢迎使用Thymeleaf!!!!!");
		return mv;
	}

	// thymeleaf Demo
	@RequestMapping(value = "/testTable")
	public ModelAndView index(ModelAndView mv) {
		mv.setViewName("/testTable");
		mv.addObject("loginName", "admin");
		mv.addObject("loginId", "27");
		SqlTable sqlTable = new SqlTable();
		List<SqlTable> sqlList = sqlService.findAllSql(1, 10);
		mv.addObject("animalForm", sqlTable);
		mv.addObject("animalsList", sqlList);
		/*
		 * List<AnimalEntity> animals = service.getAllAnimals(); ModelAndView mav = new
		 * ModelAndView(); mv.setViewName("zoolist"); mv.addObject("animalForm", new
		 * AnimalForm()); mv.addObject("animalsList", animals);
		 */
		return mv;
	}

	@RequestMapping(path = "/list", params = { "save" }, method = RequestMethod.POST)
	public String doAdd(Model model, @Valid AnimalForm form, BindingResult result) {
		System.out.println("动物名：" + form.getOname());
		System.out.println("数量：" + form.getOcount());
		System.out.println("备注：" + form.getMemo());
		if (result.hasErrors()) {
			model.addAttribute("MSG", "出错啦！");
		} else {
			model.addAttribute("MSG", "提交成功！");
		}
		return "zoolist";
	}

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public ModelAndView showZooList() {
		// 从数据库取出数据
		List<Animal> animals = service.getAllAnimals();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("zoolist");
		mav.addObject("animalForm", new AnimalForm());
		mav.addObject("animalsList", animals);
		return mav;
	}
}
