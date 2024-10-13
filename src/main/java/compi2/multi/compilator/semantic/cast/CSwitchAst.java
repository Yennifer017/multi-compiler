
package compi2.multi.compilator.semantic.cast;

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
public class CSwitchAst extends CControlStmt{
    private List<CCase> cases;
    private CExp exp;
    
    public CSwitchAst(Position initPos, CExp exp, List<CCase> cases) {
        super(initPos, null);
        this.exp = exp;
        this.cases = cases;
    }
    
}
