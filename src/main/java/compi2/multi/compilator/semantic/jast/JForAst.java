
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.j.JControlStmt;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
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

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        if(uniqueStmt != null){
            uniqueStmt.validate(globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions);
        }
        if(everyStmt != null){
            uniqueStmt.validate(globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions);
        }
        super.validateCondition(globalST, symbolTable, typeTable, jerar, semanticErrors, 
                restrictions, condition);
        return super.validateInternalStmts(globalST, symbolTable, typeTable, jerar, semanticErrors, 
                new SemanticRestrictions(
                        true, 
                        true, 
                        restrictions.getReturnType(),
                        restrictions.getReturnType()
                ));
    }
}