package com.metricsassignment.visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.metricsassignment.predicate.IsExtendedOrImplementedType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CouplingBetweenObjectsVisitor extends GenericVisitorAdapter<Set<String>, Void> {

    @Override
    public Set<String> visit(ClassOrInterfaceDeclaration n, Void arg) {
        Set<String> references = new HashSet<>();

        // interfaces and base class should to be ignored
        List<ClassOrInterfaceType> baseClass = n.getExtendedTypes();
        List<ClassOrInterfaceType> interFaces = n.getImplementedTypes();

        IsExtendedOrImplementedType isExtendedOrImplementedType = new IsExtendedOrImplementedType(baseClass, interFaces);
        // all types that are not interfaces
        List<ClassOrInterfaceType> allTypes = n.findAll(ClassOrInterfaceType.class, isExtendedOrImplementedType);
        for (ClassOrInterfaceType type : allTypes) {
            references.add(type.getName().asString());
        }
        return references;
    }
}

