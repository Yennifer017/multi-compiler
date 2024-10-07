
package compi2.multi.compilator.analysis.symbolt.clases;

import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;

/**
 *
 * @author blue-dragon
 */
public class ClassST extends RowST{
    
    private SymbolTable internalST;

    public ClassST(String name, SymbolTable internalST) {
        super(name, Category.JClass, null);
        this.internalST = internalST;
    }
    
    @Override
    public boolean isLinked() {
        return true;
    }
    
}
