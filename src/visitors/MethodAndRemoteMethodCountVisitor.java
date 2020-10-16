package visitors;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

import java.util.HashSet;
import java.util.Set;

public class MethodAndRemoteMethodCountVisitor extends GenericVisitorAdapter<Integer, Void> {

    @Override
    public Integer visit(ClassOrInterfaceDeclaration n, Void arg) {
        return super.visit(n, arg);
    }

    @Override
    public Integer visit(MethodDeclaration n, Void arg) {
        Set<MethodCallExpr> methodCallExpressions = new HashSet<>();
        return traverseTree(n, methodCallExpressions).size();
    }

    // traverses method tree and counts all method calls within that method
    private Set<MethodCallExpr> traverseTree(Node node, Set<MethodCallExpr> methodCallExpressions) {
        if(node.getChildNodes().isEmpty()) {
            return methodCallExpressions;
        } else {
            for(Node child : node.getChildNodes()){
                if(child instanceof MethodCallExpr) {
                    methodCallExpressions.add((MethodCallExpr) child);
                }
                methodCallExpressions.addAll(traverseTree(child, methodCallExpressions));
            }
        }
        return methodCallExpressions;
    }
}
