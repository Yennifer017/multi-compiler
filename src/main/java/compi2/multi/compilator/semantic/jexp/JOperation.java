
package compi2.multi.compilator.semantic.jexp;

import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.util.Position;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JOperation extends JExpression{
    
    private JExpression right;
    private JExpression left;
    
    private DefiniteOperation operation;
    
    public JOperation(Position pos, DefiniteOperation operation, 
            JExpression right, JExpression left){
        super(pos);
        this.operation = operation;
        this.left = left;
        this.right = right;
    }
    
}
