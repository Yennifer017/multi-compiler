
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.semantic.Statement;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JCase {
    private Expression passExp;
    private List<Statement> stmts;

    public JCase(Expression passExp, List<Statement> stmts) {
        this.passExp = passExp;
        this.stmts = stmts;
    }

    public JCase(List<Statement> stmts) {
        this.stmts = stmts;
    }
    
    public boolean isDefault(){
        return this.passExp == null;
    }
}
