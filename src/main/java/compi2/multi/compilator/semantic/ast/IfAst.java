
package compi2.multi.compilator.semantic.ast;


import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.ReturnCase;
import compi2.multi.compilator.semantic.SemanticRestrictions;
import compi2.multi.compilator.semantic.exp.Expression;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class IfAst extends ControlStruct{
    private Expression condition;
    private List<IfAst> elifs;
    private ElseAst elseStmt;

    public IfAst(Expression condition, List<Statement> statements, Position initPos) {
        super(initPos);
        this.condition = condition;
        super.internalStmts = statements;
    }

    public IfAst(Expression condition, List<Statement> statements, 
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
