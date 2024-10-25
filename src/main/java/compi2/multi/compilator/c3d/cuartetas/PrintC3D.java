
package compi2.multi.compilator.c3d.cuartetas;

import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.access.MemoryAccess;

/**
 *
 * @author blue-dragon
 */
public class PrintC3D extends Cuarteta{
    
    private MemoryAccess access;
    private boolean withLn;
    
    public PrintC3D(MemoryAccess access, boolean withLn){
        this.access = access;
        this.withLn = withLn;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        builder.append("std::cout<<");
        access.generateCcode(builder);
        if(withLn){
            builder.append("<< std::endl;\n");
        } else {
            builder.append(";\n");
        }
        
    }
    
}
