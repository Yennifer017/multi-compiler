
package compi2.multi.compilator.analysis.symbolt.estruc;

import compi2.multi.compilator.analysis.symbolt.AdditionalInfoST;
import compi2.multi.compilator.analysis.symbolt.Category;
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

    public FunctionST(String name, SymbolTable internalST, List<String> typesParams) {
        super(name, Category.Procedure, Analyzator.VOID_METHOD, internalST, typesParams);
    }
    
    public FunctionST(String name, String type, SymbolTable internalST, List<String> typesParams){
        super(name, Category.Function, type, internalST, typesParams);
        this.internalST = internalST;
        this.typesParams = typesParams;
        super.addReturRow();
    }
    
    @Override
    public String getCompleateName(){
        if(this.convertedName == null){
            convertedName = super.name;
            convertedName += "(";
            if(typesParams != null){
                for (int i = 0; i < typesParams.size(); i++) {
                    convertedName += typesParams.get(i);
                    if(i != typesParams.size() - 1) {
                        convertedName += ", ";
                    }
                }
            }
            convertedName += ")";
        }
        return convertedName;
    }


    @Override
    public boolean isLinked() {
        return true;
    }
    
    
    
}
