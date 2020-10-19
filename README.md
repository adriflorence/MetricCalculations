# CS409 - Chidamber and Kemerer Metrics

The aim of this assignment is to gain experience in building a program analysis tool using JavaParser.
The task is to build an analyser to calculate a subset of a well known set of code metrics known as the Chidamber and Kemerer (or C&K) metrics.

A metric is simply a measurement of some property of the code and they are used to support tasks such as software quality assurance.
See https://en.wikipedia.org/wiki/Software_metric  for more details.

This assignment will consider the following 4 metrics of the C&K suite:

- Weighted Methods per Class (WMC)
- Coupling Between Objects (CBO)
- Response For a Class (RFC)
- Lack of Cohesion in Methods (LCOM)

Metrics in detail (extract from report)
---
WMC (basic):
To calculate the basic interpretation of WMC (not-so Weighted Methods for Class) a visitor was created that implemented Javaparser’s `GenericVisitorAdapter`. After overriding the visit method that accepts `ClassOrInterfaceDeclaration` as a parameter, the `ClassOrInterfaceDeclaration`’s `getMethods()` method became available; which returns the methods declared within a given class as a list. Because each method weights 1, returning the size of this list gave the basic WMC metric for the given class.

WMC2 (implemented with cyclomatic complexity):
To calculate the second adaptation of WMC metric, a simplified interpretation of the cyclomatic complexity was considered. This simplified implementation counts the number of decisions in a method and adds 1.

CBO:
For calculating Coupling Between Objects (CBO) the best approach has proved to be working on a collection (Map) of classes and the related references. The Map was set up so that each key is a class name and each value is a list of those class names that references that class.
This structure was built while visiting the nodes (each Compilation Unit). The visiting process was the following: 
1. Retrieving all the `ClassOrInterfaceTypes` of the class
2. Filtering down to those classes whose name appears in the original source input directory (With the use of a list of class names and the `IsMemberOfClassNames` predicate)
This way all the library references were removed, however interface and super class references, as well as the given class's own name was still included.
3. To resolve this, it was examined whether the class has any interfaces and/or super classes and these were removed from the reference if there were any.
4. Finally the class name itself was removed as well.

RFC:
To calculate the size of the Response set of a class the MethodAndRemoteMethodCountVisitor was implemented that counted "all the methods in the class and all the methods that are called by methods in that class. As it is a set each called method is only counted once no matter how many times it is called." 
To achieve this, first all the MethodDeclarations were counted in a class, then all the MethodCallExpr nodes. The sum of these two provided the RFC (Response For Class) metric of a class.

LCOM:
The aim is to find where fields were referred to within a method body. It is done by visting every `NameExpn`, which is what javaparser considers every variable name in an expression, within each MethodBody. There are however, special cases where `NameExpn` was not used, for example in `this.fieldname`. These cases are considered. There is also the special case where a method parameter has the same name as a class field. This is also handled in the solution.
I later found out that I could have visited every `SimpleName` (within each method) instead, but I believe that then I would need to manually consider the context around each occurrence.

Test System and Accuracy
---
To evaluate how well the system works, there is a test code folder provided (CS409TestSystem2020) to evaluate the performance of the solution. This is the unzipped version of archive provided to us for testing.

We believe the Metrics are 100% accurate and deserve the full 20 marks.

Relative Contribution
---

Jossy George completed the LCOM calculations, provided a pretty printer and being a more experienced Java programmer, contributed by suggesting edge cases for each metric and some refactoring of the codebase.
Jossy contributed approximately 35%

Adri Forczek set up the project skeleton, implemented the two WMC metrics, the RFC metric and the CBO Metric.
Adri contributed approximately 65%