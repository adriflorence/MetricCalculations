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
To calculate this metric a simplified interpretation of the cyclomatic complexity was considered. The more complex WMC metrics counts the number of decisions in a method and adds 1.

CBO:
For calculating Coupling Between Objects (CBO) the best approach has proved to be working on a collection (Map) of classes and the related references.

RFC
...

LCOM:
The aim is to find where fields were referred to within a method body. It is done by visting every `NameExpn`, which is what javaparser considers every variable name in an expression, within each MethodBody. There are however, special cases where `NameExpn` was not used, for example in `this.fieldname`. These cases are considered. There is also the special case where a method parameter has the same name as a class field. This is also handled in the solution.
I later found out that I could have visited every `SimpleName` (within each method) instead, but I believe that then I would need to manually consider the context around each occurrence.

Test System and Accuracy
---
To evaluate how well the system works, there is a test code folder provided (CS409TestSystem2020) to evaluate the performance of the solution. This is the unzipped version of archive provided to us for testing.

We believe the Metrics are 100% accurate and deserve the full 20 marks.

Relative Contribution
---

Jossy completed the LCOM calculations, provided a pretty printer and being a more experienced Java programmer, contributed by suggesting edge cases for each metric and some refactoring of the codebase.
Jossy contributed 30 %

Adri Forczek set up the project skeleton, implemented the two WMC metrics, the RFC metric and the CBO Metric.
Adri contributed 70 %
