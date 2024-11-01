
package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class FromCArrUse extends JVarUse{
    
    private List<CExp> arrayAccess;
    
    public FromCArrUse(Label inv, List<CExp> arrayAccess) {
        //super(position, name, JContextRef.Local);
        super(inv, JContextRef.Local);
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
