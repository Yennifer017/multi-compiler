
package compi2.multi.compilator.semantic.cast.dec;

import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.c.CDef;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CArrayObjDec extends CDef{
    private Label objectName;
    private List<CExp> dims;
    
    public CArrayObjDec(Label name, List<CExp> dims){
        super.name = name;
        this.dims = dims;
    }

    @Override
    public RowST generateRowST(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<String> semanticErrors) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    
}
