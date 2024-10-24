
package compi2.multi.compilator.c3d.access;

import compi2.multi.compilator.c3d.AdmiMemory;

/**
 *
 * @author blue-dragon
 */
public class StackPtrUse extends MemoryAccess{

    @Override
    public StringBuilder generateCcode(StringBuilder builder) {
        builder.append(AdmiMemory.STACK_PTR);
        return builder;
    }
    
}
