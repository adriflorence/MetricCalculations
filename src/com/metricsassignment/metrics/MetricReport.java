package com.metricsassignment.metrics;

import com.github.javaparser.ast.CompilationUnit;
import java.util.List;

public class MetricReport {

    private CompilationUnit compilationUnit;

    public MetricReport(CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
    }

    public void aggregateData(List<Metric> metrics){

        for (Metric m : metrics) {
            System.out.println(m.calculate(compilationUnit));
        }

    }
}
