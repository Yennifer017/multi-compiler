
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.Expression;
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
public class JSwitchAst extends JControlStmt{
    
    List<JCase> cases;
    Expression expression;
    
    public JSwitchAst(Position initPos, Expression expression, List<JCase> cases) {
        super(initPos);
        this.expression = expression;
        this.cases = cases;
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
