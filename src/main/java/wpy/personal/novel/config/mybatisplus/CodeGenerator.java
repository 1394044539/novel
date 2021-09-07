package wpy.personal.novel.config.mybatisplus;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class CodeGenerator {

    public static void main(String[] args) {
        generate();
    }

    private static void generate() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //1、数据源配置
        mpg.setDataSource(getDataSourceConfig());

        //2、数据库表配置
        mpg.setStrategy(getStrategyConfig(null));

        //3.包配置
        mpg.setPackageInfo(getPackageConfig());

        //4、全局配置
        mpg.setGlobalConfig(getGlobalConfig());

        //5、模板引擎
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        //执行
        mpg.execute();
    }

    /**
     * 全局配置
     * @return
     */
    private static GlobalConfig getGlobalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        //输出路径

        globalConfig.setOutputDir("E://Program//JAVA//IDEAProgram//novel//src//main//java");
        //作者
        globalConfig.setAuthor("wangpanyin");
        //生成后是否打开
        globalConfig.setOpen(false);
        //是否覆盖已有文件
        globalConfig.setFileOverride(true);
        //实体命名方式
        globalConfig.setEntityName("%s");
        //mapper命名方式
        globalConfig.setMapperName("%sMapper");
        //xml
        globalConfig.setXmlName("%sMapper");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setControllerName("%sController");
        //时间类型对应策略，这种是java的Date类
        globalConfig.setDateType(DateType.ONLY_DATE);
        //baseResultMap
        globalConfig.setBaseResultMap(true);
        //baseColumnList
        globalConfig.setBaseColumnList(true);
        //是否在xml中开启二级缓存
//        globalConfig.setEnableCache();
        // Swagger2 注解
//        globalConfig.setSwagger2(true);
        //kotlin注解
//        globalConfig.setKotlin();
        //activeRecord模式
//        globalConfig.setActiveRecord();
        //生成主键的类型
//        globalConfig.setIdType();

        return globalConfig;
    }

    /**
     * 包配置
     * @return
     */
    private static PackageConfig getPackageConfig() {
        PackageConfig packageConfig = new PackageConfig();
        //父包名
        packageConfig.setParent("wpy.personal.novel.config.mybatisplus");
        //controller包名
        packageConfig.setController("controller");
        //service包名
        packageConfig.setService("service");
        //impl包名
        packageConfig.setServiceImpl("service.impl");
        //mapper包名
        packageConfig.setMapper("mapper");
        //entity包名
        packageConfig.setEntity("entity");
        //xml包名
        packageConfig.setXml("mapper.mapper");
        //父包模块名
//        packageConfig.setModuleName();
        //路径信息配置
//        packageConfig.setPathInfo();
        return packageConfig;
    }

    /**
     * 数据库表配置
     * @return
     */
    private static StrategyConfig getStrategyConfig(String[] tables) {
        StrategyConfig strategyConfig = new StrategyConfig();
        //数据库表映射到实体的命名策略，下划线转驼峰
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        //数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        //是否为lombok模式
        strategyConfig.setEntityLombokModel(true);
        //生成 @RestController 控制器
        strategyConfig.setRestControllerStyle(true);
        //是否生成实体时，生成字段注解
        strategyConfig.setEntityTableFieldAnnotationEnable(true);
        //是否序列化基础类
        strategyConfig.setEntitySerialVersionUID(true);
        //需要包含的表名，当enableSqlFilter为false时，允许正则表达式（与exclude二选一配置）
        if(tables!=null && tables.length>0){
            strategyConfig.setInclude(tables);
        }
        //生成驼峰转连字符
//        strategyConfig.setControllerMappingHyphenStyle(false);
        //是否大写命名
//        strategyConfig.setChainModel(true);
        //是否跳过视图
//        strategyConfig.setSkipView(true);
        //表前缀
//        strategyConfig.setTablePrefix();
        //字段前缀
//        strategyConfig.setFieldPrefix();
        //自定义继承的Entity类全称，带包名
//        strategyConfig.setSuperEntityClass();
        //自定义基础的Entity类，公共字段
//        strategyConfig.setSuperEntityColumns();
        //继承的controller类
//        strategyConfig.setSuperControllerClass();
        //自定义继承的Mapper类全称，带包名
//        strategyConfig.setSuperMapperClass();
        //自定义继承的Service类全称，带包名
//        strategyConfig.setSuperServiceClass();
        //自定义继承的ServiceImpl类全称，带包名
//        strategyConfig.setSuperServiceImplClass();
        //默认激活进行sql模糊表名匹配，关闭之后likeTable与notLikeTable将失效，include和exclude将使用内存过滤
//        strategyConfig.setEnableSqlFilter()
        //去掉的表名
//        strategyConfig.setExclude();
        //模糊匹配表名（与notLikeTable二选一配置）
//        strategyConfig.setLikeTable();
        //模糊排除表名
//        strategyConfig.setNotLikeTable();
        //生成字段常量
//        strategyConfig.setEntityColumnConstant();
        //是否为链式模式
//        strategyConfig.setChainModel();
        //Boolean类型字段是否移除is前缀（默认 false）
//        strategyConfig.setEntityBooleanColumnRemoveIsPrefix();
        //乐观锁名称
//        strategyConfig.setVersionFieldName();
        //逻辑删除字段名称
//        strategyConfig.setLogicDeleteFieldName();
        //表填充字段
//        strategyConfig.setTableFillList();
        return strategyConfig;
    }

    /**
     * 数据源
     * @return
     */
    private static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc=new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/novel_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&allowMultiQueries=true");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        //数据库类型
//        dsc.setDbType(DbType.MYSQL);
        //数据库SchemaName
//        dsc.setSchemaName("public");
        //类型转换
//        dsc.setKeyWordsHandler();
        //数据库信息查询类
//        dsc.setDbQuery();
        return dsc;
    }
}
