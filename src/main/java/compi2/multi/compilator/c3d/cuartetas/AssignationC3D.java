
package compi2.multi.compilator.c3d.cuartetas;

import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class AssignationC3D extends Cuarteta{
    private MemoryAccess variable;
    private MemoryAccess first;

    public AssignationC3D(MemoryAccess variable, MemoryAccess first) {
        this.variable = variable;
        this.first = first;
    }
    
}
