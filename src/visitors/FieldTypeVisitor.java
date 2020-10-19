package visitors;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

import java.lang.reflect.Field;


public class FieldTypeVisitor extends GenericVisitorAdapter<Class, Void> {

    @Override
    public Class visit(FieldDeclaration n, Void arg) {
        Type FieldType = n.getElementType();
        this.visit(n,arg);
        return FieldType.getClass();
    }
}
