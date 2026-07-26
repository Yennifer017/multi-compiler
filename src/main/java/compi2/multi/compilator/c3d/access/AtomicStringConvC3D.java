
package compi2.multi.compilator.c3d.access;

import compi2.multi.compilator.assembly.AssemblyComps;
import compi2.multi.compilator.assembly.interfaces.AssemblyPreparable;

/**
 *
 * @author blue-dragon
 */
public class AtomicStringConvC3D extends MemoryAccess implements AssemblyPreparable{
    
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
    
    @Override
    public void prepareForAssembly(AssemblyComps ac) {
        if (access instanceof AssemblyPreparable atomicValue) {
            atomicValue.prepareForAssembly(ac);
        }
    }

    @Override
    public String getNasmPrintableCode(AssemblyComps ac) {
        return access.getNasmPrintableCode(ac);
    }

}
