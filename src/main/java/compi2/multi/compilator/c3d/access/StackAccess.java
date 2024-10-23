
package compi2.multi.compilator.c3d.access;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class StackAccess extends MemoryAccess{
    private int position;
    private MemoryAccess memoryAccess;

    public StackAccess(int position) {
        this.position = position;
    }
    
    public StackAccess(MemoryAccess memoryAccess){
        this.memoryAccess = memoryAccess;
        this.position = -1;
    }
    
}
