package visitors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class CouplingCountVisitor extends VoidVisitorAdapter<Void>{

	/**
	 * The set of fields of this class
	 */
	private Set<String> fieldSet;
	/**
	 * A working set of fields referred to in the current method being visited.
	 */
	private Set<String> workingFieldReferences;

	/**
	 * The list of fields referred by each method.
	 */
	private List<Set<String>> methodFieldsList;

	/**
	 * We use this to tell further visits to record.
	 */
	private boolean doRecord;
	
	/**
	 * We use this to track parameter names
	 */
	private Set<String> params;
	
	public CouplingCountVisitor(List<Set<String>> methodFieldsList) {
		this.methodFieldsList = methodFieldsList;
		fieldSet = new HashSet<String>();
		doRecord = false;
	}


	/**
	 * Add each field to a list. 
	 */
	public void visit(FieldDeclaration n, Void arg) {
		for(VariableDeclarator var : n.getVariables())
			fieldSet.add(var.getNameAsString());
	}

	/**
	 * We make sure we track visits to a method's body and consider that as one entry.
	 */
	@Override
	public void visit(MethodDeclaration n, Void arg){
		doRecord = true;
		params = new HashSet<String>();
		for(Parameter p : n.getParameters()) {
			params.add(p.getNameAsString());
		}
		workingFieldReferences = new HashSet<String>();
		super.visit(n, null);
		if(workingFieldReferences.size() != 0)
			methodFieldsList.add(workingFieldReferences);
		doRecord = false;
	}

	/*
	 * Possible places where a field could be used:
	 * * 
	 * * FieldAccessExpr - only if the scope is a ThisExpr.Typename of the ThisExpr is ignored.
	 * * NameExpr
	 */

	/**
	 * This method will catch whenever the field names are used in a NameExpr.
	 * NameExpr are used in assignments, method call parameters.
	 */
	@Override
	public void visit(NameExpr n , Void arg) {
			if(doRecord && fieldSet.contains(n.getNameAsString()) && !params.contains(n.getNameAsString()))
				workingFieldReferences.add(n.getNameAsString());
	}
	
	/**
	 * This method will catch usage of fields in expressions like {@code this.fieldname}
	 * Any type names before {@code this} like {@code World} in {@code World.this.fieldname} are ignored.
	 */
	@Override
	public void visit(FieldAccessExpr n, Void arg) {
		if(doRecord && n.getScope() instanceof ThisExpr && fieldSet.contains(n.getNameAsString()))
			workingFieldReferences.add(n.getNameAsString());
	}
}
