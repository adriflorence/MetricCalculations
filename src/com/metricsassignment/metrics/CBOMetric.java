package com.metricsassignment.metrics;

import com.github.javaparser.ast.CompilationUnit;
import visitors.FieldTypeVisitor;
import visitors.MethodParameterVisitor;

import java.lang.reflect.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// CBO - Coupling between Objects
public class CBOMetric implements Metric {

    private static List<File> files = new ArrayList<>();

    @Override
    public double calculate(CompilationUnit compilationUnit) {
        double CBOValue = 0;
        FieldTypeVisitor FieldTypeVisitor = new FieldTypeVisitor();
        MethodParameterVisitor methodParameterVisitor = new MethodParameterVisitor();
        Class MethodParamType = methodParameterVisitor.visit(compilationUnit, null);
        Class FieldTypeClass = FieldTypeVisitor.visit(compilationUnit, null);
        System.out.println("fields " + FieldTypeClass);
        if (FieldTypeClass != null) {
            if (FieldTypeClass.toString().contains("ClassOrInterface"))
                CBOValue = CBOValue + 1;
        }
        if (MethodParamType != null) {
            if (MethodParamType.toString().contains("ClassOrInterface")) {
                CBOValue = CBOValue + 1;
            }
        }
        return CBOValue;
    }
}
