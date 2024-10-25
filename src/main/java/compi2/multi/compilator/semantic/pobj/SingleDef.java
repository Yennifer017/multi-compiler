
package compi2.multi.compilator.semantic.pobj;


import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.DefAst;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class SingleDef extends DefAst {
    
    private Label base;

    public SingleDef(Label name, Label base) {
        super();
        this.name = name;
        this.base = base;
    }


    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors) {
        if(canInsert(symbolTable, semanticErrors)){
            if(refAnalyzator.existReference(typeTable, semanticErrors, base)){
                int lastDir = symbolTable.getLastDir();
                symbolTable.incrementLastDir(1);
                return new SingleData(
                        name.getName(), 
                        Category.Variable, 
                        base.getName(), 
                        lastDir
                );
            }
        }
        return null;
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
