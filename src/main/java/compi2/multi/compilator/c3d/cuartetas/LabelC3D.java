
package compi2.multi.compilator.c3d.cuartetas;

import compi2.multi.compilator.c3d.Cuarteta;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class LabelC3D extends Cuarteta{
    private int number;

    public LabelC3D(int number) {
        this.number = number;
    }
    
    
}
