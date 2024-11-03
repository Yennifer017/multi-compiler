
package compi2.multi.compilator.analysis.symbolt.clases;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class ClassST extends RowST{
    
    private SymbolTable methodsST;
    private SymbolTable fieldsST;
    
    private List<String> fieldsOrdered;
    private NodeJerarTree jerar;

    public ClassST(String name, SymbolTable methodsST, SymbolTable fieldsST, 
            NodeJerarTree jerar, List<String> fieldsOrdered) {
        super(name, Category.JClass, null);
        this.methodsST = methodsST;
        this.fieldsST = fieldsST;
        this.jerar = jerar;
        this.fieldsOrdered = fieldsOrdered;
    }
    
    @Override
    public boolean isLinked() {
        return true;
    }
    
}
