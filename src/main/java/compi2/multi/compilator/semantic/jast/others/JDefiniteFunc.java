
package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JDefiniteFunc extends JStatement{
    
    private boolean withLn;
    private List<JExpression> listToPrint;

    public JDefiniteFunc(Position initPos, List<JExpression> listToPrint, boolean withLn) {
        super(initPos);
        this.listToPrint = listToPrint;
        this.withLn = withLn;
    }

}
