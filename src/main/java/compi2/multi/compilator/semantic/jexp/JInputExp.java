
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
public class JInputExp extends JExpression{
    private JTypeInput type;
    
    public JInputExp(Position pos, JTypeInput type){
        super(pos);
        this.type = type;
    }

    
}
