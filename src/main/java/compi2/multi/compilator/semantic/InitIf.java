
package compi2.multi.compilator.semantic;


import compi2.multi.compilator.semantic.exp.Expression;
import compi2.multi.compilator.util.Position;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class InitIf {
    private Expression expression;
    private Position position;

    public InitIf(Expression expression, Position position) {
        this.expression = expression;
        this.position = position;
    }
    
}
