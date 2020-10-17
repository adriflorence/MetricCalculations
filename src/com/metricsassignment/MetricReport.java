package com.metricsassignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.metricsassignment.metrics.Metric;

public class MetricReport {

	private List<File> files;
	private List<Metric> metrics;
	private Map<CompilationUnit, List<Double>> report;

	public MetricReport(File file, List<Metric> metrics) {
		report = new HashMap<CompilationUnit, List<Double>>();
		files = new ArrayList<File>();
		doListing(file);
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
				System.out.printf("\t\t%8.2f", d.doubleValue());
			}
			System.out.print("\n");
		}
	}

	private void calculate() {
		for (File f : files) {
			if (f.getName().endsWith(".java")) {
				FileInputStream stream;
				try {
					stream = new FileInputStream(f);
				} catch (FileNotFoundException e) {
					System.out.println(e.getMessage());
					continue;
				}
				CompilationUnit compilationUnit = StaticJavaParser.parse(stream);
				List<Double> l = new ArrayList<Double>();
				for (Metric m : metrics) {
					l.add(m.calculate(compilationUnit));
				}
				report.put(compilationUnit, l);
			}
		}
	}

	private void doListing(File dirName) {

		File[] fileList = dirName.listFiles();

		for (File file : fileList) {
			if (file.isFile() && file.getName().endsWith(".java")) {
				files.add(file);
			} else if (file.isDirectory()) {
				doListing(file);
			}
		}
	}
}
