
package compi2.multi.compilator.semantic.cast;

import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CStatement;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class CCase {
    private CExp passExp;
    private List<CStatement> stmts;

    public CCase(CExp passExp, List<CStatement> stmts) {
        this.passExp = passExp;
        this.stmts = stmts;
    }

    public CCase(List<CStatement> stmts) {
        this.stmts = stmts;
    }
    
    public boolean isDefault(){
        return this.passExp == null;
    }
}
