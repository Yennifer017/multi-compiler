
package compi2.multi.compilator.analysis.symbolt.clases;

import compi2.multi.compilator.analysis.symbolt.AccessMod;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.ModuleRowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analyzator.Analyzator;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class MethodST extends ModuleRowST{
    
    private AccessMod access;

    public MethodST(String name, String type, SymbolTable internalST, List<String> typesParams) {
        super(name, Category.JMethod, type, internalST, typesParams);
        super.addReturRow();
    }
    
    public MethodST(String name, SymbolTable internalST, List<String> typesParams) {
        super(name, Category.JMethod, Analyzator.VOID_METHOD, internalST, typesParams);
    }

    @Override
    public String getCompleateName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isLinked() {
        return true;
    }
    
}
