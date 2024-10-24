
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
public class GotoC3D extends Cuarteta{
    private int numberLabel;

    public GotoC3D(int numberLabel) {
        this.numberLabel = numberLabel;
    }

    @Override
    public StringBuilder generateCcode(StringBuilder builder) {
        builder.append("goto ");
        builder.append(AdmiMemory.LABEL_C3D_NAME);
        builder.append(numberLabel);
        builder.append(";\n");
        return builder;
    }
    
}
