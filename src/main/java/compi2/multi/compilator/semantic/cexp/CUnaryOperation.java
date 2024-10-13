
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
public class CUnaryOperation extends CExp{
    private CExp exp;
    private DefiniteOperation operation;
    
    public CUnaryOperation(Position pos, DefiniteOperation operation, CExp exp) {
        super(pos);
        this.operation = operation;
        this.exp = exp;
    }
    
}
