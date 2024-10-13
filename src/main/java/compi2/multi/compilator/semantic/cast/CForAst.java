
package compi2.multi.compilator.semantic.cast;

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
public class CForAst extends CControlStmt{
    private CStatement first;
    private CStatement last;
    private CExp condition;
    public CForAst(Position initPos, List<CStatement> internalStmt, 
            CStatement first, CStatement last, CExp condition) {
        super(initPos, internalStmt);
        this.first = first;
        this.last = last;
        this.condition = condition;
    }
    
}
