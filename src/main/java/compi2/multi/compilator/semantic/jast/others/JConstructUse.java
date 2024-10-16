
package compi2.multi.compilator.semantic.jast.others;


import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.util.Position;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JConstructUse extends JStatement{
    
    private boolean isFatherConst;
    private List<JExpression> args;

    public JConstructUse(Position initPos, List<JExpression> args, boolean isFatherConst) {
        super(initPos);
        this.args = args;
        this.isFatherConst = isFatherConst;
    }
    
    public JConstructUse(Position initPos, boolean isFatherConst) {
        super(initPos);
        this.args = new LinkedList<>();
        this.isFatherConst = isFatherConst;
    }
    
}
