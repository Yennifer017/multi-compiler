
package compi2.multi.compilator.c3d;

import compi2.multi.compilator.c3d.interfaces.CodeTransformable;
import compi2.multi.compilator.c3d.util.Register;
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
    public final static String HEAP_ACCESS = "heap";
    public final static String STACK_ACCESS = "stack";
    
    private Memory stack;
    private Memory heap;
    private int countLabels;
    
    private List<String> definitions;
    private List<Cuarteta> cuartetas;
    
    public AdmiMemory(){
        stack = new Memory(STACK_ACCESS);
        heap = new Memory(HEAP_ACCESS);
        definitions = new LinkedList<>();
        cuartetas = new LinkedList<>();
    }
    

    @Override
    public void generateCcode(StringBuilder builder) {
        builder.append("#include <string>\n");
        builder.append("#include <iostream>\n");
        builder.append("#include <cmath>\n");
        
        builder.append("int " + HEAP_PTR + " = 0;\n");
        builder.append("int " + STACK_PTR + " = 0;\n");
        
        stack.incrementMemory(5000);
        stack.generateCcode(builder);
        heap.incrementMemory(5000);
        heap.generateCcode(builder);
        
        for (Register reg : Register.values()) {
            reg.generateCcode(builder);
        }

        if(!definitions.isEmpty()){
            for (String definition : definitions) {
                builder.append("void ");
                builder.append(definition).append("();\n");
            }
        }
        if(cuartetas != null){
            for (Cuarteta cuarteta : cuartetas) {
                cuarteta.generateCcode(builder);
            }
        }
    }

    /*@Override
    public void generateAssemblyCode(StringBuilder builder) {
        builder.append("section .bss");
        stack.setBytes(20000);
        stack.generateAssemblyCode(builder);
        heap.setBytes(20000);
        heap.generateAssemblyCode(builder);
        
        builder.append("section .data\n");
        builder.append(HEAP_PTR + " add 0;\n");
        builder.append(STACK_PTR + " add 0;\n");
        
        builder.append("section .text\n");
        builder.append("global _start\n");
        builder.append("_start:\n");
        
    }*/
}
