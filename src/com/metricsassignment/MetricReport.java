package com.metricsassignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.metricsassignment.metrics.Metric;

public class MetricReport {

	private List<File> javaFiles;
	private List<Metric> metrics;
	private Map<CompilationUnit, List<Double>> report;

	public MetricReport(List<File> javaFiles, List<Metric> metrics) {
		report = new HashMap<>();
		this.javaFiles = javaFiles;
		this.metrics = metrics;
	}

	/**
	 * This method assumes that each class is in its own file.
	 */
	public void print() {
		calculate();

		System.out.printf("%-24s", "Class name");
		for (Metric m : metrics)
			System.out.printf("%24s", m.getClass().getSimpleName());
		System.out.print("\n");

		for (Map.Entry<CompilationUnit, List<Double>> entry : report.entrySet()) {
			String className = entry.getKey().getType(0).getNameAsString();
			System.out.printf("%-24s", className);
			for (Double d : entry.getValue()) {
				System.out.printf("\t\t%8d", d.intValue());
			}
			System.out.print("\n");
		}
	}

	private void calculate() {
		for (File f : javaFiles) {
			// try with resources
			try (FileInputStream stream = new FileInputStream(f)) {
				CompilationUnit compilationUnit = StaticJavaParser.parse(stream);
				List<Double> metricResults = new ArrayList<>();
				for (Metric m : metrics) {
					metricResults.add(m.calculate(compilationUnit));
				}
				report.put(compilationUnit, metricResults);

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
