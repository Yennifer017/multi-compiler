
package compi2.multi.compilator.semantic.cast;

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
public abstract class CControlStmt extends CStatement{
    
    private List<CStatement> internalStmts;
    
    public CControlStmt(Position initPos, List<CStatement> internalStmt) {
        super(initPos);
        this.internalStmts = internalStmt;
    }
    
}
