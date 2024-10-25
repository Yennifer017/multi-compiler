
package compi2.multi.compilator.semantic.cexp;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
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
public class CLiteral extends CExp{
    private Object object;
    private PrimitiveType type;
    public CLiteral(Position pos, Object object, PrimitiveType type) {
        super(pos);
        this.object = object;
        this.type = type;
    }
    
    @Override
    public Label validateSimpleData(SymbolTable symbolTable, List<String> semanticErrors) {
        return new Label(this.type.getName(), pos);
    }

    @Override
    public Label validateComplexData(CImports imports, JSymbolTable clasesST, SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, List<String> semanticErrors) {
        return new Label(this.type.getName(), pos);
    }
    
}
