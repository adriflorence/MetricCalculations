package visitors;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class CouplingCountVisitor extends VoidVisitorAdapter<Boolean>{

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

	public CouplingCountVisitor(List<Set<String>> methodFieldsList) {
		this.methodFieldsList = methodFieldsList;
		fieldSet = new HashSet<String>();
	}


	/*
	 * Add each field to a list. 
	 */
	public void visit(FieldDeclaration n, Boolean arg) {
		for(VariableDeclarator var : n.getVariables())
			fieldSet.add(var.getNameAsString());
	}

	/*
	 * We make sure we track visits to a method's body and consider that as one entry.
	 */
	@Override
	public void visit(MethodDeclaration n, Boolean arg){
		/*
		 * We use the extra argument to tell further visits to record.
		 */
		arg = true;

		workingFieldReferences = new HashSet<String>();
		super.visit(n, arg);
		if(workingFieldReferences.size() != 0)
			methodFieldsList.add(workingFieldReferences);
		//arg = false; coz source call has final param. Goes back to null
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
	 * There is a specific case were 
	 * a field name is used as a local parameter but not anything else.
	 * This method would wrongly add that as a field reference.
	 * For example, in a class with method {@code create(int a, int b)}
	 * and field {@code private int b}, if the body of the method
	 * uses the parameter and not the field, this visit method would still
	 * wrongly count it. 
	 */
	@Override
	public void visit(NameExpr n , Boolean arg) {
			if(arg != null && fieldSet.contains(n.getNameAsString()))
				workingFieldReferences.add(n.getNameAsString());
	}
	
	/**
	 * This method will catch usage of fields in expressions like {@code this.fieldname}
	 * Any type names before {@code this} like {@code World} in {@code World.this.fieldname} are ignored.
	 */
	@Override
	public void visit(FieldAccessExpr n, Boolean arg) {
		if(arg != null && n.getScope() instanceof ThisExpr && fieldSet.contains(n.getNameAsString()))
			workingFieldReferences.add(n.getNameAsString());
	}
}
