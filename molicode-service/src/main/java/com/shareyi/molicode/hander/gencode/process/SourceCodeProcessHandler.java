package com.shareyi.molicode.hander.gencode.process;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.shareyi.molicode.common.chain.handler.SimpleHandler;
import com.shareyi.molicode.common.chain.handler.awares.DataProcessHandlerAware;
import com.shareyi.molicode.common.constants.MoliCodeConstant;
import com.shareyi.molicode.common.context.MoliCodeContext;
import com.shareyi.molicode.common.enums.DataModelTypeEnum;
import com.shareyi.molicode.common.vo.code.AutoCodeParams;
import com.shareyi.molicode.sdk.dto.FieldInfoDto;
import com.shareyi.molicode.sdk.dto.JavaSourceCodeDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 单行文本 数据处理handler
 *
 * @author zhangshibin
 * @date 2018/10/3
 */
@Service
public class SourceCodeProcessHandler extends SimpleHandler<MoliCodeContext> implements DataProcessHandlerAware {
    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public boolean shouldHandle(MoliCodeContext context) {
        DataModelTypeEnum dataModelTypeEnum = context.getDataModelTypeEnum();
        if (dataModelTypeEnum == DataModelTypeEnum.JAVA_SOURCE) {
            return true;
        }
        return false;
    }

    @Override
    public void doHandle(MoliCodeContext context) {
        String content = context.getDataString(MoliCodeConstant.CTX_KEY_SRC_CONTENT);
        //AutoCodeParams autoCodeParams = context.getAutoCodeParams();
        if (StringUtils.isEmpty(content)) {
            return;
        }

        JavaSourceCodeDto sourceCodeDto = new JavaSourceCodeDto();
        CompilationUnit compilationUnit = JavaParser.parse(content);
        compilationUnit.accept(new JavaSourceCodeVisitor(sourceCodeDto), null);
        context.put(MoliCodeConstant.CTX_KEY_DEF_DATA, sourceCodeDto);
    }


    class JavaSourceCodeVisitor extends VoidVisitorAdapter<Void> {

        JavaSourceCodeDto sourceCodeDto;

        public JavaSourceCodeVisitor(JavaSourceCodeDto sourceCodeDto) {
            this.sourceCodeDto = sourceCodeDto;
        }

        @Override
        public void visit(FieldDeclaration n, Void arg) {
            if (n.isFinal() || n.isStatic()) {
                return;
            }
            NodeList<VariableDeclarator> nodeList = n.getVariables();
            for (VariableDeclarator declarator : nodeList) {
                FieldInfoDto fieldInfoDto = new FieldInfoDto();
                fieldInfoDto.setDataName(declarator.getNameAsString());
                fieldInfoDto.setFieldClass(declarator.getType().asString());

                Optional<Comment> commentOptional = n.getComment();
                if (commentOptional.isPresent()) {
                    String commentContent = commentOptional.get().getContent();
                    commentContent = commentContent.replaceAll("\\*", "");
                    fieldInfoDto.setComment(commentContent.trim());
                } else {
                    fieldInfoDto.setComment(fieldInfoDto.getDataName());
                }
                sourceCodeDto.addField(fieldInfoDto);
            }
            sourceCodeDto.addFieldDeclaration(n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            if (!n.isPublic()) {
                return;
            }

            sourceCodeDto.setClassName(n.getNameAsString());
            Optional<Comment> commentOptional = n.getComment();
            if (commentOptional.isPresent()) {
                sourceCodeDto.setComment(commentOptional.get().getContent());
            } else {
                sourceCodeDto.setComment(sourceCodeDto.getClassName());
            }
            super.visit(n, arg);
        }

        @Override
        public void visit(EnumConstantDeclaration n, Void arg) {
            sourceCodeDto.addEnumConstDeclaration(n);
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            sourceCodeDto.addMethodDeclaration(n);
            super.visit(n, arg);
        }

        @Override
        public void visit(ImportDeclaration n, Void arg) {
            sourceCodeDto.addImportDeclaration(n);
            super.visit(n, arg);
        }

        @Override
        public void visit(EnumDeclaration n, Void arg) {
            sourceCodeDto.setClassName(n.getNameAsString());
            Optional<Comment> commentOptional = n.getComment();
            if (commentOptional.isPresent()) {
                sourceCodeDto.setComment(commentOptional.get().getContent());
            } else {
                sourceCodeDto.setComment(sourceCodeDto.getClassName());
            }
            super.visit(n, arg);
        }
    }
}
