
package compi2.multi.compilator.c3d.cuartetas;

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
    
}
