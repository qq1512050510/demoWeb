package com.example.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.winter.mapper.ConnectorTableMapper;
import com.winter.mapper.SqlTableMapper;
import com.winter.mapper.UserMapper;
import com.winter.model.ConnectorTable;
import com.winter.model.SqlTable;
import com.winter.model.User;
import com.winter.service.UserService;

/*@RunWith(SpringRunner.class*/
@RunWith(SpringJUnit4ClassRunner.class) 
@SpringBootTest
@MapperScan("com.winter.mapper")//将项目中对应的mapper类的路径加进来就可以了
public class DemoWebApplicationTests {

	@Autowired
    private SqlTableMapper sqlTableMapper;
	
	@Autowired
	private ConnectorTableMapper connectorMapper;
	
	
	
	@Test
	public void contextLoads() {
		System.out.println("test");
		List<SqlTable> listSql = sqlTableMapper.selectAll();
		System.out.println(listSql.size());
	}
	@Test
	public void connectorMapperTest() {
		System.out.println("connectorMapperTest");
		List<ConnectorTable> listConnctor = connectorMapper.selectAll();
		System.out.println(listConnctor.size());
	}

}
