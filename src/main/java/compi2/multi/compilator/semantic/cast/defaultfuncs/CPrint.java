
package compi2.multi.compilator.semantic.cast.defaultfuncs;

import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CPrint extends CStatement {
    private List<CExp> expressions;
    
    public CPrint(Position initPos, List<CExp> expressions) {
        super(initPos);
        this.expressions = expressions;
    }
    
}
