
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.semantic.j.JControlStmt;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JSwitchAst extends JControlStmt{
    
    List<JCase> cases;
    JExpression expression;
    
    public JSwitchAst(Position initPos, JExpression expression, List<JCase> cases) {
        super(initPos);
        this.expression = expression;
        this.cases = cases;
    }

    /*@Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    */
    
}
