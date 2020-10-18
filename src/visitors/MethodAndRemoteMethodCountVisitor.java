package visitors;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

import java.util.HashSet;
import java.util.Set;

public class MethodAndRemoteMethodCountVisitor extends GenericVisitorAdapter<Integer, Void> {

    @Override
    public Integer visit(ClassOrInterfaceDeclaration n, Void arg) {
        // returns number of methods and methodCallExprs within those methods
        return n.getMethods().size() +
                collectMethodCallExpr(n, new HashSet<>()).size();
    }

    // traverses method tree and counts all method calls within that method
    private Set<String> collectMethodCallExpr(Node node, Set<String> methodCallExpressions) {
        if(node.getChildNodes().isEmpty()) {
            return methodCallExpressions;
        } else {
            for(Node child : node.getChildNodes()){
                if(child instanceof MethodCallExpr) {
                    methodCallExpressions.add(((MethodCallExpr) child).getNameAsString());
                }
                methodCallExpressions.addAll(collectMethodCallExpr(child, methodCallExpressions));
            }
        }
        return methodCallExpressions;
    }
}
