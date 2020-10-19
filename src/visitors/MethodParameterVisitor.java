package visitors;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

import java.util.ArrayList;


public class MethodParameterVisitor extends GenericVisitorAdapter<Class, Object> {

    @Override
    public Class visit(MethodDeclaration n, Object arg) {
        Class params = null;
        for(Parameter p : n.getParameters()){
            params = p.getType().getClass();
            p.accept(this,arg);
        }

        return params;
    }

}
