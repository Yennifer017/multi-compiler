
package compi2.multi.compilator.semantic.util;

import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JPassExp {
    private Expression expression;
    private List<JInvocation> invocations;

    public JPassExp(Expression expression) {
        this.expression = expression;
    }

    public JPassExp(List<JInvocation> invocations) {
        this.invocations = invocations;
    }
    
    public JPassExp(){}
    
    
}
