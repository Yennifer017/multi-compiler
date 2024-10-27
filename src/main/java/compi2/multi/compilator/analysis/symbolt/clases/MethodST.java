
package compi2.multi.compilator.analysis.symbolt.clases;

import compi2.multi.compilator.analysis.symbolt.AccessMod;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.InfParam;
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
    private int arrayDims;

    public MethodST(String name, String type, SymbolTable internalST, 
            List<InfParam> typesParams, AccessMod access, int arrayDims) {
        super(name, Category.JMethod, type, internalST, typesParams);
        this.access = access;
        this.arrayDims = arrayDims;
    }
    
    public MethodST(String name, SymbolTable internalST, List<InfParam> typesParams, 
            AccessMod access, int arrayDims) {
        super(name, Category.JMethod, Analyzator.VOID_METHOD, internalST, typesParams);
        this.access = access;
        this.arrayDims = arrayDims;
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
