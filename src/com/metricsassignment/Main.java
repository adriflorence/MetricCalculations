package com.metricsassignment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.metricsassignment.IO.JavaFileFilter;
import com.metricsassignment.metrics.*;

public class Main {

	public static void main(String[] args) {

		File file = new File(args[0]);

		// only .java files to be considered for metrics
		JavaFileFilter javaFileFilter = new JavaFileFilter();
		List<File> javaFiles = javaFileFilter.filterJavaFiles(file);

		List<Metric> metrics = new ArrayList<>();
		metrics.add(new WMCMetric());
		metrics.add(new WMC2Metric());
		metrics.add(new RFCMetric());
		metrics.add(new CBOMetric(javaFiles));
		metrics.add(new LCOMMetric());

		MetricReport mr = new MetricReport(javaFiles, metrics);
		mr.print();
	}

}
