
package compi2.multi.compilator.analysis.symbolt.estruc;

import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.typet.Limits;
import compi2.multi.compilator.exceptions.SemanticException;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class ArrayST extends RowST {
    private int totalIndex;
    private int relativeDir;
    private Limits limits;

    public ArrayST(String name, String type, Limits limits, int relativeDir) 
            throws SemanticException {
        super(name, Category.Array, type);
        if(limits.containsNegative()){
            throw new SemanticException();
        }
        this.limits = limits;
        this.totalIndex = limits.calculateDimension();
        this.relativeDir = relativeDir;
    }


    @Override
    public boolean isLinked() {
        return false;
    }

}
