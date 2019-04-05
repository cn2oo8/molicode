package com.shareyi.molicode.common.beanhelper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bean描述信息
 *
 * @author david
 * @date 2017/9/30
 */
public class BeanDescribeInfo {

    /**
     * class信息
     */
    private Class beanClass;

    /**
     * 所有成员变量
     */
    private List<Field> beanFields;

    /**
     * 属性名和属性的映射map
     */
    private Map<String,Field> fieldMap;
    /**
     * 属性描述Map
     */
    private Map<String,PropertyDescriptor> propertyDescriptorMap;



    public BeanDescribeInfo(Class<?> beanClass) {
        this.beanClass=beanClass;
        this.beanFields =new ArrayList();
        this.propertyDescriptorMap=new HashMap();
        this.fieldMap=new HashMap();
    }


    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public List<Field> getBeanFields() {
        return beanFields;
    }

    public void setBeanFields(List<Field> beanFields) {
        this.beanFields = beanFields;
    }

    public Map<String, PropertyDescriptor> getPropertyDescriptorMap() {
        return propertyDescriptorMap;
    }

    public void setPropertyDescriptorMap(Map<String, PropertyDescriptor> propertyDescriptorMap) {
        this.propertyDescriptorMap = propertyDescriptorMap;
    }

    public boolean containsField(String fieldName) {
        return fieldMap.containsKey(fieldName);
    }

    public void addFieldAndDescriptor(Field field, PropertyDescriptor descriptor) {
        beanFields.add(field);
        fieldMap.put(field.getName(),field);
        propertyDescriptorMap.put(field.getName(),descriptor);
    }


    public PropertyDescriptor getPropertyDescriptor(String filedName) {
        return propertyDescriptorMap.get(filedName);
    }
}
