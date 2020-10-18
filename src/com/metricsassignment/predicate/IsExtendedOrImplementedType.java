package com.metricsassignment.predicate;

import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.util.List;
import java.util.function.Predicate;

public class IsExtendedOrImplementedType implements Predicate<ClassOrInterfaceType> {

    private List<ClassOrInterfaceType> baseClass;
    private List<ClassOrInterfaceType> interFaces;

    public IsExtendedOrImplementedType(List<ClassOrInterfaceType> baseClass, List<ClassOrInterfaceType> interFaces) {
        this.baseClass = baseClass;
        this.interFaces = interFaces;
    }

    /**
     * Predicate that filters out interfaces and base class of the current class
     */
    @Override
    public boolean test(ClassOrInterfaceType classOrInterfaceType) {
        if (baseClass.contains(classOrInterfaceType) || interFaces.contains(classOrInterfaceType)) {
            return false;
        }
        return true;
    }
}
