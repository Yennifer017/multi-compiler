
package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.semantic.jclases.components.JArg;
import compi2.multi.compilator.analysis.symbolt.AccessMod;
import compi2.multi.compilator.semantic.DefAst;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public abstract class JFunction extends JDef{
    protected Label nameClass;
    protected List<JArg> args;
    protected List<Statement> internalStmts;

    public JFunction(AccessMod access, List<JArg> args, List<Statement> internalStmts) {
        super(access);
        this.args = args;
        this.internalStmts = internalStmts;
    }
    
}
