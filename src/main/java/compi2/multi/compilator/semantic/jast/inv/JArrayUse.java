
package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JArrayUse extends JVarUse{
    private List<JExpression> arrayAccess;
    
    public JArrayUse(Label inv, JContextRef context, List<JExpression> arrayAccess) {
        super(inv, context);
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
