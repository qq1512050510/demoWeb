package com.winter.Controller;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.winter.form.SqlForm;
import com.winter.model.SqlTable;
import com.winter.pagefilter.PageFilterBoot;
import com.winter.service.SqlService;
import com.winter.utils.MapEntityConverse;

/**
 * 
 */
@RestController
@RequestMapping(value = "/sql")
public class SqlController {

    @Autowired
    private SqlService sqlService;

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(SqlTable sqlTable){
        return sqlService.addSql(sqlTable);
    }
    
    @RequestMapping(path = "/list", params = {"save"}, method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ModelAndView doAdd(Model model, @Valid SqlTable sqlTable, BindingResult result){
    	ModelAndView mav = new ModelAndView();
    	PageInfo<SqlTable> sqlPageInfo = new PageInfo<SqlTable>();
    	System.out.println(result);
        if(result.hasErrors()){
        	mav.addObject("MSG", "出错啦！");
        }else{
            //保存数据到数据库
        	sqlService.addSql(sqlTable);
        	mav.addObject("MSG", "提交成功！");
        }
        //从数据库取出数据
        List<SqlTable> sqlList = sqlService.getAllSql();
        sqlPageInfo.setList(sqlList);
        mav.addObject("model", model);
        mav.setViewName("sqllist");
        mav.addObject("sqlForm", sqlTable);
        mav.addObject("sqlPageInfo", sqlPageInfo);
        return mav;
    }
    @RequestMapping(value = "/getAllSql", produces = {"application/json;charset=UTF-8"})
    public ModelAndView getAllSql(){
        //从数据库取出数据
    	 List<SqlTable> sqlList = sqlService.getAllSql();
         ModelAndView mav = new ModelAndView();
         mav.setViewName("sqllist1");
         mav.addObject("sqlForm", new SqlForm());
         mav.addObject("sqlList", sqlList);
         return mav;
    }
    @RequestMapping(path = "/list", params = {"add"}, method = RequestMethod.POST)
    public String doAdd(Model model, @Valid SqlForm sqlForm, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("MSG", "出错啦！");
        }else{
            //保存数据到数据库
        	sqlService.addSql(this.copyDataFromForm2Entity(sqlForm));
            model.addAttribute("MSG", "提交成功！");
        }
        //从数据库取出数据
        List<SqlTable> sqlList = sqlService.getAllSql();
        model.addAttribute("sqlList", sqlList);
        return "sqllist1";
    }

    @ResponseBody
    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){

        return sqlService.findAllSql(pageNum,pageSize);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/getSqlByPagefilter/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.GET, RequestMethod.POST} )
    public PageFilterBoot<SqlTable> getSqlByPagefilter(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestParam Map<String, Object> paras){
    	if(paras.get("cp")!=null)
    	{
    		pageNum = Integer.parseInt((String) paras.get("cp"));
    	}
    	if(paras.get("ps")!=null)
    	{
    		pageSize = Integer.parseInt((String) paras.get("ps"));
    	}
    	PageFilterBoot<SqlTable> pageFilter = new PageFilterBoot<SqlTable>(sqlService.findSqlPageFilter(pageNum,pageSize));
        return pageFilter;
    }
    
    @RequestMapping(value = "/getPagefilter/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public ModelAndView getPagefilter(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        //从数据库取出数据
        PageInfo<?> sqlPageInfo = sqlService.findSqlPageFilter(pageNum,pageSize);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sqllist");
        mav.addObject("sqlForm", new SqlTable());
        mav.addObject("sqlPageInfo", sqlPageInfo);
        return mav;
    }
    @RequestMapping(value = "/getSqlByPagefilterAndImport/{pageNum}/{pageSize}/{importOrNot}", produces = {"application/json;charset=UTF-8"})
    public PageFilterBoot<SqlTable> getSqlByPagefilterAndImport(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize,@PathVariable("importOrNot") String importOrNot, @RequestParam Map<String, Object> paras){
        //从数据库取出数据
        if(paras.get("cp")!=null)
    	{
    		pageNum = Integer.parseInt((String) paras.get("cp"));
    	}
    	if(paras.get("ps")!=null)
    	{
    		pageSize = Integer.parseInt((String) paras.get("ps"));
    	}
    	PageFilterBoot<SqlTable> pageFilter = new PageFilterBoot<SqlTable>(sqlService.findSqlPageFilterAndImportOrNot(pageNum,pageSize,importOrNot));
        return pageFilter;
    }
    @RequestMapping(value = "/selectSqlTableBySortId", produces = {"application/json;charset=UTF-8"})
    public PageFilterBoot<SqlTable> selectSqlTableBySortId(@RequestParam Map<String, Object> paras){
    	String sortId = (String) paras.get("sortId");
    	PageFilterBoot<SqlTable> pageFilter = new PageFilterBoot<SqlTable>(sqlService.findSqlPageFilterBySortId(sortId));
    	return pageFilter;
    }
    //把form里的数据copy到entity中
    private SqlTable copyDataFromForm2Entity(SqlForm form){
    	SqlTable entity = new SqlTable();
    	entity.setSqlcontent(form.getSqlcontent());
        entity.setSqlid(form.getSqlid());
        entity.setSqldetails(form.getSqldetails());
        entity.setSqlsort(form.getSqlsort());
        return entity;
    }
    
    
    /**
  	 * 新增实体实例
  	 * 
  	 * @param entityRid
  	 *            实体类型可读标识
  	 * @param entity
  	 *            实体实例数据
  	 * @return 实体实例数据，其中应该包含了新增实体实例的内部标识
       * @throws IntrospectionException 
       * @throws InvocationTargetException 
       * @throws InstantiationException 
       * @throws IllegalAccessException 
  	 */
  	@PostMapping("/executeSql")
  	public Map<String, Object> executeSql(@RequestParam Map<String, Object> attributeMap){
  		
  		Map<String, Object> returnMap = new HashMap<String,Object>();
  		String sqlId  = (String) attributeMap.get("sqlid");
  		String dbSourceId = (String) attributeMap.get("dbSourceId");
  		
  		try
  		{
  			sqlService.executeSqlByIdAndDBId(sqlId,dbSourceId);
  			returnMap.put("state", "success");
  		}catch (Exception e) {
  			returnMap.put("state", "error");
  			System.out.println(e.getMessage());
  		}
  		return returnMap;
  	}
  	
  	
  	
  	
  	
  	 /**
  	 * 新增实体实例
  	 * 
  	 * @param entityRid
  	 *            实体类型可读标识
  	 * @param entity
  	 *            实体实例数据
  	 * @return 实体实例数据，其中应该包含了新增实体实例的内部标识
       * @throws IntrospectionException 
       * @throws InvocationTargetException 
       * @throws InstantiationException 
       * @throws IllegalAccessException 
  	 */
  	@PostMapping("/executeSqlBysortId")
  	public Map<String, Object> executeSqlBysortId(@RequestParam Map<String, Object> attributeMap){
  		
  		Map<String, Object> returnMap = new HashMap<String,Object>();
  		String sortId  = (String) attributeMap.get("sortId");
  		String dbSourceId = (String) attributeMap.get("dbSourceId");
  		
  		try
  		{
  			sqlService.executeSqlBysortIdAndDBId(sortId,dbSourceId);
  			returnMap.put("state", "success");
  		}catch (Exception e) {
  			returnMap.put("state", "error");
  			System.out.println(e.getMessage());
  		}
  		return returnMap;
  	}
  	
  	
  	@PostMapping("/exportSql")
  	public Map<String, Object> exportSql(@RequestParam Map<String, Object> attributeMap){
  		
  		Map<String, Object> returnMap = new HashMap<String,Object>();
  		String sqlId  = (String) attributeMap.get("sqlid");
  		String dbSourceId = (String) attributeMap.get("dbSourceId");
  		
  		try
  		{
  			String insertSqlId = sqlService.exportSqlByIdAndDBId(sqlId,dbSourceId);
  			returnMap.put("insertSqlId", insertSqlId);
  			returnMap.put("state", "success");
  		}catch (Exception e) {
  			returnMap.put("state", "error");
  			System.out.println(e.getMessage());
  		}
  		return returnMap;
  	}
  	
 	@PostMapping("/executeSqlBySqlIdAndDBSourceId")
  	public Map<String, Object> executeSqlBySqlIdAndDBSourceId(@RequestParam Map<String, Object> attributeMap){
  		
  		Map<String, Object> returnMap = new HashMap<String,Object>();
  		String sqlId  = (String) attributeMap.get("sqlId");
  		String dbSourceId = (String) attributeMap.get("dbSourceId");
  		
  		try
  		{
  			boolean executeState = sqlService.executeOneSqlByIdAndDBId(sqlId,dbSourceId);
  			if(executeState)
  				returnMap.put("state", "success");
  			else
  				returnMap.put("state", "error");
  		}catch (Exception e) {
  			returnMap.put("state", "error");
  			System.out.println(e.getMessage());
  		}
  		return returnMap;
  	}
  	
  	@PostMapping("/addSqlSelective")
  	public Map<String, Object> addDatabaseSelective(@RequestParam Map<String, Object> sqlTableMap) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
  		
  		SqlTable sqlTable = new SqlTable();
  		
  		sqlTable  = (SqlTable) MapEntityConverse.convertMap(SqlTable.class,sqlTableMap);
  		
  		try
  		{
  			sqlService.addSelective(sqlTable);
  		}catch (Exception e) {
  			System.out.println(e.getMessage());
  		}
  		return sqlTableMap;
  	}
  	/**
	 * 編輯实体实例
	 * 
	 * @param entityRid
	 *            实体类型可读标识
	 * @param entity
	 *            实体实例数据
	 * @return 实体实例数据，其中应该包含了新增实体实例的内部标识
     * @throws IntrospectionException 
     * @throws InvocationTargetException 
     * @throws InstantiationException 
     * @throws IllegalAccessException 
	 */
 	@ResponseBody
	@PostMapping("/editSqlSelective")
	public Map<String, Object> editSqlSelective(@RequestParam Map<String, Object> sqlTableMap) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
 		SqlTable sqlTable = new SqlTable();
 		sqlTable  = (SqlTable) MapEntityConverse.convertMap(SqlTable.class,sqlTableMap);
		sqlService.editSelective(sqlTable);
		return sqlTableMap;
	}
 	
 	
	@ResponseBody
 	@PostMapping("/deleteSqlByIds")
 	public Map<String, Object> deleteSqlByIds(@RequestParam Map<String, Object> sqlIdsMap){
 		
 		Map<String, Object> returnMap = new HashMap<String,Object>();
 		List<String> sqlIds = new ArrayList<String>(); 
 		String sqlIdsString = (String) sqlIdsMap.get("conIds");
 		if(sqlIdsString!=null)
 		{
 			
 		sqlIds = Arrays.asList(sqlIdsString.split(","));
 		try {
 			sqlService.deleteBySqlIds(sqlIds);
 			returnMap.put("state", "success");
 		}catch(Exception e)
 		{
 			returnMap.put("state", "error");
 			e.printStackTrace();
 		}
 		}
 		else
 		{
 			returnMap.put("state", "deleteNull");
 		}
 		return returnMap;
 	}
  	
  	
}