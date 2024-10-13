
package compi2.multi.compilator.semantic.cast;

import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class CWhileAst extends CControlStmt{
    private CExp condition;
    
    public CWhileAst(Position initPos, List<CStatement> internalStmt, CExp condition) {
        super(initPos, internalStmt);
        this.condition = condition;
    }
    
}
