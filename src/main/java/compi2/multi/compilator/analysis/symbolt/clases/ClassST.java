
package compi2.multi.compilator.analysis.symbolt.clases;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class ClassST extends RowST{
    
    private SymbolTable internalST;
    private NodeJerarTree jerar;

    public ClassST(String name, SymbolTable internalST, NodeJerarTree jerar) {
        super(name, Category.JClass, null);
        this.internalST = internalST;
        this.jerar = jerar;
    }
    
    @Override
    public boolean isLinked() {
        return true;
    }
    
}
