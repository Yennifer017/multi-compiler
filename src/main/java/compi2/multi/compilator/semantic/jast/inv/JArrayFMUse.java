
package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
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
public class JArrayFMUse extends JMethodUse{
    private List<JExpression> arrayAccess;
    
    public JArrayFMUse(Label inv, 
            JContextRef context, List<JExpression> arrayAccess) {
        super(inv, context, arrayAccess);
        this.arrayAccess = arrayAccess;
    }
    
    public JArrayFMUse(Label inv, JContextRef context, 
            List<JExpression> args, List<JExpression> arrayAccess) {
        super(inv, context, args);
        this.arrayAccess = arrayAccess;
    }
    
    @Override
    public boolean isStatement() {
        return false;
    }
    
    public Label validate(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors){
        throw new UnsupportedOperationException("No se permiten arreglos");
    };
    
    public Label validate(JSymbolTable globalST, SymbolTable symbolTable,
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, Label previus){
        throw new UnsupportedOperationException("No se permiten arreglos");
    };
    
    
}
