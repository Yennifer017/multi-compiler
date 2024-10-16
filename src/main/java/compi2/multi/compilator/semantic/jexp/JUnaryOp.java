
package compi2.multi.compilator.semantic.jexp;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JUnaryOp extends JExpression{
    
    private JExpression passExp;
    
    private DefiniteOperation operation;

    public JUnaryOp(Position pos, JExpression passExp, DefiniteOperation operation) {
        super(pos);
        this.passExp = passExp;
        this.operation = operation;
    }
    
    
}
