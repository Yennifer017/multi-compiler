
package compi2.multi.compilator.semantic.jexp;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.semantic.jast.inv.JContextRef;
import compi2.multi.compilator.semantic.jast.inv.JInvocation;
import compi2.multi.compilator.semantic.util.Label;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JInvocationExp extends Expression{
    private List<JInvocation> invocations;
    
    public JInvocationExp(List<JInvocation> invocations){
        this.invocations = invocations;
    }
    
    public JInvocationExp(List<JInvocation> invocations, JContextRef firstContext){
        try {
            invocations.get(0).setContext(firstContext);
            this.invocations = invocations;
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            this.invocations = new LinkedList<>();
        }
    }

    @Override
    public Label validateSimpleData(SymbolTable symbolTable, List<String> semanticErrors) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Label validateComplexData(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
