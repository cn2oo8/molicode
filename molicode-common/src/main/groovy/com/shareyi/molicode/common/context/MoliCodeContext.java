package com.shareyi.molicode.common.context;

import com.shareyi.molicode.common.enums.DataModelTypeEnum;
import com.shareyi.molicode.common.vo.code.AutoCodeParams;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

/**
 * 代码生成context
 *
 * @author zhangshibin
 * @date 2018/10/3
 */
public class MoliCodeContext {

    /**
     * 数据存储map
     */
    private Map<String, Object> dataMap = new HashMap<>();

    /**
     * 前台入参
     */
    private AutoCodeParams autoCodeParams;
    /**
     * 是否执行成功
     */
    private boolean success;
    /**
     * 信息
     */
    private StringBuilder message = new StringBuilder();
    /**
     * template 的上下文
     */
    private Map<String, Object> templateBinding;
    /**
     * 获取数据模型类型
     */
    private DataModelTypeEnum dataModelTypeEnum;

    /**
     * 模板maven 仓库jar file
     */
    private JarFile mavenJarFile;
    /**
     * 创建context
     *
     * @param params
     * @return
     */
    public static MoliCodeContext create(AutoCodeParams params) {
        MoliCodeContext context = new MoliCodeContext();
        context.setAutoCodeParams(params);
        return context;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public AutoCodeParams getAutoCodeParams() {
        return autoCodeParams;
    }

    public void setAutoCodeParams(AutoCodeParams autoCodeParams) {
        this.autoCodeParams = autoCodeParams;
    }

    public StringBuilder getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Object> getTemplateBinding() {
        return templateBinding;
    }

    public void setTemplateBinding(Map<String, Object> templateBinding) {
        this.templateBinding = templateBinding;
    }

    public MoliCodeContext appendMessage(String message) {
        this.message.append(message).append("\n");
        return this;
    }

    /**
     * 放置数据
     *
     * @param key
     * @param value
     * @return
     */
    public MoliCodeContext put(String key, Object value) {
        dataMap.put(key, value);
        return this;
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return dataMap.get(key);
    }

    /**
     * 获取String 类型的数据
     *
     * @param key
     * @return
     */
    public String getDataString(String key) {
        Object object = this.get(key);
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        }
        return object.toString();
    }

    public JarFile getMavenJarFile() {
        return mavenJarFile;
    }

    public void setMavenJarFile(JarFile mavenJarFile) {
        this.mavenJarFile = mavenJarFile;
    }

    public DataModelTypeEnum getDataModelTypeEnum() {
        if(dataModelTypeEnum == null ){
           dataModelTypeEnum = DataModelTypeEnum.Parser.parseToNullSafe(DataModelTypeEnum.class, autoCodeParams.getDataModelType(), DataModelTypeEnum.TABLE_MODEL);
        }
        return dataModelTypeEnum;
    }
}
