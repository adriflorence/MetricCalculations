# CS409 - Metric Calculations

The aim of this assignment is to gain experience in building a program analysis tool using JavaParser.
The task is to build an analyser to calculate a subset of a well known set of code metrics known as the Chidamber and Kemerer (or C&K) metrics.

A metric is simply a measurement of some property of the code and they are used to support tasks such as software quality assurance.
See https://en.wikipedia.org/wiki/Software_metric  for more details.

The C&K suite consists of the following 6 metrics:

- Weighted Methods per Class (WMC)
- Coupling Between Objects (CBO)
- Response For a Class (RFC)
- Lack of Cohesion in Methods (LCOM)
- Number Of Children (NOC)
- Depth of Inheritance Tree (DIT)

However, this assignment will ignore the last two and focus just on the first four.


### Test System
To evaluate how well the system works, there is an archive of code test code provided to evaluate the performance of the solution.

When testing the system it needs to be able to run through this entire archive and report on the metric values for each class in each system.