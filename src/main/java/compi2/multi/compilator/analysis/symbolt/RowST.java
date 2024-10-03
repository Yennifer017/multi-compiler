
package compi2.multi.compilator.analysis.symbolt;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public abstract class RowST {
    protected String name;
    protected Category category;
    protected String type;

    public RowST(String name, Category category, String type) {
        this.name = name;
        this.category = category;
        this.type = type;
    }
    
    
    public abstract boolean isLinked();
    
            
}
