
package compi2.multi.compilator.semantic.jexp;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.Position;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JCreateClassE extends JExpression{
    private List<JExpression> params;
    private String name;
    
    public JCreateClassE(Position pos, String name, List<JExpression> params){
        super(pos);
        this.name = name;
        this.params = params;
    }
    
    public JCreateClassE(Position pos, String name){
        super(pos);
        this.name = name;
        this.params = new LinkedList<>();
    }
    
}
