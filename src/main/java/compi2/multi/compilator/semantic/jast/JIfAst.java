
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.semantic.j.JControlStmt;
import compi2.multi.compilator.semantic.Statement;
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
public class JIfAst extends JControlStmt{
    private JExpression condition;
    private List<JIfAst> elifs;
    private JElseAst elseStmt;

    public JIfAst(Position initPos, JExpression condition, List<JStatement> statements) {
        super(initPos);
        this.condition = condition;
        super.internalStmts = statements;
    }

    public JIfAst(Position initPos, JExpression condition, List<JStatement> statements, 
            List<JIfAst> elifs, JElseAst elseStmt) {
        super(initPos);
        this.condition = condition;
        this.elifs = elifs;
        this.elseStmt = elseStmt;
        super.internalStmts = statements;
    }

    /*@Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        super.validateCondition(condition, symbolTable, typeTable, semanticErrors);
        
        ReturnCase internalRC = super.validateInternalStmts(symbolTable, typeTable, semanticErrors, 
                restrictions
        );*/
        
        /*if(elifs != null && !elifs.isEmpty()){
            for (IfAst ifAst : elifs) {
                ReturnCase pathRC = ifAst.validate(symbolTable, typeTable, semanticErrors, restrictions);
                if(internalRC.isAllScenaries() && !pathRC.isAllScenaries()){
                    internalRC.setAllScenaries(false);
                }
            }
        }*/
        
        /*if(elseStmt != null){
            ReturnCase elseRC = elseStmt.validate(symbolTable, typeTable, semanticErrors, restrictions);
            if(internalRC.isAllScenaries() && !elseRC.isAllScenaries()){
                internalRC.setAllScenaries(false);
            }
        } else {
            internalRC.setAllScenaries(false);
        }
        return null;
    }
    */
}
