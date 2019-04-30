package com.shareyi.molicode.sdk.dto;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.printer.PrettyPrinterConfiguration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * java源码Dto
 *
 * @author zhangshibin
 * @date 2018/10/28
 */
public class JavaSourceCodeDto extends ExtAttrDto implements Serializable {

    /**
     * 类名
     */
    private String className;

    /**
     * 注释信息
     */
    private String comment;
    /**
     * 字段列表
     */
    private List<FieldInfoDto> fieldList = new ArrayList<FieldInfoDto>();

    /**
     * 枚举常量列表
     */
    private List<EnumConstantDeclaration> enumConstantDeclarationList;

    /**
     * 方法列表
     */
    private List<MethodDeclaration> methodDeclarationList;
    /**
     * 字段列表
     */
    private List<FieldDeclaration> fieldDeclarationList;
    /**
     * import声明
     */
    private List<ImportDeclaration> importDeclarationList;
    /**
     * 注解声明
     */
    private List<AnnotationDeclaration> annotationDeclarationList;
    /**
     * 包名
     */
    private String packageName;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<FieldInfoDto> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<FieldInfoDto> fieldList) {
        this.fieldList = fieldList;
    }

    public void addField(FieldInfoDto fieldInfoDto) {
        fieldList.add(fieldInfoDto);
    }

    public void addEnumConstDeclaration(EnumConstantDeclaration declaration) {
        enumConstantDeclarationList = safeAddList(enumConstantDeclarationList, declaration);
    }

    public void addMethodDeclaration(MethodDeclaration declaration) {
        methodDeclarationList = safeAddList(methodDeclarationList, declaration);
    }

    public void addFieldDeclaration(FieldDeclaration declaration) {
        fieldDeclarationList = safeAddList(fieldDeclarationList, declaration);
    }

    public void addImportDeclaration(ImportDeclaration declaration) {
        importDeclarationList = safeAddList(importDeclarationList, declaration);
    }

    public void addAnnotationDeclaration(AnnotationDeclaration declaration) {
        annotationDeclarationList = safeAddList(annotationDeclarationList, declaration);
    }


    private static <T> List<T> safeAddList(List<T> list, T element) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        list.add(element);
        return list;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
