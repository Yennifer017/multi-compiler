
package compi2.multi.compilator.semantic.cast.assign;

import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.util.Position;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CVarAssign extends CAssign{
    private String nameVar;
    public CVarAssign(Position initPos, String nameVar, CExp expression) {
        super(initPos, expression);
        this.nameVar = nameVar;
    }
    
}
