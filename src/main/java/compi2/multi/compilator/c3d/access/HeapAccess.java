
package compi2.multi.compilator.c3d.access;

import compi2.multi.compilator.c3d.AdmiMemory;

/**
 *
 * @author blue-dragon
 */
public class HeapAccess extends MemoryAccess{
    private int position;

    private MemoryAccess memoryAccess;
    
    public HeapAccess(int position) {
        this.position = position;
    }

    public HeapAccess(MemoryAccess memoryAccess) {
        this.memoryAccess = memoryAccess;
    }
    
    @Override
    public void generateCcode(StringBuilder builder) {
        if(memoryAccess != null){
            builder.append(AdmiMemory.HEAP_PTR + "[");
            memoryAccess.generateCcode(builder);
            builder.append("]");
        } else {
            builder.append(AdmiMemory.HEAP_PTR + "[")
                    .append(position)
                    .append("]");
        }
    }
    
}
