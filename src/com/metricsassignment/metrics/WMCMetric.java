package com.metricsassignment.metrics;

import com.github.javaparser.ast.CompilationUnit;

import visitors.MethodCountVisitor;

// Number of methods in a Class
public class WMCMetric implements Metric {

    @Override
    public double calculate(CompilationUnit compilationUnit) {
        MethodCountVisitor methodCountVisitor = new MethodCountVisitor();
        return methodCountVisitor.visit(compilationUnit, null);
    }
}
