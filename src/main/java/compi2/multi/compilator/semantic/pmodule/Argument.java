
package compi2.multi.compilator.semantic.pmodule;

import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.semantic.p.DefAst;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class Argument extends DefAst{
    private boolean isForReference;
    private Label type;

    public Argument(boolean isForReference, Label name, Label type) {
        super();
        this.isForReference = isForReference;
        super.name = name;
        this.type = type;
    }


    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors) {
        if(canInsert(symbolTable, semanticErrors) && refAnalyzator.existReference(typeTable, semanticErrors, type)){
            int relativeDir = symbolTable.getLastDir();
            symbolTable.incrementLastDir(1);
            return new SingleData(
                    name.getName(),
                    isForReference ? Category.Param_ref : Category.Param_val, 
                    type.getName(), 
                    relativeDir
            );
        }
        return null;
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
