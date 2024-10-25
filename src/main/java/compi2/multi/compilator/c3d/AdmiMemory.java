
package compi2.multi.compilator.c3d;

import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class AdmiMemory implements CodeTransformable{
    public final static String HEAP_PTR = "h";
    public final static String STACK_PTR = "ptr";
    public final static String LABEL_C3D_NAME = "et";
    
    private Memory stack;
    private Memory heap;
    private int countLabels;
    
    private List<String> definitions;
    private List<Cuarteta> cuartetas;
    
    public AdmiMemory(){
        stack = new Memory("stack");
        heap = new Memory("heap");
        definitions = new LinkedList<>();
        cuartetas = new LinkedList<>();
    }
    

    @Override
    public void generateCcode(StringBuilder builder) {
        builder.append("int " + HEAP_PTR + " = 0;\n");
        builder.append("int " + STACK_PTR + " = 0;\n");
        stack.generateCcode(builder);
        heap.generateCcode(builder);
        if(!definitions.isEmpty()){
            for (String definition : definitions) {
                builder.append(definition).append("();");
            }
        }
        if(cuartetas != null){
            for (Cuarteta cuarteta : cuartetas) {
                cuarteta.generateCcode(builder);
            }
        }
    }
}
