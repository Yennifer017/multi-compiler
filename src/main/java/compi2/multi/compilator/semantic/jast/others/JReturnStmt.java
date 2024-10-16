
package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.util.Position;

/**
 *
 * @author blue-dragon
 */
public class JReturnStmt extends JStatement{
    
    private JExpression passExp;

    public JReturnStmt(Position initPos, JExpression passExp) {
        super(initPos);
        this.passExp = passExp;
    }

    
}
