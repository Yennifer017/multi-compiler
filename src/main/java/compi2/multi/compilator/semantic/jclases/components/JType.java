
package compi2.multi.compilator.semantic.jclases.components;

import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import static compi2.multi.compilator.semantic.jclases.components.JReferType.ObjectReference;
import static compi2.multi.compilator.semantic.jclases.components.JReferType.PrimitiveData;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.ErrorsRep;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JType {
    protected JReferType refType;
    protected Label name;
    
    private ErrorsRep errorsRep;

    public JType(JReferType refType, Label name) {
        this.refType = refType;
        this.name = name;
        errorsRep = new ErrorsRep();
    }

    public JType(Position pos) {
        this.refType = JReferType.Void;
        this.name = new Label("", pos);
    }
    
    /**
    * valida que el tipo exista
     * @param classesST
     * @param typeTable
     * @param semanticErrors
     * @param allowVoid
     * @return true si existe, false de lo contrario
    */
    public boolean validateSemantic(JSymbolTable classesST,  
            TypeTable typeTable, List<String> semanticErrors, boolean allowVoid){
        switch (getRefType()) {
            case ObjectReference -> {
                if(!classesST.containsKey(this.getName().getName())){
                    semanticErrors.add(errorsRep.undefiniteClassError(
                            this.name.getName(), 
                            this.name.getPosition())
                    );
                    return false;
                }
                return true;
            }
            case PrimitiveData -> {
                if(!typeTable.containsKey(this.name.getName())){
                    semanticErrors.add(errorsRep.undefiniteTypeError(
                            this.name.getName(), 
                            this.name.getPosition())
                    );
                    return false;
                } 
                return true;
            } 
            default -> {
                if(!allowVoid){
                    semanticErrors.add(errorsRep.inesperateVoid(this.name.getPosition()));
                    return false;
                }
                return true;
            }
        }
    }
    
    public int getArrayDimensions(){
        return 0;
    }
    
    public String getCompleateName(){
        return this.name + this.getArrayName(this.getArrayDimensions());
    }
    
    private String getArrayName(int times){
        String arrayRep = "";
        for (int i = 0; i < times; i++) {
            arrayRep += "[]";
        }
        return arrayRep;
    }
    
}
