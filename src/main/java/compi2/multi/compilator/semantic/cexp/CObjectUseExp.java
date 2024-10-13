
package compi2.multi.compilator.semantic.cexp;

import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CObjectUseExp extends CExp{
    List<JInvocation> invocations;
    public CObjectUseExp(List<JInvocation> invocations) {
        super(null);
        this.invocations = invocations;
    }
    
}
