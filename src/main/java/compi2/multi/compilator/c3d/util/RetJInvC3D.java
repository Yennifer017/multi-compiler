
package compi2.multi.compilator.c3d.util;

import compi2.multi.compilator.c3d.access.TemporalUse;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class RetJInvC3D {
    
    public static final int HEAP_ACCESS = 0, STACK_ACCESS = 1, TEMPORAL_USE = 2;
    
    private TemporalUse temporalUse;
    //private boolean isHeapAccess;
    private int typeAccess;

    public RetJInvC3D(TemporalUse temporalUse, int typeAccess) {
        this.temporalUse = temporalUse;
        //this.isHeapAccess = isHeapAccess;
        this.typeAccess = typeAccess;
    }
    
}
