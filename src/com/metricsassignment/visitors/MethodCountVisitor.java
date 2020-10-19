package com.metricsassignment.visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

public class MethodCountVisitor extends GenericVisitorAdapter<Integer, Void> {

    @Override
    public Integer visit(ClassOrInterfaceDeclaration n, Void arg) {
        return n.getMethods().size();
    }
}
