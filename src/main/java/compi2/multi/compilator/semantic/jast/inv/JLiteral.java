
package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import compi2.multi.compilator.util.Position;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JLiteral extends JInvocation{
    private Object object;

    public JLiteral(Position position, Object object) {
        super(position);
        this.object = object;
    }
    
    
}
