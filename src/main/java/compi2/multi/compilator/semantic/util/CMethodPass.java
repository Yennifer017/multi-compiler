
package compi2.multi.compilator.semantic.util;

import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.cast.inv.CMethodUseAst;
import compi2.multi.compilator.semantic.cexp.CMethodUseExp;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CMethodPass {
    private Label name;
    private List<CExp> args;

    public CMethodPass(Label name, List<CExp> args) {
        this.name = name;
        this.args = args;
    }

    public CMethodPass(Label name) {
        this.name = name;
        this.args = new LinkedList<>();
    }
    
    public CMethodUseAst toStmt(){
        return new CMethodUseAst(name.getPosition(), name.getName(), args);
    }
    
    public CMethodUseExp toExp(){
        return new CMethodUseExp(name.getPosition(), name.getName(), args);
    }
    
}
