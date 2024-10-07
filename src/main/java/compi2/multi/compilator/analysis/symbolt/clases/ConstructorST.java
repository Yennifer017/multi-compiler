
package compi2.multi.compilator.analysis.symbolt.clases;

import compi2.multi.compilator.analysis.symbolt.AccessMod;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.ModuleRowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analyzator.Analyzator;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class ConstructorST extends ModuleRowST{
    
    private AccessMod access;

    public ConstructorST(String name, SymbolTable internalST, List<String> typesParams) {
        super(name, Category.JConstruct, Analyzator.VOID_METHOD, internalST, typesParams);
    }
    
    @Override
    public boolean isLinked() {
        return true;
    }

    @Override
    public String getCompleateName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
