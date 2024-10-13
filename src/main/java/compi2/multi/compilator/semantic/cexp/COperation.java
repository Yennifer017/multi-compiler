
package compi2.multi.compilator.semantic.cexp;

import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.util.Position;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class COperation extends CExp {
    
    private DefiniteOperation operation;
    private CExp leftExp;
    private CExp rightExp;
    
    public COperation(Position pos, DefiniteOperation operation, 
            CExp leftExp, CExp rightExp) {
        super(pos);
        this.operation = operation;
        this.leftExp = leftExp;
        this.rightExp = rightExp;
    }
    
}
