
package compi2.multi.compilator.semantic.cast.assign;

import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CObjectAssign extends CAssign{
    
    private List<JInvocation> jinvocations;
    
    public CObjectAssign(Position initPos, CExp expression, List<JInvocation> jInvocations) {
        super(initPos, expression);
    }
    
}
