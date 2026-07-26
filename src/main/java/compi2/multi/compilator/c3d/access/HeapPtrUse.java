
package compi2.multi.compilator.c3d.access;

import compi2.multi.compilator.assembly.AssemblyComps;
import compi2.multi.compilator.c3d.AdmiMemory;

/**
 *
 * @author blue-dragon
 */
public class HeapPtrUse extends MemoryAccess{

    @Override
    public void generateCcode(StringBuilder builder) {
        builder.append(AdmiMemory.HEAP_PTR);
    }

    @Override
    public String getNasmPrintableCode(AssemblyComps ac) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
