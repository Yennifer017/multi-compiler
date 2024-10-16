
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.semantic.j.JControlStmt;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JForAst extends JControlStmt{

    private JStatement uniqueStmt;
    private JExpression condition;
    private JStatement everyStmt;
    
    public JForAst(Position initPos, JStatement uniqueStmt, JExpression condition, 
            JStatement everyStmt, List<JStatement> internalStmts) {
        super(initPos);
        super.internalStmts = internalStmts;
        this.uniqueStmt = uniqueStmt;
        this.condition = condition;
        this.everyStmt = everyStmt;
    }

    /*@Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors, SemanticRestrictions restrictions) {
        if(assign != null){
            assign.validateSS(symbolTable, typeTable, semanticErrors, restrictions);
        }
        if(increment != null){
            increment.validateSS(symbolTable, typeTable, semanticErrors, restrictions);
        };
        super.validateCondition(condition, symbolTable, typeTable, semanticErrors);
        return super.validateInternalStmts(symbolTable, typeTable, semanticErrors, restrictions);
        return null;
    }
    */
}