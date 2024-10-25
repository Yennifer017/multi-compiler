
package compi2.multi.compilator.c3d.cuartetas.funcs;

import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.access.MemoryAccess;

/**
 *
 * @author blue-dragon
 */
public class ScanC3D extends Cuarteta{

    private MemoryAccess access;
    
    public ScanC3D(MemoryAccess access){
        this.access = access;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        builder.append("std::cin >> ");
        access.generateCcode(builder);
        builder.append(";\n");
    }

    
}
