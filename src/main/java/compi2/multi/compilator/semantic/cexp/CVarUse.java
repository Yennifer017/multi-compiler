
package compi2.multi.compilator.semantic.cexp;

import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.util.Position;

/**
 *
 * @author blue-dragon
 */
public class CVarUse extends CExp{
    String name;
    
    public CVarUse(Position pos, String name) {
        super(pos);
        this.name = name;
    }
    
}
