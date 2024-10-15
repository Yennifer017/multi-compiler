
package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.semantic.jclases.components.JType;
import compi2.multi.compilator.semantic.jclases.components.Typable;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.FieldST;
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
        String nameForST = getNameFunctionForST(symbolTable);
        if(nameForST != null){
            return new FieldST(name.getName(), type.getName().getName(), access);
        } else {
            semanticErrors.add(
                super.errorsRep.repeatedDeclarationError(name.getName(), name.getPosition())
            );
        }
        return null;
    }

    @Override
    public void defineType(JType type) {
        this.type = type;
    }
    
    protected String getNameFunctionForST(SymbolTable symbolTable){
        if(symbolTable.containsKey(name.getName())){            
            RowST rowST = symbolTable.get(name.getName());
            if(rowST instanceof FieldST){
                return null;
            } else {
                return verificateOthersConstructs(symbolTable);
            }
        } else {
            return this.name.getName();
        }
    }
    
    private String verificateOthersConstructs(SymbolTable symbolTable){
        int index = 1;
        while (symbolTable.containsKey(
                refFun.getSTName(this.name.getName(), index))) {
            RowST anotherRowST = symbolTable.get(refFun.getSTName(this.name.getName(), index));
            if (anotherRowST instanceof FieldST) {
                return null;
            }
            index++;
        }
        return refFun.getSTName(this.name.getName(), index);
    }
    
}
