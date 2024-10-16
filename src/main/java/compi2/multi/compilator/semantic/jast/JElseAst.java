
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.semantic.j.JControlStmt;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class JElseAst extends JControlStmt{
    
    public JElseAst(Position initPos, List<JStatement> stmts){
        super(initPos);
        super.internalStmts = stmts;
    }

    /*@Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        return super.validateInternalStmts(symbolTable, typeTable, semanticErrors, restrictions);
    }
    */
}
