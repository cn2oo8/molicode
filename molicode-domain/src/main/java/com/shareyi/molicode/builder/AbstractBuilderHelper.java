package com.shareyi.molicode.builder;

import com.shareyi.molicode.common.enums.StatusEnum;
import com.shareyi.molicode.domain.BasicDomain;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * 抽象的基础builder
 *
 * @author david
 * @date 2017/9/27
 */
public abstract class AbstractBuilderHelper {

    /**
     *  验证字段是否合法
     * @param fields
     * @param fieldSet
     * @return
     */
    public static Set<String> checkFiledStatic(Set<String> fields, Set<String> fieldSet){
        if(CollectionUtils.isEmpty(fields)){
            return fields;
        }
        Iterator<String> iterator=fields.iterator();
        while (iterator.hasNext()){
            String filed=iterator.next();
            if(filed.indexOf(',') > 0 || filed.indexOf(';') >0 || filed.indexOf("'") > 0){
                iterator.remove();
                continue;
            }
            String val = filed;
            String[] aa = filed.split("\\.");
            if(aa.length > 1){
                val = aa[1];
                String[] bb = aa[1].split(" ");
                if(bb.length > 1){
                    val = bb[0];
                }
            }
            if(!fieldSet.contains(val)){
                iterator.remove();
            }
        }
        return fields;
    }



    /**
     * 填充新增的数据
     * @param bean
     * @return
     */
    public static BasicDomain supplyAddInfo(BasicDomain bean) {
        Date now = new Date();
        if(bean.getCreated()==null){
            bean.setCreated(now);
        }
        bean.setModified(now);
        if(bean.getStatus()==null){
            bean.setStatus(StatusEnum.YES.getCode());
        }
        if(bean.getDataVersion()==null){
            bean.setDataVersion(1);
        }
        if(bean.getConcurrentVersion()==null){
            bean.setConcurrentVersion(1);
        }
        //迁移数据请重载本方法
        //ID为自增字段，不能外部传入
        bean.setId(null);
        return bean;
    }

    /**
     * 填充修改的数据
     * @param bean
     * @return
     */
    public static BasicDomain supplyUpdateInfo(BasicDomain bean) {
        bean.setModified(new Date());
        return bean;
    }
}
