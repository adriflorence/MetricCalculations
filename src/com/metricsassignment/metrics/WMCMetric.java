package com.metricsassignment.metrics;

import com.github.javaparser.ast.CompilationUnit;
import visitors.MethodCountVisitor;

// Weighted methods for Class - Number of methods in a class
public class WMCMetric implements Metric {

    @Override
    public double calculate(CompilationUnit compilationUnit) {
        MethodCountVisitor methodCountVisitor = new MethodCountVisitor();
        Integer count = methodCountVisitor.visit(compilationUnit, null);
        return count;
    }
}
