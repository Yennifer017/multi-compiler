
package compi2.multi.compilator.analysis.symbolt;

import java.util.LinkedHashMap;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter 
public class SymbolTable extends LinkedHashMap<String, RowST>{
    private SymbolTable father;
    private int lastDir;
        
    public void incrementLastDir(int totalSpaces){
        lastDir += totalSpaces;
    }
    
}
