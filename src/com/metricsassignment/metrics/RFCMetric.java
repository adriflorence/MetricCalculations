package com.metricsassignment.metrics;

import com.github.javaparser.ast.CompilationUnit;
import com.metricsassignment.visitors.MethodAndRemoteMethodCountVisitor;

/**
 * the number of methods in a class plus the number of remote methods called by methods in the class
 */
public class RFCMetric implements Metric {

    @Override
    public double calculate(CompilationUnit compilationUnit) {
        MethodAndRemoteMethodCountVisitor marm = new MethodAndRemoteMethodCountVisitor();
        return marm.visit(compilationUnit, null);
    }
}
