
package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.semantic.jast.inv.JContextRef;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
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
public class JMethodUseStmt extends Statement {
    
    private List<JInvocation> invocations;
    public JMethodUseStmt(Position initPos, List<JInvocation> invocations) {
        super(initPos);
        this.invocations = invocations;
    }
    
    public JMethodUseStmt(Position initPos, List<JInvocation> invocations, JContextRef firstContextRef){
        super(initPos);
        try {
            invocations.get(0).setContext(firstContextRef);
            this.invocations = invocations;
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            this.invocations = new LinkedList<>();
        }
    }

    @Override
    public ReturnCase validate(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors, SemanticRestrictions restrictions) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
