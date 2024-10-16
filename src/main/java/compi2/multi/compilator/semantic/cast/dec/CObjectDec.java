
package compi2.multi.compilator.semantic.cast.dec;

import compi2.multi.compilator.semantic.c.CDef;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.util.Label;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CObjectDec extends CDef {
    
    private Label objectName;
    private List<CExp> args;
    
    public CObjectDec(Label name){
        super.name = name;
        args = new LinkedList<>();
    }
    
    public CObjectDec(Label name, List<CExp> args){
        super.name = name;
        this.args = args;
    }
    
}
