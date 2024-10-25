
package compi2.multi.compilator.c3d.access;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.c3d.AdmiMemory;

/**
 *
 * @author blue-dragon
 */
public class HeapAccess extends MemoryAccess{
    private int position;

    private PrimitiveType type;
    private MemoryAccess memoryAccess;
    
    public HeapAccess(PrimitiveType type, int position) {
        this.type = type;
        this.position = position;
    }

    public HeapAccess(PrimitiveType type, MemoryAccess memoryAccess) {
        this.type = type;
        this.memoryAccess = memoryAccess;
    }
    
    @Override
    public void generateCcode(StringBuilder builder) {
        if(memoryAccess != null){
            builder.append(AdmiMemory.HEAP_ACCESS + "[");
            memoryAccess.generateCcode(builder);
            builder.append("]");
        } else {
            builder.append(AdmiMemory.HEAP_ACCESS + "[")
                    .append(position)
                    .append("]");
        }
    }
    
}
