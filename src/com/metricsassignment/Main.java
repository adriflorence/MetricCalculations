package com.metricsassignment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		File file = new File(args[0]);

		List<Metric> metrics = new ArrayList<Metric>();
		metrics.add(new WMCMetric());
		metrics.add(new RFCMetric());
		//metrics.add(new CBOMetric());
		//metrics.add(new LCOMMetric());

		MetricReport mr = new MetricReport(file, metrics);
		mr.print();
	}

}
