
package compi2.multi.compilator.semantic.cast.assign;

import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.util.Position;

/**
 *
 * @author blue-dragon
 */
public abstract class CAssign extends CStatement {
    protected CExp expression;
    
    public CAssign(Position initPos, CExp expression) {
        super(initPos);
        this.expression = expression;
    }
    
}
