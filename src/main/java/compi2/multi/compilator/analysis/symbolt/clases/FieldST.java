
package compi2.multi.compilator.analysis.symbolt.clases;

import compi2.multi.compilator.analysis.symbolt.AccessMod;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.semantic.jclases.JField;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class FieldST extends RowST{
    
    private AccessMod access;
    private JField jfield;
    private int relativeDir;
    
    public FieldST(String name, String type, AccessMod access, int relativeDir, JField jfield) {
        super(name, Category.JField, type);
        this.access = access;
        this.relativeDir = relativeDir;
        this.jfield = jfield;
    }

    @Override
    public boolean isLinked() {
        return false;
    }
    
}
