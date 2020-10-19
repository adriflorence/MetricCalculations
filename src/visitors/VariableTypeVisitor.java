package visitors;

import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

public class VariableTypeVisitor extends GenericVisitorAdapter<Class, Void> {

    @Override
    public Class visit(VariableDeclarationExpr n, Void arg) {
        Type VariableType = n.getElementType();
        return VariableType.getClass();
    }
}