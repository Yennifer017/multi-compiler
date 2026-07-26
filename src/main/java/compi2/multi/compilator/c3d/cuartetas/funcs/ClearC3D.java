
package compi2.multi.compilator.c3d.cuartetas.funcs;

import compi2.multi.compilator.assembly.AssemblyComps;
import compi2.multi.compilator.c3d.Cuarteta;

/**
 *
 * @author blue-dragon
 */
public class ClearC3D extends Cuarteta{

    @Override
    public void generateCcode(StringBuilder builder) {
        builder.append("std::cout.flush();\n");
        builder.append("system(\"clear\");\n");
    }

    @Override
    public void generateAssemblyNasmCode(StringBuilder builder, AssemblyComps ac) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
