package com.metricsassignment.metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.javaparser.ast.CompilationUnit;

import visitors.CouplingCountVisitor;

// Lack of Cohesion of Methods
public class LCOMMetric implements Metric {
	
	/**
	 * p counts method pairs that don't share fields
	 * q counts method pairs that share fields
	 */
	private int p = 0, q = 0;

    @Override
    public double calculate(CompilationUnit compilationUnit) {
    	List<Set<String>> fieldsSetList = new ArrayList<Set<String>>();
    	new CouplingCountVisitor(fieldsSetList).visit(compilationUnit, null);
    	considerAllPairs(fieldsSetList);
        return p > q ? p - q : 0;
    }

    private void considerAllPairs(List<Set<String>> ls) {
    	int n = ls.size();
    	if(n == 2) {
    		if(doesShareFields(ls.get(0), ls.get(1)))
    			p++;
    		else
    			q++;
    	}
    	else if(n > 2) {
    		Set<String> first = ls.get(0);
    		List<Set<String>> newls = ls.subList(1, n);
    		for( Set<String> s : newls) {
    			if(doesShareFields(first, s))
    				p++;
    			else
    				q++;
    		}
    		considerAllPairs(newls);
    	}
    		
    }
    
    private boolean doesShareFields(Set<String> a, Set<String> b) {
    	for(String s : a) {
    		if(b.contains(s))
    			return true;
    	}
		return false;
    }
}
