
package compi2.multi.compilator.analyzator;

import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analysis.typet.convert.TConvertidor;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.ErrorsRep;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class RefAnalyzator {
    protected ErrorsRep errorsRep;
    protected TConvertidor tConvert;
    
    public RefAnalyzator(){
        errorsRep = new ErrorsRep();
        tConvert = new TConvertidor();
    }
    
    public boolean existReference(SymbolTable symbolTable, 
            List<String> semanticErrors, Label variable){
        SymbolTable currentTab = symbolTable;
        while (currentTab != null) {            
            if (currentTab.containsKey(variable.getName())) {
                return true;
            } else {
                currentTab = currentTab.getFather();
            }
        }
        semanticErrors.add(errorsRep.undefiniteVarUseError(
                variable.getName(),
                variable.getPosition())
        );
        return false;
    }
    
    /**
     * Retorna si en la tabla de tipos existe una referencia de tipos
     * @param typeTable
     * @param semanticErrors
     * @param type
     * @return verdadero si existe, falso de lo contrario
     */
    public boolean existReference(TypeTable typeTable, List<String> semanticErrors, Label type){
        TypeTable currentTypeTab = typeTable;
        while (currentTypeTab != null) {            
            if (currentTypeTab.containsKey(type.getName())) {
                return true;
            } else {
                currentTypeTab = currentTypeTab.getFather();
            }
        }
        semanticErrors.add(errorsRep.undefiniteTypeError(
                type.getName(),
                type.getPosition())
        );
        return false;
    }
    
    public RowST getFromST(SymbolTable symbolTable, String var){
        SymbolTable currentTab = symbolTable;
        while (currentTab != null) {            
            if (currentTab.containsKey(var)) {
                return currentTab.get(var);
            } else {
                currentTab = currentTab.getFather();
            }
        }
        return null;
    }
    
    public boolean canInsert(Label name, TypeTable typeTable, List<String> semanticErrors){
        if(typeTable.containsKey(name.getName())){
            semanticErrors.add(errorsRep.repeatedTypeError(
                    name.getName(), 
                    name.getPosition())
            );
            return false;
        } else {
            return true;
        }
    }
    
    public boolean canInsert(Label name, SymbolTable symbolTable, List<String> semanticErrors){
        if(symbolTable.containsKey(name.getName())){
            semanticErrors.add(errorsRep.repeatedDeclarationError(
                    name.getName(), 
                    name.getPosition())
            );
            return false;
        } else {
            return true;
        }
    }
    
}
