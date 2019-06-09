package com.xd.pre.config;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Objects;


/**
 * @Author 李号东
 * @Description mybatis-plus自动生成
 * @Date 08:07 2019-03-17
 * @Param
 * @return
 **/
public class MyBatisPlusGenerator {

    public static void main(String[] args) {
        //指定包名
        String packageName = "com.xd.pre";
        //指定生成的表名
        String[] tableNames = new String[]{"sys_dict"};
        new MyBatisPlusGenerator().generateByTables(packageName, tableNames);

    }


    /**
     * 根据表自动生成
     *
     * @param packageName 包名
     * @param tableNames  表名
     */
    private void generateByTables(String packageName, String... tableNames) {
        // 配置数据源
        DataSourceConfig dataSourceConfig = getDataSourceConfig();
        // 策略配置
        StrategyConfig strategyConfig = getStrategyConfig(tableNames);
        // 全局变量配置
        GlobalConfig globalConfig = getGlobalConfig();
        // 包名配置
        PackageConfig packageConfig = getPackageConfig(packageName);
        // 自动生成
        atuoGenerator(dataSourceConfig, strategyConfig, globalConfig, packageConfig);
    }

    /**
     * 集成
     *
     * @param dataSourceConfig 配置数据源
     * @param strategyConfig   策略配置
     * @param config           全局变量配置
     * @param packageConfig    包名配置
     */
    private void atuoGenerator(DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, GlobalConfig config, PackageConfig packageConfig) {
        new AutoGenerator()
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .execute();
    }

    /**
     * 设置包名
     *
     * @param packageName 父路径包名
     * @return PackageConfig 包名配置
     */
    private PackageConfig getPackageConfig(String packageName) {
        return new PackageConfig()
                .setParent(packageName)
                .setController("controller")
                .setEntity("domain")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.impl");
    }

    /**
     * 全局配置
     *
     * @return GlobalConfig
     */
    private GlobalConfig getGlobalConfig() {
        return new GlobalConfig()
                .setOutputDir(getOutputDir("pre"))
                .setOpen(false)
                .setFileOverride(true)
                //生成基本的resultMap
                .setBaseResultMap(true)
                //生成基本的SQL片段
                .setBaseColumnList(false)
                // 作者
                .setAuthor("lihaodong");
    }

    /**
     * 返回项目路径
     *
     * @param projectName 项目名
     * @return 项目路径
     */
    private String getOutputDir(String projectName) {
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("")).getPath();
        int index = path.indexOf(projectName);
        return "/"+path.substring(1, index) + projectName + "/src/main/java/";
    }


    /**
     * 策略配置
     *
     * @param tableNames 表名
     * @return StrategyConfig
     */
    private StrategyConfig getStrategyConfig(String... tableNames) {

        return new StrategyConfig()
                .setEntityLombokModel(true)
//                .setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model")
                .setNaming(NamingStrategy.underline_to_camel)
                //需要生成的的表名，多个表名传数组
                .setInclude(tableNames)
                .setRestControllerStyle(true)
                .setControllerMappingHyphenStyle(true);
    }

    /**
     * 配置数据源
     *
     * @return 数据源配置 DataSourceConfig
     */
    private DataSourceConfig getDataSourceConfig() {
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/pre";
        return new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("root")
                .setDriverName("com.mysql.jdbc.Driver");
    }

}
