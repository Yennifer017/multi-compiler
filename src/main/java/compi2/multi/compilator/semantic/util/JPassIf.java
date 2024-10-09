
package compi2.multi.compilator.semantic.util;

import compi2.multi.compilator.semantic.jast.JElseAst;
import compi2.multi.compilator.semantic.jast.JIfAst;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JPassIf {
    private JElseAst elseAst;
    private List<JIfAst> ifs;

    public JPassIf(JElseAst elseAst){
        this.elseAst = elseAst;
    }
    
    public JPassIf(List<JIfAst> ifs, JElseAst elseAst){
        this.ifs = ifs;
        this.elseAst = elseAst;
    }
    
    public JPassIf (JIfAst ifAst){
        this.ifs = new LinkedList<>();
        ifs.add(ifAst);
    }
    
    public JPassIf(){}
}
