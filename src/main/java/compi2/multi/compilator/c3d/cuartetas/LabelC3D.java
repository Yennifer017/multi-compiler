
package compi2.multi.compilator.c3d.cuartetas;

import compi2.multi.compilator.c3d.AdmiMemory;
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

    @Override
    public StringBuilder generateCcode(StringBuilder builder) {
        builder.append(AdmiMemory.LABEL_C3D_NAME);
        builder.append(number);
        builder.append("\n");
        return builder;
    }
    
    
}
