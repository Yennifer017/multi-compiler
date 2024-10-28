
package compi2.multi.compilator.analysis.symbolt.estruc;

import compi2.multi.compilator.analysis.symbolt.AdditionalInfoST;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.InfParam;
import compi2.multi.compilator.analysis.symbolt.ModuleRowST;
import compi2.multi.compilator.analysis.symbolt.ReturnRow;
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
public class FunctionST extends ModuleRowST{

    public FunctionST(String name, SymbolTable internalST, List<InfParam> typesParams) {
        super(name, Category.Procedure, Analyzator.VOID_METHOD, internalST, typesParams);
    }
    
    public FunctionST(String name, String type, SymbolTable internalST, List<InfParam> typesParams){
        super(name, Category.Function, type, internalST, typesParams);
        this.internalST = internalST;
        super.addReturRow();
    }

    @Override
    public boolean isLinked() {
        return true;
    }
    
    
    
}
