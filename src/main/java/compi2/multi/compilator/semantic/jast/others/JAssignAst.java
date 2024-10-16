
package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.jast.inv.JContextRef;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
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
public class JAssignAst extends JStatement{
    
    private List<JInvocation> variable;
    private JExpression value;
    
    public JAssignAst(Position initPos, List<JInvocation> variable, JExpression value) {
        super(initPos);
        this.variable = variable;
        this.value = value;
    }
    
    public JAssignAst(Position initPos, List<JInvocation> variable, 
            JExpression value, JContextRef first) {
        super(initPos);
        try {
            variable.get(0).setContext(first);
            this.variable = variable;
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            this.variable = new LinkedList<>();
        }
        this.value = value;
    }
    

    
}
