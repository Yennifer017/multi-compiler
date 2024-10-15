
package compi2.multi.compilator.analysis.symbolt.clases;

import compi2.multi.compilator.analysis.symbolt.AdditionalInfoST;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class DirInstanceST extends RowST{
    
    private int dirMemory;

    public DirInstanceST(int dirMemory) {
        super(AdditionalInfoST.DIR_INSTANCE_ROW.getNameRow(), Category.JDirInstance, null);
        this.dirMemory = dirMemory;
    }

    @Override
    public boolean isLinked() {
        return false;
    }
    
}
