package com.winter.Controller;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winter.model.ConnectorTable;
import com.winter.pagefilter.PageFilterBoot;
import com.winter.service.DbConnectService;
import com.winter.utils.MapEntityConverse;

/**
 * Created by Administrator on .
 */
@Controller
@RequestMapping(value = "/database")
public class DcController {

    @Autowired
    private DbConnectService dbService;


    @RequestMapping(value = "/getAll", produces = {"application/json;charset=UTF-8"})
    public void getAllSql(){
        //从数据库取出数据
    	try {
			//Connection connect = dbService.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @ResponseBody
    @RequestMapping(value = "/getAllConnector", produces = {"application/json;charset=UTF-8"})
    public Object getAllConnector(){
			
			
			return dbService.getAllConnector();
    }
    @ResponseBody
    @RequestMapping(value = "/getDcConnectorByPagefilter/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"}, method = { RequestMethod.GET, RequestMethod.POST} )
    public PageFilterBoot<ConnectorTable> getDcConnectorByPagefilter(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestParam Map<String, Object> paras){
    	if(paras.get("cp")!=null)
    	{
    		pageNum = Integer.parseInt((String) paras.get("cp"));
    	}
    	if(paras.get("ps")!=null)
    	{
    		pageSize = Integer.parseInt((String) paras.get("ps"));
    	}
    	PageFilterBoot<ConnectorTable> pageFilter = new PageFilterBoot<ConnectorTable>(dbService.findSqlPageFilter(pageNum,pageSize));
        return pageFilter;
    }
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addSqlTable(ConnectorTable connectorTable){
        return dbService.addSelective(connectorTable);
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
    @ResponseBody
	@PostMapping("/addDatabaseSelective")
	public Map<String, Object> addOne(@RequestParam Map<String, Object> connectorTableMap) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		
		ConnectorTable connectorTable = new ConnectorTable();
		
		connectorTable  = (ConnectorTable) MapEntityConverse.convertMap(ConnectorTable.class,connectorTableMap);
		
		dbService.addSelective(connectorTable);
		return connectorTableMap;
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
		@PostMapping("/editDatabaseSelective")
		public Map<String, Object> editDatabaseSelective(@RequestParam Map<String, Object> connectorTableMap) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
			
			ConnectorTable connectorTable = new ConnectorTable();
			connectorTable  = (ConnectorTable) MapEntityConverse.convertMap(ConnectorTable.class,connectorTableMap);
			dbService.editSelective(connectorTable);
			return connectorTableMap;
		}
	 	
	 	@ResponseBody
	 	@PostMapping("/deleteDbByIds")
	 	public Map<String, Object> deleteDbByIds(@RequestParam Map<String, Object> connectorIdsMap){
	 		
	 		Map<String, Object> returnMap = new HashMap<String,Object>();
	 		List<String> dbIds = new ArrayList<String>(); 
	 		String conIdsString = (String) connectorIdsMap.get("conIds");
	 		if(conIdsString!=null)
	 		{
	 			
	 		dbIds = Arrays.asList(conIdsString.split(","));
	 		try {
	 			dbService.deleteByConIds(dbIds);
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