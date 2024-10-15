
package compi2.multi.compilator.semantic.util;

import compi2.multi.compilator.semantic.cast.CElseAst;
import compi2.multi.compilator.semantic.cast.CIfAst;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class CPassIf {
    private CElseAst elseAst;
    private List<CIfAst> ifs;

    public CPassIf(CElseAst elseAst){
        this.elseAst = elseAst;
    }
    
    public CPassIf(List<CIfAst> ifs, CElseAst elseAst){
        this.ifs = ifs;
        this.elseAst = elseAst;
    }
    
    public CPassIf (CIfAst ifAst){
        this.ifs = new LinkedList<>();
        ifs.add(ifAst);
    }
    
    public CPassIf(){}
}
