
package compi2.multi.compilator.semantic.cexp;

import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CMethodUseExp extends CExp {
    private String name;
    private List<CExp> args;
    
    public CMethodUseExp(Position pos, String name, List<CExp> args) {
        super(pos);
        this.name = name;
        this.args = args;
    }
    
}
