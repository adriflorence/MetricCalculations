package com.metricsassignment.metrics;

import com.github.javaparser.ast.CompilationUnit;

public interface Metric {

    double calculate(CompilationUnit compilationUnit);

    String getName();
}
