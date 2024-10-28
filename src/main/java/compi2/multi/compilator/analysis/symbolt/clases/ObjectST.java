
package compi2.multi.compilator.analysis.symbolt.clases;

import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;

/**
 *
 * @author blue-dragon
 */
public class ObjectST extends RowST{
    private int relativeDir;

    public ObjectST(String name, String nameObject) {
        super(name,Category.JObject, nameObject);
    }

    @Override
    public boolean isLinked() {
        return false;
    }
    
}
