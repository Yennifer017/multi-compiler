
package compi2.multi.compilator.semantic.past;

import compi2.multi.compilator.semantic.p.Statement;
import compi2.multi.compilator.analysis.symbolt.Category;
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
import compi2.multi.compilator.semantic.p.Expression;
import compi2.multi.compilator.semantic.util.Label;
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

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals, C3Dpass pass) {
        throw new UnsupportedOperationException("No se soportan assigancion a arreglos desde pascal");
    }

    
}
