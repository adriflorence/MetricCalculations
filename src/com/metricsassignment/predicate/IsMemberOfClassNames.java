package com.metricsassignment.predicate;

import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.util.List;
import java.util.function.Predicate;

public class IsMemberOfClassNames implements Predicate<ClassOrInterfaceType> {

    private List<String> classNames;

    public IsMemberOfClassNames(List<String> classNames) {
        this.classNames = classNames;
    }

    /**
     * Predicate that filters out all the types whose name is not a classname from the archive source
     */
    @Override
    public boolean test(ClassOrInterfaceType classOrInterfaceType) {
        return classNames.contains(classOrInterfaceType.getName().asString());
    }
}
