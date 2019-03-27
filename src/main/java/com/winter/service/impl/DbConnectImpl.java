package com.winter.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*//引入站点的配置信息
import config.Config;*/
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winter.config.bean.Config;
import com.winter.mapper.ConnectorTableMapper;
import com.winter.model.ConnectorTable;
import com.winter.model.ConnectorTableExample;
import com.winter.service.DbConnectService;

/**
 * 数据库连接生成类，返回一个数据库连接对象 构造函数完成数据库驱动的加载和数据的连接 提供数据库连接的取得和数据库的关闭方法
 * 
 * @author
 *
 */
@Transactional
@Service
public class DbConnectImpl implements DbConnectService {

	private static DruidDataSource dataSourceMDB = null;
	private static DruidDataSource dataSourceSSO = null;
	private static Connection conMDB = null;

	public String db;

	@Autowired
	private Config config;

	@Autowired
	private ConnectorTableMapper connectMapper;

	/**
	 * 构造函数完成数据库的连接和连接对象的生成
	 * 
	 * @throws Exception
	 */
	public DbConnectImpl() {

	}

	public void GetDbConnectSSO() throws Exception {
		try {

			if (dataSourceSSO == null) {

				dataSourceSSO = new DruidDataSource();

				// 设置连接参数
				dataSourceSSO.setUrl(config.getUrl());
				dataSourceSSO.setDriverClassName(config.getDriverclassname());
				dataSourceSSO.setUsername(config.getUsername());
				dataSourceSSO.setPassword(config.getPassword());
				// 配置初始化大小、最小、最大
				dataSourceSSO.setInitialSize(1);
				dataSourceSSO.setMinIdle(1);
				dataSourceSSO.setMaxActive(20);
				// 连接泄漏监测
				dataSourceSSO.setRemoveAbandoned(true);
				dataSourceSSO.setRemoveAbandonedTimeout(30);
				// 配置获取连接等待超时的时间
				dataSourceSSO.setMaxWait(20000);
				// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
				dataSourceSSO.setTimeBetweenEvictionRunsMillis(20000);
				// 防止过期
				dataSourceSSO.setValidationQuery("SELECT 'x'");
				dataSourceSSO.setTestWhileIdle(true);
				dataSourceSSO.setTestOnBorrow(true);

			}

		} catch (Exception e) {

			throw e;

		}

	}

	public void GetDbConnectMDB() throws Exception {
		try {

			if (dataSourceMDB == null) {

				dataSourceMDB = new DruidDataSource();

				// 设置连接参数
				dataSourceMDB.setUrl(config.getUrl());
				dataSourceMDB.setDriverClassName(config.getDriverclassname());
				dataSourceMDB.setUsername(config.getUsername());
				dataSourceMDB.setPassword(config.getPassword());
				// 配置初始化大小、最小、最大
				dataSourceMDB.setInitialSize(1);
				dataSourceMDB.setMinIdle(1);
				dataSourceMDB.setMaxActive(20);
				// 连接泄漏监测
				dataSourceMDB.setRemoveAbandoned(true);
				dataSourceMDB.setRemoveAbandonedTimeout(30);
				// 配置获取连接等待超时的时间
				dataSourceMDB.setMaxWait(20000);
				// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
				dataSourceMDB.setTimeBetweenEvictionRunsMillis(20000);
				// 防止过期
				dataSourceMDB.setValidationQuery("SELECT 'x'");
				dataSourceMDB.setTestWhileIdle(true);
				dataSourceMDB.setTestOnBorrow(true);

			}

		} catch (Exception e) {

			throw e;

		}

	}

	/**
	 * 取得已经构造生成的数据库连接
	 * 
	 * @return 返回数据库连接对象
	 * @throws Exception
	 */
	public Connection getConnection() {

		try {
			GetDbConnect();
			conMDB = dataSourceMDB.getConnection();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return conMDB;

	}

	private void GetDbConnect() {
		try {

			if (dataSourceMDB == null) {

				dataSourceMDB = new DruidDataSource();

				// 设置连接参数
				dataSourceMDB.setUrl(config.getUrl());
				dataSourceMDB.setDriverClassName(config.getDriverclassname());
				dataSourceMDB.setUsername(config.getUsername());
				dataSourceMDB.setPassword(config.getPassword());
				// 配置初始化大小、最小、最大
				dataSourceMDB.setInitialSize(1);
				dataSourceMDB.setMinIdle(1);
				dataSourceMDB.setMaxActive(20);
				// 连接泄漏监测
				dataSourceMDB.setRemoveAbandoned(true);
				dataSourceMDB.setRemoveAbandonedTimeout(30);
				// 配置获取连接等待超时的时间
				dataSourceMDB.setMaxWait(20000);
				// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
				dataSourceMDB.setTimeBetweenEvictionRunsMillis(20000);
				// 防止过期
				dataSourceMDB.setValidationQuery("SELECT 'x'");
				dataSourceMDB.setTestWhileIdle(true);
				dataSourceMDB.setTestOnBorrow(true);

			}

		} catch (Exception e) {

			throw e;

		}
	}

	@Override
	public boolean executeSqlBatch(List<String> sqlBatch) {
		if (conMDB == null) {
			getConnection();
		}

		for (String sqlFor : sqlBatch) {
			try {
				PreparedStatement pstm = conMDB.prepareStatement(sqlFor);
				ResultSet rs = pstm.executeQuery();
				if (rs.next()) {
					System.out.println("testPreparedStatement测试，FIXITEM_CODE = " + rs.getString("FIXITEM_CODE"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conMDB != null) {
			try {
				conMDB.close();
				System.out.println(conMDB);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean executeSql(String sqlString) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean GetDbConnectState() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ConnectorTable> getAllConnector() {
		return connectMapper.selectAll();
	}

	@Override
	public PageInfo<ConnectorTable> findSqlPageFilter(int pageNum, int pageSize) {
		//将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum,pageSize);
        //startPage后紧跟的这个查询就是分页查询  
        List<ConnectorTable> connectorList = connectMapper.selectAll();
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以  
        PageInfo<ConnectorTable> pageInfo = new PageInfo<ConnectorTable>(connectorList,pageSize);  
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数  
        return pageInfo;
	}

	/**
	 * 
	 * @   addSelective
	 * @param connectorTableId
	 * @return 
	 * 
	 */
	@Override
	public int addSelective(ConnectorTable connectorTable) {
	//	connectMapper.add();
		connectMapper.insertSelective(connectorTable);
		return 0;
	}

	/**
	 * @param connectorTableId
	 * @return 
	 * 
	 */
	@Override
	public int editSelective(ConnectorTable connectorTable) {
		
		ConnectorTableExample connectorTableExample = new ConnectorTableExample();
		connectorTableExample.createCriteria().andConidEqualTo(connectorTable.getConid());
		connectMapper.updateByExampleSelective(connectorTable, connectorTableExample);
		return 0;
	}
/**
 * @param dbIds connectorTableIds
 * @return state true false
 * 
 */
	@Override
	public boolean deleteByConIds(List<String> dbIds) {
		try {
			ConnectorTableExample connectorTableExample = new ConnectorTableExample();
			connectorTableExample.createCriteria().andConidIn(dbIds);
			connectMapper.deleteByExample(connectorTableExample);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

}