
package compi2.multi.compilator.analysis.typet;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class Type {
    
    protected String name;
    protected int dimention;
    
    
    public Type(String name, int dimention) {
        this.name = name;
        this.dimention = dimention;
    }
    
    
    public boolean isLinked(){
        return false;
    };
}
