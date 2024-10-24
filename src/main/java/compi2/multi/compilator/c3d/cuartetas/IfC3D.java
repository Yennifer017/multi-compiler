
package compi2.multi.compilator.c3d.cuartetas;

import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import compi2.multi.compilator.semantic.DefiniteOperation;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class IfC3D extends Cuarteta{
    private MemoryAccess first;
    private MemoryAccess second;
    private DefiniteOperation operation;
    private GotoC3D gotoTrue;

    public IfC3D(MemoryAccess first, MemoryAccess second, DefiniteOperation operation, GotoC3D gotoTrue) {
        this.first = first;
        this.second = second;
        this.operation = operation;
        this.gotoTrue = gotoTrue;
    }
    
    
}
