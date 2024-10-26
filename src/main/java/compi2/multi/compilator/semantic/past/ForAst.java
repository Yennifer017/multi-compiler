
package compi2.multi.compilator.semantic.past;

import compi2.multi.compilator.semantic.ControlStruct;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.pobj.Range;
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

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
