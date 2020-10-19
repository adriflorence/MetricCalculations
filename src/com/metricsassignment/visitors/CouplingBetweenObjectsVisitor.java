package com.metricsassignment.visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.metricsassignment.predicate.IsMemberOfClassNames;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CouplingBetweenObjectsVisitor extends GenericVisitorAdapter<Set<String>, List<String>> {

    @Override
    public Set<String> visit(ClassOrInterfaceDeclaration n, List<String> classNames) {
        Set<String> references = new HashSet<>();

        // Predicate to filter out all found types that are not class names from the archive resource
        // This should cover for interfaces, base classes, library methods
        IsMemberOfClassNames isMemberOfClassNames = new IsMemberOfClassNames(classNames);

        List<ClassOrInterfaceType> interfaces = n.getImplementedTypes(); // returns interfaces or empty list
        List<ClassOrInterfaceType> superClass = n.getExtendedTypes(); // returns super class or empty list

        List<ClassOrInterfaceType> allTypes = n.findAll(ClassOrInterfaceType.class, isMemberOfClassNames);
        for (ClassOrInterfaceType type : allTypes) {

            // this is to exclude interface and base class references
            if ((!interfaces.contains(type)) && (!superClass.contains(type))) {
                references.add(type.getName().asString());
            }
        }
        return references;
    }
}

