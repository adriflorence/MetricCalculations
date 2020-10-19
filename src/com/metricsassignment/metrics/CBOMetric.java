package com.metricsassignment.metrics;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.metricsassignment.visitors.CouplingBetweenObjectsVisitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

// CBO - Coupling between Objects
public class CBOMetric implements Metric {

    private Map<String, Set<String>> cboMap;

    public CBOMetric(List<File> javaFiles) {
        this.cboMap = buildStructure(javaFiles);
    }

    @Override
    public double calculate(CompilationUnit compilationUnit) {
        String classname = compilationUnit.getType(0).getNameAsString();

        // all references to other classes within this class
        Integer referenceToOtherClass = cboMap.get(classname).size();

        // every class that references this class
        Integer referencedInOtherClasses = 0;
        for( Set<String> refs : cboMap.values()){
            if(refs.contains(classname)){
                referencedInOtherClasses++;
            }
        }

        return referenceToOtherClass + referencedInOtherClasses;
    }

    /**
     * key = name of class
     * value = set of class names that are referenced in that class
     */
    private Map<String, Set<String>> buildStructure(List<File> javaFiles){
        Map<String, Set<String>> cboMap = new HashMap<>();

        List<String> classNames = extractClassNames(javaFiles);

        for(File f : javaFiles) {
            try (FileInputStream stream = new FileInputStream(f)) {
                CompilationUnit compilationUnit = StaticJavaParser.parse(stream);
                CouplingBetweenObjectsVisitor cbo = new CouplingBetweenObjectsVisitor();
                Set<String> references = cbo.visit(compilationUnit, classNames);
                cboMap.put(compilationUnit.getType(0).getNameAsString(), references);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return cboMap;
    }

    /**
     * returns a list of the top level class names from the input archive
     */
    private List<String> extractClassNames(List<File> javaFiles){
        List<String> classNames = new ArrayList<>();
        for(File f : javaFiles) {
            String fileName = f.getName();
            classNames.add(fileName.substring(0, fileName.lastIndexOf(".")));
        }
        return classNames;
    }
}
