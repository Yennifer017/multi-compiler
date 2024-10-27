
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
    private TemporalUse temporalUse;
    private boolean isHeapAccess;

    public RetJInvC3D(TemporalUse temporalUse, boolean isHeapAccess) {
        this.temporalUse = temporalUse;
        this.isHeapAccess = isHeapAccess;
    }
    
}
