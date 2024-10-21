
package compi2.multi.compilator.analysis.symbolt.estruc;

import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JArrayST extends RowST{
    private List<Integer> listDims;
    private int totalDims;
    private int relativeDir;

    public JArrayST(String name, String type, int totalDims, int relativeDir) {
        super(name, Category.Array, type);
        this.totalDims = totalDims;
        this.relativeDir = relativeDir;
    }

    @Override
    public boolean isLinked() {
        return false;
    }
    
}
