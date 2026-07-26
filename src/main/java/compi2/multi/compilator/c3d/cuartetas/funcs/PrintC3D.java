
package compi2.multi.compilator.c3d.cuartetas.funcs;

import compi2.multi.compilator.assembly.AssemblyComps;
import compi2.multi.compilator.assembly.AssemblyConf;
import compi2.multi.compilator.assembly.interfaces.AssemblyPreparable;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.access.MemoryAccess;

/**
 *
 * @author blue-dragon
 */
public class PrintC3D extends Cuarteta implements AssemblyPreparable{
    
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

    @Override
    public void prepareForAssembly(AssemblyComps ac) {
        if(access instanceof AssemblyPreparable ap){
            ap.prepareForAssembly(ac);
        }
    }

    @Override
    public void generateAssemblyNasmCode(StringBuilder builder, AssemblyComps ac) {
        builder.append(access.getNasmPrintableCode(ac));
        if(withLn){
            builder.append("call " + AssemblyConf.PRINT_LN_FUNCT_NAME + "\n");
        }
    }
    
}
