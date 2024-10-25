
package compi2.multi.compilator.semantic;

import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analysis.typet.convert.TConvertidor;
import compi2.multi.compilator.analyzator.RefAnalyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.ErrorsRep;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public abstract class DefAst {
    protected Label name;
    protected ErrorsRep errorsRep;
    protected TConvertidor tConvert;
    protected RefAnalyzator refAnalyzator;
    
    public DefAst(){
        errorsRep = new ErrorsRep();
        tConvert = new TConvertidor();
        refAnalyzator = new RefAnalyzator();
    }
    
    
    public abstract RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors);
    
    protected boolean canInsert(TypeTable typeTable, List<String> semanticErrors){
        return refAnalyzator.canInsert(this.name, typeTable, semanticErrors);
    }
    
    protected boolean canInsert(SymbolTable symbolTable, List<String> semanticErrors){
        return refAnalyzator.canInsert(this.name, symbolTable, semanticErrors);
    }
    
    public void validateNumericIntegerType(SymbolTable symbolTable, Expression expression, List<String> semanticErrors){
        Label type = expression.validateSimpleData(symbolTable, semanticErrors);
        if(!tConvert.isNumericIntegerType(type.getName())){
            semanticErrors.add(errorsRep.inesperateTypeError(PrimitiveType.IntegerPT.getName(), 
                    type.getPosition()));
        } 
    }
    public abstract void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals);

}
