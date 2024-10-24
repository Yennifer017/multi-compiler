
package compi2.multi.compilator.c3d.access;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.c3d.Memory;
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
    private Memory memory;

    public TemporalUse(PrimitiveType type, int countTemp, Memory memory) {
        this.type = type;
        this.countTemp = countTemp;
        this.memory = memory;
    }

    @Override
    public StringBuilder generateCcode(StringBuilder builder) {
        builder.append(memory.getMemoryName(type));
        builder.append("[");
        builder.append(countTemp);
        builder.append("]");
        return builder;
    }
    
}
