
package compi2.multi.compilator.semantic.c;

import compi2.multi.compilator.util.Position;

/**
 *
 * @author blue-dragon
 */
public abstract class CStatement {
    protected Position initPos;
    
    public CStatement(Position initPos){
        this.initPos = initPos;
    }
}
