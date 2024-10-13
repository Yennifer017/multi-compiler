
package compi2.multi.compilator.semantic.cast.inv;

import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CObjectInv extends CStatement{
    
    List<JInvocation> invocations;
    
    public CObjectInv(List<JInvocation> invocations) {
        super(null);
        this.invocations = invocations;     
    }
    
}
