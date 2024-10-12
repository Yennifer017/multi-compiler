
package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.semantic.jclases.components.JType;
import compi2.multi.compilator.semantic.jclases.components.Typable;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.Expression;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JField extends JDef implements Typable{
    
    private Expression expAssign;
    private JType type;
    
    public JField(){
        super(null);
    }

    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void defineType(JType type) {
        this.type = type;
    }
    
}
