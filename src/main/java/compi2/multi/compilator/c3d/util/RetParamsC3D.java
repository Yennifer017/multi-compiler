
package compi2.multi.compilator.c3d.util;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.c3d.access.TemporalUse;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class RetParamsC3D {
    private Object atomicValue;
    private PrimitiveType primType;
    private TemporalUse temporalUse;

    public RetParamsC3D(TemporalUse temporalUse) {
        this.temporalUse = temporalUse;
    }

    public RetParamsC3D(Object atomicValue, PrimitiveType primType) {
        this.atomicValue = atomicValue;
        this.primType = primType;
    }
    
}
