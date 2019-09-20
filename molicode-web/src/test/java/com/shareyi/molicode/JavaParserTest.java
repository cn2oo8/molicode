package com.shareyi.molicode;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.DataKey;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.stmt.BlockStmt;
import org.junit.Test;

import static com.github.javaparser.ast.Modifier.PRIVATE;
import static com.github.javaparser.ast.Modifier.PUBLIC;
import static com.github.javaparser.ast.Modifier.STATIC;

/**
 * 描述
 *
 * @author david
 * @date 2018/10/18
 */
public class JavaParserTest {

    @Test
    public void test(){
        CompilationUnit compilationUnit = new CompilationUnit();
        ClassOrInterfaceDeclaration myClass = compilationUnit
                .addClass("MyClass")
                .setPublic(true);
        myClass.addField(int.class, "A_CONSTANT", PUBLIC, STATIC);
        myClass.addField(String.class, "name", PRIVATE);
        Comment comment= new JavadocComment();
        comment.setContent("你大爷！");
        myClass.addMethod("helloWorld", PUBLIC).setParameters(NodeList.nodeList()).setComment(comment);
        String code = myClass.toString();
        System.out.println(code);
    }
}
