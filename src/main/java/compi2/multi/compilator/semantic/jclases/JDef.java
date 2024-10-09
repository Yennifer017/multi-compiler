
package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.analysis.symbolt.AccessMod;
import compi2.multi.compilator.semantic.DefAst;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public abstract class JDef extends DefAst{
    protected AccessMod access;
    
    public JDef(AccessMod access){
        this.access = access;
    }
}
