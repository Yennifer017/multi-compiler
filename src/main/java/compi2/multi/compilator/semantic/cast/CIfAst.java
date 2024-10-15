
package compi2.multi.compilator.semantic.cast;

import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CIfAst extends CControlStmt{
    private CExp condition;
    private List<CIfAst> elifs;
    private CElseAst elseStmt;
    
    public CIfAst(Position initPos, CExp condition, List<CStatement> internalStmt) {
        super(initPos, internalStmt);
        this.condition = condition;
    }
    
    public CIfAst(Position initPos, CExp condition, List<CStatement> statements, 
            List<CIfAst> elifs, CElseAst elseStmt) {
        super(initPos, statements);
        this.condition = condition;
        this.elifs = elifs;
        this.elseStmt = elseStmt;
    }

    
}
