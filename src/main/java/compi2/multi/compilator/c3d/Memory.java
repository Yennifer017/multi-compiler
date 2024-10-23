
package compi2.multi.compilator.c3d;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class Memory {
    private int stringCount;
    private int integerCount;
    private int floatCount;
    private int charCount;
    private int booleanCount;
    
    private String name;
    
    public Memory(String name){
        this.name = name;
    }
    
}
