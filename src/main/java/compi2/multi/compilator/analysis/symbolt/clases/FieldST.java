
package compi2.multi.compilator.analysis.symbolt.clases;

import compi2.multi.compilator.analysis.symbolt.AccessMod;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.semantic.jclases.JField;

/**
 *
 * @author blue-dragon
 */
public class FieldST extends RowST{
    
    private AccessMod access;
    private JField jfield;
    
    public FieldST(String name, String type, AccessMod access, JField jfield) {
        super(name, Category.JField, type);
        this.access = access;
    }

    @Override
    public boolean isLinked() {
        return false;
    }
    
}
