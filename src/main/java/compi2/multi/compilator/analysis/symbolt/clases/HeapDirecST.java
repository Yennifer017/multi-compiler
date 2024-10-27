
package compi2.multi.compilator.analysis.symbolt.clases;

import compi2.multi.compilator.analysis.symbolt.AdditionalInfoST;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import lombok.Getter;

/**
 *
 * @author blue-dragon
 */
@Getter
public class HeapDirecST extends RowST{
    
    private int dirMemory;
    public HeapDirecST(int dirMemory) {
        super(AdditionalInfoST.DIR_HEAP_ROW.getNameRow(), Category.JHeapDir, null);
        this.dirMemory = dirMemory;
    }

    @Override
    public boolean isLinked() {
        return false;
    }
    
}
