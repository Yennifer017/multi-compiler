
package compi2.multi.compilator.analysis.typet.convert;

import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.util.Position;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class PrimFollowType {
    PrimitiveType primitiveType;
    Position position;

    public PrimFollowType(PrimitiveType primitiveType, Position position) {
        this.primitiveType = primitiveType;
        this.position = position;
    }
    
}
