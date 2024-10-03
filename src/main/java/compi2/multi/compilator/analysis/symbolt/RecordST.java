
package compi2.multi.compilator.analysis.symbolt;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class RecordST extends RowST{
    private int totalElements;
    private SymbolTable internalST;

    public RecordST(String name, SymbolTable internalST) {
        super(name, Category.Record, null);
        this.internalST = internalST;
    }


    @Override
    public boolean isLinked() {
        return true;
    }
    
}
