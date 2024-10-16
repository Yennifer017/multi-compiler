
package compi2.multi.compilator.semantic.jast.others;

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
public class JMethodUseStmt extends JStatement {
    
    private List<JInvocation> invocations;
    public JMethodUseStmt(Position initPos, List<JInvocation> invocations) {
        super(initPos);
        this.invocations = invocations;
    }
    
    public JMethodUseStmt(Position initPos, List<JInvocation> invocations, JContextRef firstContextRef){
        super(initPos);
        try {
            invocations.get(0).setContext(firstContextRef);
            this.invocations = invocations;
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            this.invocations = new LinkedList<>();
        }
    }

    
}
