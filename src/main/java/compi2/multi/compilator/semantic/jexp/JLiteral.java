
package compi2.multi.compilator.semantic.jexp;

import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.util.Position;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JLiteral extends JExpression{
    private Object object;
    
    public JLiteral(Position initPos, Object object){
        super(initPos);
        this.object = object;
    }
    
    
    
}
