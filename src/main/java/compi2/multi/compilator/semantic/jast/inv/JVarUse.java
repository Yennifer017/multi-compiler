
package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.util.Position;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JVarUse extends JInvocation{
    private String name;
    
    public JVarUse(Position position, String name, JContextRef context) {
        super(position, context);
        this.name = name;
    }
    
}
