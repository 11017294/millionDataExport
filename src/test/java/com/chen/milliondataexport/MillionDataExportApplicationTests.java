package com.chen.milliondataexport;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class MillionDataExportApplicationTests {

    public static void main(String[] args) {
        List<String> tables = new ArrayList<>();
        tables.add("t_user");
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/data_export?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT&allowPublicKeyRetrieval=true",
                        "root",
                        "333")
                //全局配置
                .globalConfig(builder -> {
                    builder.author("MaybeBin")                          // 设置作者
                            .enableSwagger()                            // 开启 swagger 模式
                            .fileOverride()                             // 覆盖已生成文件
                            .disableOpenDir()                           // 禁止打开输出目录
                            .dateType(DateType.TIME_PACK)               // 时间策略
                            .commentDate("yyyy-MM-dd")                  // 注释日期
                            .outputDir(System.getProperty("user.dir")
                                    + "/src/main/java/");                // 指定输出目录
                })
                //包配置
                .packageConfig(builder -> {
                    builder.entity("entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .parent("com.chen.milliondataexport.export")                           // 设置父包名
                            .mapper("mapper")                                  // Mapper 包名
                            .xml("mapper")                                     // Mapper XML 包名
                            .pathInfo(Collections.singletonMap(
                                    OutputFile.mapperXml,
                                    System.getProperty("user.dir")
                                            + "/src/main/resources/mapper"));  // 设置mapperXml生成路径

                })
                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(tables)                            // 设置需要生成的表名
                            .addTablePrefix("t_")                         // 表前缀过滤
                            .entityBuilder()                              // 切换至Entity设置
                            .versionColumnName("version")                 // 乐观锁字段名(数据库)
                            .logicDeleteColumnName("isDeleted")           // 逻辑删除字段名(数据库)
                            .enableLombok()                               // lombok生效
                            .enableTableFieldAnnotation()                 // 所有实体类加注解
                            .serviceBuilder()                             // 切换至Service层设置
                            .formatServiceFileName("%sService")           // 设定后缀名
                            .formatServiceImplFileName("%sServiceImpl");  // 设定后缀名
                })
                //模板配置
                .templateEngine(new FreemarkerTemplateEngine())           // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    @Test
    void contextLoads() {
    }

}
