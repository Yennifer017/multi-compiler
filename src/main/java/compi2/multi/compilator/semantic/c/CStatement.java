
package compi2.multi.compilator.semantic.c;

import compi2.multi.compilator.util.Position;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public abstract class CStatement {
    protected Position initPos;
    
    public CStatement(Position initPos){
        this.initPos = initPos;
    }
}
