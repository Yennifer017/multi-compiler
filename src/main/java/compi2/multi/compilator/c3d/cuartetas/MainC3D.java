
package compi2.multi.compilator.c3d.cuartetas;

import compi2.multi.compilator.assembly.AssemblyComps;
import compi2.multi.compilator.assembly.interfaces.AssemblyPreparable;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class MainC3D extends Cuarteta implements AssemblyPreparable{
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
    
    @Override
    public void prepareForAssembly(AssemblyComps ac) {
        if(cuartetas != null){
            for (Cuarteta cuarteta : cuartetas) {
                if (cuarteta instanceof AssemblyPreparable ap) {
                    ap.prepareForAssembly(ac);
                }
            }
        }
    }

    @Override
    public void generateAssemblyNasmCode(StringBuilder builder, AssemblyComps ac) {
        builder.append("""
                       ; ============================
                       ; Programa principal
                       ; ============================
                                                  
                       _start:
                       
                       """);
        builder.append(internalMemory.getReserveMemoryNasmCode());
        if(cuartetas != null){
            for (Cuarteta cuarteta : cuartetas) {
                cuarteta.generateAssemblyNasmCode(builder, ac);
            }
        }
        builder.append(internalMemory.getFreeMemoryNasmCode(false));
        
        builder.append("""
                           ; syscall exit
                           mov rax, 60
                           xor rdi, rdi
                           syscall
                       """);
    }
    
    
}
