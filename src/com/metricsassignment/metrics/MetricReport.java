package com.metricsassignment.metrics;

import java.util.List;

public class MetricReport {

    public void aggregateData(List<Metric> metrics){

        for (Metric m : metrics) {
            System.out.println(m.calculate());
        }

    }
}
