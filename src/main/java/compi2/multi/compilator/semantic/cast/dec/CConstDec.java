
package compi2.multi.compilator.semantic.cast.dec;

import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.DefAst;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class CConstDec extends DefAst{
    
    private PrimitiveType type;
    private CExp exp;
    
    public CConstDec(Label name, PrimitiveType type, CExp exp){
        super.name = name;
        this.type = type;
        this.exp = exp;
    }

    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
