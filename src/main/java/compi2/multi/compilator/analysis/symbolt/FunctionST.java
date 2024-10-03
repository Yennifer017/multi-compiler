
package compi2.multi.compilator.analysis.symbolt;

import compi2.multi.compilator.analyzator.Analyzator;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class FunctionST extends RowST{
    private SymbolTable internalST;
    private List<String> typesParams; 
    
    private String convertedName;

    public FunctionST(String name, SymbolTable internalST, List<String> typesParams) {
        super(name, Category.Procedure, Analyzator.VOID_METHOD);
        this.internalST = internalST;
        this.typesParams = typesParams;
    }
    
    public FunctionST(String name, String type, SymbolTable internalST, List<String> typesParams){
        super(name, Category.Function, type);
        this.internalST = internalST;
        this.typesParams = typesParams;
    }
    
    /**
     *
     * @return el nombre completo
     */
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
