
package compi2.multi.compilator.semantic;


import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.StmtsAnalizator;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public abstract class ControlStruct extends Statement{
    protected List<Statement> internalStmts;
    protected StmtsAnalizator stmtsAnalizator;
    
    
    public ControlStruct(Position initPos){
        super(initPos);
        stmtsAnalizator = new StmtsAnalizator();
    }
    
    protected ReturnCase validateInternalStmts(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions){
        return stmtsAnalizator.validateInternalStmts(symbolTable, typeTable, semanticErrors, 
                restrictions, internalStmts);
    }
    
    protected void validateCondition(Expression condition, SymbolTable symbolTable, 
            TypeTable typeTable, List<String> semanticErrors){
        Label typeCond = condition.validateComplexData(symbolTable, typeTable, semanticErrors);
        if(!tConvert.isNumericIntegerType(typeCond.getName())){
            semanticErrors.add(errorsRep.incorrectTypeError(
                    typeCond.getName(), 
                    PrimitiveType.BooleanPT.getName(), 
                    typeCond.getPosition())
            );
        }
    }
    
}
