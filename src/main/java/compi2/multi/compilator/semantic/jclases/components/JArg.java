
package compi2.multi.compilator.semantic.jclases.components;

import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.ErrorsRep;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JArg {
    private JType type;
    private Label name;
    
    private ErrorsRep errorsRep;

    public JArg(JType type, Label name) {
        this.type = type;
        this.name = name;
        this.errorsRep = new ErrorsRep();
    }
    
    public void validateSemantic(JSymbolTable classesST, SymbolTable symbolTable, 
            TypeTable typeTable, List<String> semanticErrors){
        
        if(this.type.validateSemantic(classesST, typeTable, semanticErrors, false)
                && !symbolTable.containsKey(this.name.getName())){
            symbolTable.put(this.name.getName(),
                    new SingleData(
                            this.name.getName(),
                            this.type.getRefType() == JReferType.ObjectReference 
                                    ? Category.Param_ref : Category.Param_val, 
                            this.type.getName().getName(),
                            symbolTable.getLastDir())
            );
            symbolTable.incrementLastDir(1);
        } else {
            semanticErrors.add(errorsRep.repeatedDeclarationError(
                this.name.getName(), this.name.getPosition())
            );
        }
    }
    
}
