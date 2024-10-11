
package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import compi2.multi.compilator.semantic.util.JPassExp;
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
public class JAssignAst extends Statement{
    
    private List<JInvocation> variable;
    private JPassExp value;
    
    public JAssignAst(Position initPos, List<JInvocation> variable, JPassExp value) {
        super(initPos);
        this.variable = variable;
        this.value = value;
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors, SemanticRestrictions restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
