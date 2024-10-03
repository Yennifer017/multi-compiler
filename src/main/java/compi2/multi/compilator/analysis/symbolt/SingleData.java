
package compi2.multi.compilator.analysis.symbolt;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class SingleData extends RowST {
    private int relativeDir;

    public SingleData(String name, Category category, String type, int relativeDir) {
        super(name, category, type);
        this.relativeDir = relativeDir;
    }


    @Override
    public boolean isLinked() {
        return false;
    }
    

}
