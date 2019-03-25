package com.winter.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winter.mapper.ConnectorTableMapper;
import com.winter.mapper.SqlTableMapper;
import com.winter.mapper.UserMapper;
import com.winter.model.ConnectorTable;
import com.winter.model.ConnectorTableExample;
import com.winter.model.SqlTable;
import com.winter.model.SqlTableExample;
import com.winter.model.User;
import com.winter.service.SqlService;
import com.winter.utils.MysqlWFToolsOnline;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/8/16.
 */

@Transactional
@Component
@Service(value = "sqlService")
public class SqlServiceImpl implements SqlService {

    @Autowired
    private SqlTableMapper sqlTableMapper;//这里会报错，但是并不会影响
    
    @Autowired
	private ConnectorTableMapper connectMapper;

    @Override
    public int addSql(SqlTable sqlTable) {

    	if(sqlTable.getSqlid()==null)
    	{
    		String sqlIdUUID = UUID.randomUUID().toString();
    		System.out.println(sqlIdUUID);
    		sqlTable.setSqlid(sqlIdUUID);
    	}
        return sqlTableMapper.insertSelective(sqlTable);
    }

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @Override
    public List<SqlTable> findAllSql(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum,pageSize);
        //startPage后紧跟的这个查询就是分页查询  
        List<SqlTable> sqlList = sqlTableMapper.selectAll();
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以  
        PageInfo<SqlTable> pageInfo = new PageInfo<>(sqlList,pageSize);  
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数  
        return pageInfo.getList();
    }

	@Override
	public PageInfo findSqlPageFilter(int pageNum, int pageSize) {
		//将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum,pageSize);
        //startPage后紧跟的这个查询就是分页查询  
        List<SqlTable> sqlList = sqlTableMapper.selectAll();
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以  
        PageInfo<SqlTable> pageInfo = new PageInfo<>(sqlList,pageSize);  
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数  
        return pageInfo;
	}

	@Override
	public List<SqlTable> getAllSql() {
		return sqlTableMapper.selectAll();
	}

	@Override
	public int addSelective(SqlTable sqlTable) {
		sqlTableMapper.insertSelective(sqlTable);
		return 0;
	}

	@Override
	public int executeSqlByIdAndDBId(String sqlId, String dbSourceId) {
		ConnectorTableExample cExample = new ConnectorTableExample();
		cExample.createCriteria().andConidEqualTo(dbSourceId);
		List<ConnectorTable> conList = connectMapper.selectByExample(cExample);
		SqlTableExample example = new SqlTableExample();
		example.createCriteria().andSqlidEqualTo(sqlId);
		List<SqlTable> sqlTableList = sqlTableMapper.selectByExample(example);
		for(ConnectorTable conFor:conList)
		{
			List<String> sqlList = new ArrayList<String>();
			for(SqlTable sqlFor: sqlTableList)
			{
				String sqltContentFor = sqlFor.getSqlcontent();
				List<String> sqlOnLineList = Arrays.asList(sqltContentFor.split(";"));
 				for(String sqlOneLineStringFor :sqlOnLineList)
 					sqlList.add(sqlOneLineStringFor);
			}
			try {
				execInsertSql(sqlList,conFor);
			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
		}
		
		
		return 0;
	}
	private static Connection dbConnect(String[] dbInfo) {
		try {
			Class.forName(dbInfo[0]).newInstance();
			return DriverManager.getConnection(dbInfo[1], dbInfo[2], dbInfo[3]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static boolean execInsertSql(List<String> sqlList,ConnectorTable connectAtt) throws SQLException {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		/*tring[] targetDb = {
				"com.mysql.jdbc.Driver",
				"jdbc:mysql://172.15.100.151:3306/adp?useUnicode=true&amp;characterEncoding=utf8&amp;noAccessToProcedureBodies=true",
				"adp", "adp" };*/
		String[] targetDb = {
				connectAtt.getCondrivername(),connectAtt.getConurl(),
				connectAtt.getConusername(), connectAtt.getConpassword() };
		try {
			conn = dbConnect(targetDb);
			st = conn.createStatement();
			
			
			for (String s : sqlList) {
				
				System.out.println(s);
				if(MysqlWFToolsOnline.judgeSqlExecute(s))
				{
				//st.addBatch(s);
				System.out.println("执行");
				st.execute(s);
				}else
					continue;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			st.close();
			conn.close();
		}
		return true;
	}

	@Override
	public int editSelective(SqlTable sqlTable) {
		SqlTableExample sqlTableExample = new SqlTableExample();
		sqlTableExample.createCriteria().andSqlidEqualTo(sqlTable.getSqlid());
		sqlTableMapper.updateByExampleSelective(sqlTable, sqlTableExample);
		return 0;
	}

	@Override
	public boolean deleteBySqlIds(List<String> sqlIds) {
		try {
			SqlTableExample sqlExample = new SqlTableExample();
			sqlExample.createCriteria().andSqlidIn(sqlIds);
			sqlTableMapper.deleteByExample(sqlExample);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public PageInfo<SqlTable> findSqlPageFilterAndImportOrNot(int pageNum, int pageSize, String importOrNot) {
		//将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum,pageSize);
        SqlTableExample example = new SqlTableExample();
        example.setOrderByClause("sqlOrder");
        if(importOrNot.equals("import"))
        {
        example.createCriteria().andSqlsortLike("import");
        }
        else {
        example.createCriteria().andSqlsortNotEqualTo("import");
        }
        //startPage后紧跟的这个查询就是分页查询  
        List<SqlTable> sqlList = sqlTableMapper.selectByExample(example);
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以  
        PageInfo<SqlTable> pageInfo = new PageInfo<>(sqlList,pageSize);  
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数  
        return pageInfo;
	}

	@Override
	public String exportSqlByIdAndDBId(String sqlId, String dbSourceId) {
		String returnId = "0";
		MysqlWFToolsOnline sqlTool = new MysqlWFToolsOnline();
		ConnectorTableExample cExample = new ConnectorTableExample();
		cExample.createCriteria().andConidEqualTo(dbSourceId);
		List<ConnectorTable> conList = connectMapper.selectByExample(cExample);
		SqlTableExample example = new SqlTableExample();
		example.createCriteria().andSqlidEqualTo(sqlId);
		List<SqlTable> sqlTableList = sqlTableMapper.selectByExample(example);
		for(ConnectorTable conFor:conList)
		{
			List<String> sqlList = new ArrayList<String>();
			String[] sourceDbString = {conFor.getCondrivername(),conFor.getConurl(),conFor.getConusername(),conFor.getConpassword()};
			sqlTool.setSourceDb(sourceDbString);
			for(SqlTable sqlFor: sqlTableList)
			{
				String sqltContentFor = sqlFor.getSqlcontent();
				List<String> sqlOnLineList = Arrays.asList(sqltContentFor.split(";"));
 				for(String sqlOneLineStringFor :sqlOnLineList)
 					sqlList.add(sqlOneLineStringFor);
			}
			try {
				sqlTool.exportInserSql(sqlTool.readList(sqlList));
				int i = 0;
				for(String outSqlOne:sqlTool.getOutSqlList())
				{
					System.out.println(i++);
					System.out.println(outSqlOne);
				}
				System.out.println(sqlTool.getOutSqlList());
				List<String> convertSqlList = sqlTool.getConvertSqlList();//execSqlList
				/*i = 0;
				for(String outSqlOne:convertSqlList)
				{
					System.out.println(i++);
					System.out.println(outSqlOne);
					System.out.println(outSqlOne.length());
				}*/
				//execInsertSql(sqlList,conFor);
				//returnId = addConcerSqlTableBySqlString(StringUtils.join(convertSqlList, ';'));
				returnId = addConcerSqlListTableBySqlString(convertSqlList);
				
				ConnectorTableExample exampleCo = new ConnectorTableExample();
				String sortIds = conFor.getSortids()==null?"":conFor.getSortids();
				if(sortIds.indexOf(returnId)==-1)
				{
					List<String> sortIdNList = new ArrayList<String>();
					List<String> sortIdOList = Arrays.asList(sortIds.split(","));
					System.out.println(returnId);
					sortIdNList.addAll(sortIdOList);
					sortIdNList.add(returnId);
					conFor.setSortids(StringUtils.join(sortIdNList,','));
					exampleCo.createCriteria().andConidEqualTo(conFor.getConid());
					connectMapper.updateByExample(conFor, exampleCo);
				}
				//System.out.println(StringUtils.join(convertSqlList, ';'));
			} catch (Exception e) {
				e.printStackTrace();
				return "-1";
			}
		}
		
		
		return returnId;
	}
	
	public String addConcerSqlTableBySqlString(String convertSqlListString) {
		SqlTable sqlTable  = new SqlTable();
		String sqlIdUU  = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 7);
		sqlTable.setSqlid(sqlIdUU);
		sqlTable.setSqlcontent(convertSqlListString);
		sqlTable.setSqlsort("import");
		this.addSelective(sqlTable);
		return sqlIdUU;
	}
	public String addConcerSqlListTableBySqlString(List<String> convertSqlList) {
		String sortId  = Math.round(Math.random()*100000)+"";
		Integer i = 0;
		for(String convertSqlListString :convertSqlList)
		{
		SqlTable sqlTable  = new SqlTable();
		String sqlIdUU  = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 7);
		sqlTable.setSqlid(sqlIdUU);
		sqlTable.setSqlcontent(convertSqlListString);
		sqlTable.setSqlsort("import");
		sqlTable.setSqlorder(""+(i++));
		sqlTable.settSortSortid(Integer.parseInt(sortId));
		this.addSelective(sqlTable);
		}
		return sortId;
	}
	public static void main(String args[])
	{
		System.out.println(Math.round(Math.random()*100000));
	}

	@Override
	public int executeSqlBysortIdAndDBId(String sortId, String dbSourceId) {
		ConnectorTableExample cExample = new ConnectorTableExample();
		cExample.createCriteria().andConidEqualTo(dbSourceId);
		List<ConnectorTable> conList = connectMapper.selectByExample(cExample);
		//SqlTableExample example = new SqlTableExample();
		//example.createCriteria().(sqlId);
		List<SqlTable> sqlTableList = sqlTableMapper.selectSqlBySortId(Integer.parseInt(sortId));
		for(ConnectorTable conFor:conList)
		{
			List<String> sqlList = new ArrayList<String>();
			for(SqlTable sqlFor: sqlTableList)
			{
				String sqltContentFor = sqlFor.getSqlcontent();
 				sqlList.add(sqltContentFor);
			}
			try {
				execInsertSql(sqlList,conFor);
			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
		}
		
		
		return 0;
	}

	@Override
	public PageInfo<SqlTable> findSqlPageFilterBySortId(String sortId) {
		
        //startPage后紧跟的这个查询就是分页查询  
        List<SqlTable> sqlList = sqlTableMapper.selectSqlBySortId(Integer.parseInt(sortId));
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(0,sqlList.size());
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以  
        PageInfo<SqlTable> pageInfo = new PageInfo<>(sqlList,sqlList.size());  
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数  
        return pageInfo;
	}

	@Override
	public boolean executeOneSqlByIdAndDBId(String sqlId, String dbSourceId) {
		ConnectorTableExample cExample = new ConnectorTableExample();
		cExample.createCriteria().andConidEqualTo(dbSourceId);
		List<ConnectorTable> conList = connectMapper.selectByExample(cExample);
		SqlTableExample example = new SqlTableExample();
		example.createCriteria().andSqlidEqualTo(sqlId);
		List<SqlTable> sqlTableList = sqlTableMapper.selectByExample(example);
		for(ConnectorTable conFor:conList)
		{
			List<String> sqlList = new ArrayList<String>();
			for(SqlTable sqlFor: sqlTableList)
			{
				sqlList.add(sqlFor.getSqlcontent());
			}
			try {
				return execInsertSql(sqlList,conFor);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
}