
package compi2.multi.compilator.semantic.ast;

import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.ReturnCase;
import compi2.multi.compilator.semantic.SemanticRestrictions;
import compi2.multi.compilator.semantic.exp.Expression;
import compi2.multi.compilator.semantic.obj.Label;
import compi2.multi.compilator.semantic.obj.Range;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class ForAst extends ControlStruct{
    private Label variable;
    private Range range;

    public ForAst(Label variable, Range range, List<Statement> internalStmts, Position initPos) {
        super(initPos);
        this.variable = variable;
        this.range = range;
        this.internalStmts = internalStmts;
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        if(refAnalyzator.existReference(symbolTable, semanticErrors, 
                variable)
                ){
            RowST row = refAnalyzator.getFromST(symbolTable, variable.getName());
            if(!super.tConvert.isNumericIntegerType(row.getType())){
                semanticErrors.add(errorsRep.incorrectVarTypeError(
                        variable.getName(), 
                        PrimitiveType.IntegerPT.getName(),
                        variable.getPosition())
                );
            }
        }
        validateIntData(range.getInit(), symbolTable, typeTable, semanticErrors);
        validateIntData(range.getEnd(), symbolTable, typeTable, semanticErrors);
        return super.validateInternalStmts(symbolTable, typeTable, semanticErrors, 
                new SemanticRestrictions(
                        true, 
                        true, 
                        restrictions.getReturnType(),
                        restrictions.getReturnType()
                )
        );
        
    }
    
    private void validateIntData(Expression exp, SymbolTable symbolTable, 
            TypeTable typeTable, List<String> semanticErrors){
        Label typeLabel = exp.validateComplexData(symbolTable, typeTable, semanticErrors);
        if(!tConvert.isNumericIntegerType(typeLabel.getName())){
            semanticErrors.add(errorsRep.incorrectTypeError(
                    typeLabel.getName(),
                    PrimitiveType.IntegerPT.getName(), 
                    typeLabel.getPosition())
            );
        }
    }

}
