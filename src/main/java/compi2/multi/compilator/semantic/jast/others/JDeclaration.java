
package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.jclases.components.JType;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JDeclaration extends JStatement{
    
    private String name;
    private JType type;
    private JExpression value;

    public JDeclaration(Position initPos, String name, JType type) {
        super(initPos);
        this.name = name;
        this.type = type;
    }
    
    public JDeclaration(Position initPos, String name, JType type, JExpression value){
        super(initPos);
        this.name = name;
        this.type = type;
        this.value = value;
    }
}
