
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.semantic.past.ElseAst;
import compi2.multi.compilator.semantic.past.IfAst;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class JIfAst extends JControlStmt{
    private Expression condition;
    private List<IfAst> elifs;
    private ElseAst elseStmt;

    public JIfAst(Expression condition, List<Statement> statements, Position initPos) {
        super(initPos);
        this.condition = condition;
        super.internalStmts = statements;
    }

    public JIfAst(Expression condition, List<Statement> statements, 
            List<IfAst> elifs, ElseAst elseStmt, Position initPos) {
        super(initPos);
        this.condition = condition;
        this.elifs = elifs;
        this.elseStmt = elseStmt;
        super.internalStmts = statements;
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        super.validateCondition(condition, symbolTable, typeTable, semanticErrors);
        
        ReturnCase internalRC = super.validateInternalStmts(symbolTable, typeTable, semanticErrors, 
                restrictions
        );
        
        if(elifs != null && !elifs.isEmpty()){
            for (IfAst ifAst : elifs) {
                ReturnCase pathRC = ifAst.validate(symbolTable, typeTable, semanticErrors, restrictions);
                if(internalRC.isAllScenaries() && !pathRC.isAllScenaries()){
                    internalRC.setAllScenaries(false);
                }
            }
        }
        
        if(elseStmt != null){
            ReturnCase elseRC = elseStmt.validate(symbolTable, typeTable, semanticErrors, restrictions);
            if(internalRC.isAllScenaries() && !elseRC.isAllScenaries()){
                internalRC.setAllScenaries(false);
            }
        } else {
            internalRC.setAllScenaries(false);
        }
        return internalRC;
    }
    
}
