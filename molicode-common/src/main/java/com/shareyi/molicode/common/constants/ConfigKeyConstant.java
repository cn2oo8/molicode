package com.shareyi.molicode.common.constants;

/**
 * 配置相关常量
 *
 * @author david
 * @date 2018/8/26
 */
public class ConfigKeyConstant {


    /**
     * 数据库相关配置项
     */
    public class DatabaseConfig {

        public static final String CONFIG_KEY ="databaseConfig";

        /**
         * 数据库类型名称
         */
        public static final String DATABASE_NAME = "databaseName";

        /**
         * 驱动key
         */
        public static final String DRIVER_CLASS ="driverClass";
        /**
         * 数据库连接
         */
        public static final String URL ="url";
        /**
         * 数据库用户名
         */
        public static final String USERNAME ="username";
        /**
         * 数据库密码
         */
        public static final String PASSWORD ="password";

    }

    /**
     * PATH相关配置项
     */
    public class PathConfig {

        public static final String CONFIG_KEY ="pathConfig";
        /**
         * autoXml文件路径
         */
        public static final String AUTO_XML_PATH ="autoXmlPath";
        /**
         * 模板根目录
         */
        public static final String TEMPLATE_BASE_PATH ="templateBaseDir";
        /**
         * 表模型输出目录
         */
        public static final String TABLE_MODEL_DIR ="tableModelDir";
        /**
         * 代码输出目录
         */
        public static final String PROJECT_OUTPUT_DIR ="projectOutputDir";

        /**
         * 模板类型
         * {@link com.shareyi.molicode.common.enums.TemplateTypeEnum}
         */
        public static final String TEMPLATE_TYPE ="templateType";

    }


    public class MavenConfig {

        /**
         * groupId
         */
        public static final String GROUP_ID ="groupId";

        /**
         * artifactId
         */
        public static final String ARTIFACT_ID ="artifactId";

        /**
         * version
         */
        public static final String VERSION ="version";
    }



    public class GlobalMavenConfig {

        public static final String CONFIG_KEY ="mavenConfig";

        /**
         * localRepository
         */
        public static final String LOCAL_REPOSITORY ="localRepository";

    }

    /**
     * code相关配置项
     */
    public class CodeConfig {

        public static final String CONFIG_KEY ="codeConfig";
        /**
         * 作者
         */
        public static final String AUTHOR ="author";
        /**
         * 基本目录
         */
        public static final String BASE_PACKAGE ="basePackage";
        /**
         * maven工程前置
         */
        public static final String ARTIFACT_ID ="artifactId";

        /**
         * 分包名
         */
        public static final String CATEGORY ="category";

        /**
         * Date
         */
        public static final String DATE ="date";
        /**
         * year
         */
        public static final String YEAR ="year";

    }

    /**
     * 额外的配置信息
     */
    public class ExtConfig {

        public static final String CONFIG_KEY ="extConfig";
        /**
         * json扩展配置信息
         */
        public static final String JSON_KEY ="jsonConfig";

    }
}
