
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JCase {
    private JExpression passExp;
    private List<JStatement> stmts;

    public JCase(JExpression passExp, List<JStatement> stmts) {
        this.passExp = passExp;
        this.stmts = stmts;
    }

    public JCase(List<JStatement> stmts) {
        this.stmts = stmts;
    }
    
    public boolean isDefault(){
        return this.passExp == null;
    }
}
