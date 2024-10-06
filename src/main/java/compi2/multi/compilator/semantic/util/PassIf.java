
package compi2.multi.compilator.semantic.util;


import compi2.multi.compilator.semantic.past.ElseAst;
import compi2.multi.compilator.semantic.past.IfAst;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class PassIf {
    private ElseAst elseAst;
    private List<IfAst> ifs;

    public PassIf(ElseAst elseAst){
        this.elseAst = elseAst;
    }
    
    public PassIf(List<IfAst> ifs, ElseAst elseAst){
        this.ifs = ifs;
        this.elseAst = elseAst;
    }
    
    public PassIf(IfAst ifAst){
        this.ifs = new LinkedList<>();
        ifs.add(ifAst);
    }
    
    public PassIf(){}
    
    
}
