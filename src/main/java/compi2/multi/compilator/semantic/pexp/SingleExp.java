
package compi2.multi.compilator.semantic.pexp;


import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
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
public class SingleExp extends Expression{
    private String accessId;
    private Object object;
    
    public SingleExp(PrimitiveType type, Position pos){
        super.type = type;
        super.pos = pos;
    }
    
    public SingleExp(PrimitiveType type, Object object, Position pos){
        super.type = type;
        this.object = object;
        super.pos = pos;
    }
    
    public SingleExp(String accessId, Position pos) {
        this.accessId = accessId;
        super.pos = pos;
        
    }

    @Override
    public boolean canRecoveryIntValue() {
        return accessId == null && object != null && !(object instanceof String);
    }
    
    @Override
    public int recoveryIntegerData(){
        if(object instanceof Boolean){
            boolean bool = (boolean) object;
            return bool == true ? 1 : 0;
        } 
        return Integer.parseInt(object.toString());
        
    }

    @Override
    public Label validateSimpleData(SymbolTable symbolTable, List<String> semanticErrors) {
        if(this.type != null){
            return new Label(this.type.getName(), pos);
        } else {
            try {
                if (accessId != null
                        && refAnalyzator.existReference(symbolTable, semanticErrors,
                                new Label(accessId, this.pos))) {
                    RowST row = refAnalyzator.getFromST(symbolTable, accessId);
                    if(row.getCategory() == Category.Constant){
                        return new Label(row.getType(), pos);
                    }
                }
            } catch (NullPointerException e) {
            }
            semanticErrors.add(errorsRep.ilegalUseError(accessId, pos));
            return new Label(Analyzator.ERROR_TYPE, pos);
        }
    }

    @Override
    public Label validateComplexData(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors) {
        if(this.type != null){
            return new Label(this.type.getName(), pos);
        } else if(accessId != null 
                && refAnalyzator.existReference(symbolTable, semanticErrors, 
                        new Label(accessId, this.pos))){
            
            RowST row = refAnalyzator.getFromST(symbolTable, accessId);
            String typeInST = row.getType();
            if(typeInST == null){ //no tiene un tipo especifico
                semanticErrors.add(super.errorsRep.invalidCategoryAccessError(
                        row.getName(), 
                        row.getCategory().getName(), 
                        pos)
                );
                typeInST = Analyzator.ERROR_TYPE;
            }
            return new Label(typeInST, pos);
        } else if(accessId != null){
            semanticErrors.add(errorsRep.undefiniteVarUseError(accessId, pos));
        }
        return new Label(Analyzator.ERROR_TYPE, pos);
    }

}
