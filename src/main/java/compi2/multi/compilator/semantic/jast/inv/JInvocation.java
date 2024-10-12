
package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.util.Position;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JInvocation {
    private Position position;
    private JContextRef context;
    
    public JInvocation(Position position, JContextRef context){
        this.position = position;
        this.context = context;
    }
    
}
