package com.metricsassignment.metrics;

import com.github.javaparser.ast.CompilationUnit;
import com.metricsassignment.visitors.CyclomaticComplexityVisitor;

// complexity of each class is calculated according to McCabe's Cyclomatic Complexity
public class WMC2Metric implements Metric {

    @Override
    public double calculate(CompilationUnit compilationUnit) {
        CyclomaticComplexityVisitor cyclomaticComplexityVisitor = new CyclomaticComplexityVisitor();
        return cyclomaticComplexityVisitor.visit(compilationUnit, null);
    }
}
