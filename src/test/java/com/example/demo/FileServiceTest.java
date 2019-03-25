    package com.example.demo;  
      
    import java.io.File;  
    import org.junit.Test;  
    import org.junit.runner.RunWith;  
    import org.springframework.test.context.ContextConfiguration;  
    import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;  
    import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;  
      
      
    @RunWith(SpringJUnit4ClassRunner.class)  
    @ContextConfiguration(locations = "classpath:application.yml")  
    public class FileServiceTest extends AbstractJUnit4SpringContextTests  
    {  
      // @Resource  
       // FileServiceInterface fileService;  
      
        @Test  
        public void Test()  
        {  
            
            System.out.println("Base Test");  
        }  
      
          
    }  