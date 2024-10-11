
package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import compi2.multi.compilator.semantic.util.JPassExp;
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
    private List<JPassExp> args;
    
    public JMethodUse(Position position, String name, JContextRef context) {
        super(position);
        this.name = name;
        this.context = context;
        this.args = new LinkedList<>();
    }
    
    
    public JMethodUse(Position position, String name, JContextRef context, List<JPassExp> args) {
        super(position);
        this.name = name;
        this.context = context;
        this.args = args;
    }
    
    
}
