
package compi2.multi.compilator.semantic.cast;

import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.util.Position;

/**
 *
 * @author blue-dragon
 */
public class CSimpleStmt extends CStatement{
    
    private boolean isBreak;
    
    public CSimpleStmt(Position initPos, boolean isBreak) {
        super(initPos);
        this.isBreak = isBreak;
    }
    
}
