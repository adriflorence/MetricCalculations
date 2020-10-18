package visitors;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

import java.util.List;

public class CyclomaticComplexityVisitor extends GenericVisitorAdapter<Integer, Void> {

    @Override
    public Integer visit(ClassOrInterfaceDeclaration n, Void arg) {
        return countDecisions(n);

    }

    private Integer countDecisions(Node node) {
        Integer counter = 0;

        // count all if statements
        List<IfStmt> ifStatements = node.findAll(IfStmt.class);
        counter += ifStatements.size();
        for (IfStmt ifStmt : ifStatements) {
            // add all binary expr within if/else statements that return a Boolean (incl '==')
            List<BinaryExpr> binaryExprs = ifStmt.getCondition().findAll(BinaryExpr.class);
            counter += binaryExprs.size();
            // add else statements
            if(ifStmt.getElseStmt().isPresent()) {
                counter++;
            }
        }
        return counter;
    }
}
