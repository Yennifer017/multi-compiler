
package compi2.multi.compilator.analysis.symbolt;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class InternalControlRowST extends RowST{
    
    private static final String GENERIC_NAME = "$internal_control_st";
    
    private SymbolTable internalST;

    public InternalControlRowST(SymbolTable internalST) {
        super(GENERIC_NAME, Category.InternalAnonymusST, null);
        this.internalST = internalST;
    }

    @Override
    public boolean isLinked() {
        return true;
    }
    
}
