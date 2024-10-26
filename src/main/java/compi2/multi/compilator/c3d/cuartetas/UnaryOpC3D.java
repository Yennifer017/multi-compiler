
package compi2.multi.compilator.c3d.cuartetas;

import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import compi2.multi.compilator.semantic.DefiniteOperation;

/**
 *
 * @author blue-dragon
 */
public class UnaryOpC3D extends Cuarteta{
    
    private MemoryAccess variable;
    private DefiniteOperation operation;
    private MemoryAccess access;        

    public UnaryOpC3D(MemoryAccess variable, DefiniteOperation operation, MemoryAccess access){
        this.variable = variable;
        this.operation = operation;
        this.access = access;
    }
    
    @Override
    public void generateCcode(StringBuilder builder) {
        variable.generateCcode(builder);
        builder.append(" = ");
        builder.append(operation.getSign());
        access.generateCcode(builder);
        builder.append(";\n");
    }
    
}
