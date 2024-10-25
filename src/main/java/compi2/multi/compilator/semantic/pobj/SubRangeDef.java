
package compi2.multi.compilator.semantic.pobj;

import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.DefAst;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.estruc.SubrangeST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
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
public class SubRangeDef extends DefAst{
    private Range range;
    private Label base;

    public SubRangeDef(Label name, Range range) {
        super();
        this.range = range;
        super.name = name;
        this.base = new Label(PrimitiveType.IntegerPT.getName(), null);
    }


    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors) {
        if(canInsert(symbolTable, semanticErrors)){
            int lastDir = symbolTable.getLastDir();
            symbolTable.incrementLastDir(1);
            return new SubrangeST(
                    name.getName(),
                    range.validate(symbolTable, semanticErrors, this),
                    lastDir
            );
        }
        return null;
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
