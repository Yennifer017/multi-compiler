
package compi2.multi.compilator.semantic.cast;

import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class CElseAst extends CControlStmt{
    
    public CElseAst(Position initPos, List<CStatement> internalStmt) {
        super(initPos, internalStmt);
    }
    
}
