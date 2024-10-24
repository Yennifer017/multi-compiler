
package compi2.multi.compilator.c3d.access;

import compi2.multi.compilator.c3d.AdmiMemory;
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

    @Override
    public StringBuilder generateCcode(StringBuilder builder) {
        if(memoryAccess != null){
            builder.append(AdmiMemory.STACK_PTR + "[");
            memoryAccess.generateCcode(builder);
            builder.append("]");
        } else {
            builder.append(AdmiMemory.STACK_PTR + "[")
                    .append(position)
                    .append("]");
        }
        return builder;
    }
    
}
