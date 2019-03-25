package com.winter.utils;

import java.sql.Connection;
/*//引入站点的配置信息
import config.Config;*/

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.pool.DruidDataSource;
import com.winter.config.bean.Config;

/**
 * 数据库连接生成类，返回一个数据库连接对象
 * 构造函数完成数据库驱动的加载和数据的连接
 * 提供数据库连接的取得和数据库的关闭方法
 * @author yuyu
 *
 */

public class DbConnect {    

private static DruidDataSource dataSourceMDB=null;
private static DruidDataSource dataSourceSSO=null;

public String db;


@Autowired
private Config config;  

    /**
     * 构造函数完成数据库的连接和连接对象的生成
     * @throws Exception 
     */
    public DbConnect(){

    }

    public void GetDbConnectSSO() throws Exception  {
        try{

            if(dataSourceSSO==null){

                dataSourceSSO=new DruidDataSource();

                //设置连接参数
                dataSourceSSO.setUrl(config.getUrl());
                dataSourceSSO.setDriverClassName(config.getDriverclassname());
                dataSourceSSO.setUsername(config.getUsername());
                dataSourceSSO.setPassword(config.getPassword());
                //配置初始化大小、最小、最大
                dataSourceSSO.setInitialSize(1);
                dataSourceSSO.setMinIdle(1);
                dataSourceSSO.setMaxActive(20);
                //连接泄漏监测
                dataSourceSSO.setRemoveAbandoned(true);
                dataSourceSSO.setRemoveAbandonedTimeout(30);
                //配置获取连接等待超时的时间
                dataSourceSSO.setMaxWait(20000);
                //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
                dataSourceSSO.setTimeBetweenEvictionRunsMillis(20000);
                //防止过期
                dataSourceSSO.setValidationQuery("SELECT 'x'");
                dataSourceSSO.setTestWhileIdle(true);
                dataSourceSSO.setTestOnBorrow(true);    

            }

        }catch(Exception e){

            throw e;

        }

    }
    public void GetDbConnectMDB() throws Exception {
        try{

            if(dataSourceMDB==null){

                dataSourceMDB=new DruidDataSource();                

                //设置连接参数
                dataSourceMDB.setUrl(config.getUrl());
                dataSourceMDB.setDriverClassName(config.getDriverclassname());
                dataSourceMDB.setUsername(config.getUsername());
                dataSourceMDB.setPassword(config.getPassword());
                //配置初始化大小、最小、最大
                dataSourceMDB.setInitialSize(1);
                dataSourceMDB.setMinIdle(1);
                dataSourceMDB.setMaxActive(20);
                //连接泄漏监测
                dataSourceMDB.setRemoveAbandoned(true);
                dataSourceMDB.setRemoveAbandonedTimeout(30);
                //配置获取连接等待超时的时间
                dataSourceMDB.setMaxWait(20000);
                //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
                dataSourceMDB.setTimeBetweenEvictionRunsMillis(20000);
                //防止过期
                dataSourceMDB.setValidationQuery("SELECT 'x'");
                dataSourceMDB.setTestWhileIdle(true);
                dataSourceMDB.setTestOnBorrow(true);

            }

        }catch(Exception e){

            throw e;

        }

    }
    /**
     * 取得已经构造生成的数据库连接
     * @return 返回数据库连接对象
     * @throws Exception 
     */
    public Connection getConnect(String str) throws Exception{

        Connection con=null;
        this.db=str;

        try {

            if(db.equals("MDB")){
                GetDbConnectMDB();
                con=dataSourceMDB.getConnection();

            }else if(db.equals("SSO")){
                GetDbConnectSSO();
                con=dataSourceSSO.getConnection();  

            }
        } catch (Exception e) {

            throw e;

        }
        return con;     

    }

}