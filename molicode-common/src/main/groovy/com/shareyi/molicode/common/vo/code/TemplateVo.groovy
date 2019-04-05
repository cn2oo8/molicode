package com.shareyi.molicode.common.vo.code

import com.shareyi.molicode.sdk.dto.ExtAttrDto
import org.apache.commons.collections4.CollectionUtils

/**
 * 模板对象
 */
class TemplateVo extends ExtAttrDto{
    /**
     * id
     */
    String id;
    /**
     * 名称
     */
    String name;
    /**
     * 描述信息
     */
    String desc;
    /**
     * 输出路径
     */
    String destFile;
    /**
     * 模板源路径
     */
    String templateFile;
    /**
     * 模板引擎
     */
    String engine;
    /**
     * 模板类型
     */
    String templateType;

    /**
     * 支持的数据类型
     * 从XML获取，可以用逗号分隔
     */
    String acceptDataModel;

    /**
     * 支持的数据类型
     * 从XML获取，可以用逗号分隔, 转换为list
     */
    List<String> acceptDataModelList;

    /***  转换内容  start ***/
    /**
     * 模板内容
     */
    String templateContent;

    /**
     * 渲染后的目标路径
     */
    transient String renderedDestFilePath;

    /**
     * 渲染后的内容
     */
    transient String renderedContent;
    /***  转换内容  end ***/

    /**
     * 是否能够处理该数据模型
     * @param dataModel
     * @return
     */
    boolean canHandleDataModel(String dataModel){
        if(CollectionUtils.isEmpty(acceptDataModelList)){
            return true;
        }
        return acceptDataModelList.contains(dataModel);
    }


    @Override
    String toString() {
        return "TemplateVo{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                ", destFile='" + destFile + '\'' +
                ", templateFile='" + templateFile + '\'' +
                ", engine='" + engine + '\'' +
                ", templateContent='" + templateContent + '\'' +
                ", renderedDestFilePath='" + renderedDestFilePath + '\'' +
                ", renderedContent='" + renderedContent + '\'' +
                '}';
    }
}
