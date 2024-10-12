
package compi2.multi.compilator.analysis.symbolt.clases;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import java.util.HashMap;
import lombok.Getter;

/**
 *
 * @author blue-dragon
 */
@Getter
public class JSymbolTable extends HashMap<String, ClassST>{
    private NodeJerarTree initJerarTree;
    
    public JSymbolTable(){
        initJerarTree = new NodeJerarTree();
    }
}
