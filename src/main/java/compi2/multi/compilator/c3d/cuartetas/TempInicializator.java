
package compi2.multi.compilator.c3d.cuartetas;

import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class TempInicializator extends Cuarteta{
    private Memory memory;

    public TempInicializator(Memory memory) {
        this.memory = memory;
    }
    
}
