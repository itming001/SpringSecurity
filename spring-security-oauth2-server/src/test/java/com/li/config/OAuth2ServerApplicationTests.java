package com.li.config;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OAuth2ServerApplicationTests {
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void test(){
        System.out.println(passwordEncoder.encode("secret"));
    }

    @Test
    public void autoGenerator(){
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("D:\\idea-workspace\\SpringSecurity\\spring-security-oauth2-server\\src\\main\\java");
        gc.setAuthor("liyanming");
        gc.setOpen(false);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        //自定义文件命名
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/oauth2?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT&useSSL=false");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);
        //自定义数据库转化类型
        MySqlTypeConvert mm = new MySqlTypeConvert(){
            public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                if ( fieldType.toLowerCase().contains( "datetime" ) ) {
                    return DbColumnType.LOCAL_DATE_TIME;
                }
                return  (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
            }
        };
        dsc .setTypeConvert(mm);


        mpg.setPackageInfo(new PackageConfig()
                .setParent("com.li")
                .setMapper("mapper")
                .setEntity("entity")
                .setService("service")
                .setServiceImpl("serviceImpl")
                .setController("controller")
        );
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)//驼峰命名
                .setNaming(NamingStrategy.underline_to_camel)//数据库表映射到实体的命名策略,该处下划线转驼峰命名
                .setEntityLombokModel(false);//实体类中不使用_
        mpg.setStrategy(strategyConfig);
        mpg.execute();
    }
}
