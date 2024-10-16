
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.semantic.j.JControlStmt;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class JWhileAst extends JControlStmt{

    private JExpression condition;

    public JWhileAst(Position initPos, JExpression condition, List<JStatement> internalStmts) {
        super(initPos);
        this.condition = condition;
        super.internalStmts = internalStmts;
    }

    /*@Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        super.validateCondition(condition, symbolTable, typeTable, semanticErrors);
        return super.validateInternalStmts(symbolTable, typeTable, semanticErrors, 
                new SemanticRestrictions(
                        true, 
                        true, 
                        restrictions.getReturnType(), 
                        restrictions.getReturnType())
        );
        return null;
        
    }
    */
    
}
