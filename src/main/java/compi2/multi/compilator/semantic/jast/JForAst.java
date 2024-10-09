
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class JForAst extends JControlStmt{

    private Statement uniqueStmt;
    private Expression condition;
    private Statement everyStmt;
    
    public JForAst(Position initPos, Statement uniqueStmt, Expression condition, 
            Statement everyStmt, List<Statement> internalStmts) {
        super(initPos);
        super.internalStmts = internalStmts;
        this.uniqueStmt = uniqueStmt;
        this.condition = condition;
        this.everyStmt = everyStmt;
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors, SemanticRestrictions restrictions) {
        /*if(assign != null){
            assign.validateSS(symbolTable, typeTable, semanticErrors, restrictions);
        }
        if(increment != null){
            increment.validateSS(symbolTable, typeTable, semanticErrors, restrictions);
        };
        super.validateCondition(condition, symbolTable, typeTable, semanticErrors);
        return super.validateInternalStmts(symbolTable, typeTable, semanticErrors, restrictions);*/
        return null;
    }
    
}
