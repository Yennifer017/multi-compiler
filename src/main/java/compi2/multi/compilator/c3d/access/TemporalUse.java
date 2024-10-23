
package compi2.multi.compilator.c3d.access;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class TemporalUse extends MemoryAccess{
    private PrimitiveType type;
    private int countTemp;

    public TemporalUse(PrimitiveType type, int countTemp) {
        this.type = type;
        this.countTemp = countTemp;
    }
    
}
