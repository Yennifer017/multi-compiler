
package compi2.multi.compilator.c3d.access;

/**
 *
 * @author blue-dragon
 */
public class AtomicStringConvC3D extends MemoryAccess{
    
    private MemoryAccess access;
    public AtomicStringConvC3D(MemoryAccess access) {
        this.access = access;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
        if (access instanceof AtomicValue atomicValue && atomicValue.getValue() instanceof String) {
            access.generateCcode(builder);
        } if(access instanceof RegisterUse registerUse && registerUse.isStringRegister()){
            access.generateCcode(builder);
        } else {
            builder.append("std::to_string(");
            access.generateCcode(builder);
            builder.append(")");
        } 
    }
    
}
