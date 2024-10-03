
package compi2.multi.compilator.semantic.ast;

import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.ReturnCase;
import compi2.multi.compilator.semantic.SemanticRestrictions;
import compi2.multi.compilator.semantic.exp.Expression;
import compi2.multi.compilator.semantic.obj.Label;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class ArrayAssign extends Statement{
    private Label identifier;
    private Expression indexExp;
    private Expression valToAssign;
    

    public ArrayAssign(Label identifier, Expression index, Expression valToAssign) {
        super(identifier.getPosition());
        this.identifier = identifier;
        this.indexExp = index;
        this.valToAssign = valToAssign;
    }
    
    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        //validar el index
        Label typeIndex = indexExp.validateComplexData(symbolTable, typeTable, semanticErrors);
        if(!super.tConvert.isNumericIntegerType(typeIndex.getName())){
            semanticErrors.add(errorsRep.incorrectTypeError(
                    typeIndex.getName(), 
                    PrimitiveType.IntegerPT.getName(), 
                    typeIndex.getPosition())
            );
        }
        
        //validar la asignacion
        Label typeAssign = valToAssign.validateComplexData(symbolTable, typeTable, semanticErrors);
        if(refAnalyzator.existReference(
                symbolTable, semanticErrors, identifier)){
            RowST row = refAnalyzator.getFromST(symbolTable, identifier.getName());
            if(row.getCategory() != Category.Array){
                semanticErrors.add(super.errorsRep.invalidCategoryAccessError(
                        row.getName(), 
                        row.getCategory().getName(), 
                        Category.Array.getName(), 
                        identifier.getPosition()
                ));
            } else if(!row.getType().equals(typeAssign.getName())){
                semanticErrors.add(super.errorsRep.incorrectTypeError(
                        typeAssign.getName(), 
                        row.getType(), 
                        typeAssign.getPosition())
                );
            }
        }
        
        return new ReturnCase(false);
    }

    
}
