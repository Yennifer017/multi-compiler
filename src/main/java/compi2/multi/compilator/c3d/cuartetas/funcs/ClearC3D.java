
package compi2.multi.compilator.c3d.cuartetas.funcs;

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
    
}
