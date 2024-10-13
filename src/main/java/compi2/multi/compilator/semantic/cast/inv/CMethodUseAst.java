
package compi2.multi.compilator.semantic.cast.inv;

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
public class CMethodUseAst extends CStatement{
    String name;
    private List<CExp> args;
    
    public CMethodUseAst(Position initPos, String name, List<CExp> args) {
        super(initPos);
        this.name = name;
        this.args = args;
    }
    
}
