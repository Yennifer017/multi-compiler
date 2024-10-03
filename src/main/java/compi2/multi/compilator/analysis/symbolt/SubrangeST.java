
package compi2.multi.compilator.analysis.symbolt;


import compi2.multi.compilator.analysis.typet.Limits;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class SubrangeST extends RowST {
    private int relativeDir;
    private Limits limits;

    public SubrangeST(String name, Limits limits, int relativeDir) {
        super(name, Category.Subrange, PrimitiveType.IntegerPT.getName());
        this.limits = limits;
        this.relativeDir = relativeDir;
    }

    @Override
    public boolean isLinked() {
        return false;
    }
    
}
