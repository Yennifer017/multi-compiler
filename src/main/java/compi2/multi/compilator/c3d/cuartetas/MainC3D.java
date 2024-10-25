
package compi2.multi.compilator.c3d.cuartetas;

import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class MainC3D extends Cuarteta{
    private Memory internalMemory;
    private List<Cuarteta> cuartetas;
    
    public MainC3D(Memory internalMemory, List<Cuarteta> cuartetas) {
        this.internalMemory = internalMemory;
        this.cuartetas = cuartetas;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        builder.append("int main(){\n");
        internalMemory.generateCcode(builder);
        if(!cuartetas.isEmpty()){
            for (Cuarteta cuarteta : cuartetas) {
                cuarteta.generateCcode(builder);
            }
        }
        builder.append("return 0;\n}\n");
    }
    
    
}
