
package compi2.multi.compilator.analysis.symbolt.clases;

import compi2.multi.compilator.analysis.symbolt.AdditionalInfoST;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;

/**
 *
 * @author blue-dragon
 */
public class HeapDirecST extends RowST{

    public HeapDirecST() {
        super(AdditionalInfoST.DIR_HEAP_ROW.getNameRow(), Category.JHeapDir, null);
    }

    @Override
    public boolean isLinked() {
        return false;
    }
    
}
