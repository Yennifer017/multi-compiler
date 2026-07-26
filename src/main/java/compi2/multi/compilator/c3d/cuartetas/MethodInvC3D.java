
package compi2.multi.compilator.c3d.cuartetas;

import compi2.multi.compilator.assembly.AssemblyComps;
import compi2.multi.compilator.c3d.Cuarteta;

/**
 *
 * @author blue-dragon
 */
public class MethodInvC3D extends Cuarteta{
    
    private String name;
    
    public MethodInvC3D(String name){
        this.name = name;
    }

    @Override
    public void generateCcode(StringBuilder builder) {
            builder.append(name);
            builder.append("();\n");
    }

    @Override
    public void generateAssemblyNasmCode(StringBuilder builder, AssemblyComps ac) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
