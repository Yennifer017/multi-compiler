
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
public class AdmiMemory {
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
}
