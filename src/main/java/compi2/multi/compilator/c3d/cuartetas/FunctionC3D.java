
package compi2.multi.compilator.c3d.cuartetas;

import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class FunctionC3D extends Cuarteta{
    private String name;
    private Memory internalMemory;
    private List<Cuarteta> cuartetas;

    public FunctionC3D(String name, Memory internalMemory, List<Cuarteta> cuartetas) {
        this.name = name;
        this.internalMemory = internalMemory;
        this.cuartetas = cuartetas;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        builder.append("void ");
        builder.append(name);
        builder.append("(){\n");
        internalMemory.generateCcode(builder);
        if(!cuartetas.isEmpty()){
            for (Cuarteta cuarteta : cuartetas) {
                cuarteta.generateCcode(builder);
            }
        }
        builder.append("}\n");
    }
    
}
