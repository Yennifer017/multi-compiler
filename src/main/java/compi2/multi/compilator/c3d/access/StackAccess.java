
package compi2.multi.compilator.c3d.access;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
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
    private PrimitiveType type;

    public StackAccess(PrimitiveType type, int position) {
        this.position = position;
        this.type = type;
    }
    
    public StackAccess(PrimitiveType type, MemoryAccess memoryAccess){
        this.memoryAccess = memoryAccess;
        this.position = -1;
        this.type = type;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        if(memoryAccess != null){
            builder.append(AdmiMemory.STACK_ACCESS)
                    .append(type.getName())
                    .append("[");
            memoryAccess.generateCcode(builder);
            builder.append("]");
        } else {
            builder.append(AdmiMemory.STACK_ACCESS)
                    .append(type.getName())
                    .append("[")
                    .append(position)
                    .append("]");
        }
    }
    
}
