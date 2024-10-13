
package compi2.multi.compilator.semantic.c;

import compi2.multi.compilator.util.Position;

/**
 *
 * @author blue-dragon
 */
public abstract class CExp {
    protected Position pos;
    public CExp(Position pos){
        this.pos = pos;
    }
}
