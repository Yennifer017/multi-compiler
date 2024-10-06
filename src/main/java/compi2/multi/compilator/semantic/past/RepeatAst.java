
package compi2.multi.compilator.semantic.past;


import compi2.multi.compilator.semantic.ControlStruct;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class RepeatAst extends ControlStruct {
    private Expression condition;

    public RepeatAst(Expression condition, List<Statement> internalStmts, Position initPos) {
        super(initPos);
        this.condition = condition;
        super.internalStmts = internalStmts;
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors, SemanticRestrictions restrictions) {
        super.validateCondition(condition, symbolTable, typeTable, semanticErrors);
        return super.validateInternalStmts(symbolTable, typeTable, semanticErrors, 
                new SemanticRestrictions(
                        true, 
                        true, 
                        restrictions.getReturnType(), 
                        restrictions.getReturnType())
        );
    }

    
}
