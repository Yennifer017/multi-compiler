
package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.util.Position;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JMethodUse extends JInvocation{
    
    private String name;
    private JContextRef context;
    private List<Expression> args;
    
    public JMethodUse(Position position, String name, JContextRef context) {
        super(position, context);
        this.name = name;
        this.args = new LinkedList<>();
    }
    
    
    public JMethodUse(Position position, String name, JContextRef context, List<Expression> args) {
        super(position, context);
        this.name = name;
        this.args = args;
    }
    
    
}
