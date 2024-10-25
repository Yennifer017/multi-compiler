
package compi2.multi.compilator.c3d.cuartetas;

import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import compi2.multi.compilator.semantic.DefiniteOperation;

/**
 *
 * @author blue-dragon
 */
public class OperationC3D extends Cuarteta{
    private MemoryAccess variable;
    private MemoryAccess first;
    private MemoryAccess second;
    private DefiniteOperation operation;

    public OperationC3D(MemoryAccess variable, MemoryAccess first, 
            MemoryAccess second, DefiniteOperation operation) {
        this.variable = variable;
        this.first = first;
        this.second = second;
        this.operation = operation;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        variable.generateCcode(builder);
        builder.append(" = ");
        first.generateCcode(builder);
        builder.append(operation.getSign());
        second.generateCcode(builder);
    }
    
}
