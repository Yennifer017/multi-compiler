
package compi2.multi.compilator.analyzator;


import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.InfParam;
import compi2.multi.compilator.analysis.symbolt.estruc.FunctionST;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analysis.typet.convert.TConvertidor;
import compi2.multi.compilator.semantic.Expression;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.ErrorsRep;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class FunctionRefAnalyzator {
    
    private TConvertidor tConvert;
    private ErrorsRep errorsRep;
    
    public FunctionRefAnalyzator(){
        tConvert = new TConvertidor();
        errorsRep = new ErrorsRep();
    }
    
    public List<InfParam> validateArgs(List<Expression> args, SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors){
        List<InfParam> list =  new ArrayList<>();
        if(args !=  null && !args.isEmpty()){
            for (Expression arg : args) {
                Label paramLabel = arg.validateComplexData(symbolTable, typeTable, semanticErrors);
                //list.add(paramLabel.getName());
                list.add(new InfParam(paramLabel.getName()));
            }
        }
        return list;
    }
    
    public FunctionST existReference(Label name, SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, List<InfParam> typeArgs){
        SymbolTable currentTab = symbolTable;
        while (currentTab != null) {            
            if (currentTab.containsKey(name.getName())) {
                RowST rowST = currentTab.get(name.getName());
                if (rowST instanceof FunctionST) {
                    FunctionST functionST = (FunctionST) rowST;
                    //exactly match
                    if (this.hasTheSameArgs(functionST.getParams(), typeArgs)) {
                        return functionST;
                    } else {
                        int index = 1;
                        while (currentTab.containsKey(
                                this.getSTName(name.getName(), index))) {
                            FunctionST f1 = (FunctionST) currentTab.get(this.getSTName(name.getName(), index));
                            if (this.hasTheSameArgs(f1.getParams(), typeArgs)) {
                                return f1;
                            }
                            index++;
                        }
                    }
                    //converted match
                    if (this.hasTheSameConvertArgs(functionST.getParams(), typeArgs)) {
                        return functionST;
                    } else {
                        int index = 1;
                        while (currentTab.containsKey(
                                this.getSTName(name.getName(), index))) {
                            FunctionST f1 = (FunctionST) currentTab.get(this.getSTName(name.getName(), index));
                            if (this.hasTheSameConvertArgs(f1.getParams(), typeArgs)) {
                                return f1;
                            }
                            index++;
                        }
                        //agregar error de no tiene parametros requeridos
                        semanticErrors.add(errorsRep.noSuitableFunctionError(
                                name.getName(), 
                                typeArgs,
                                name.getPosition())
                        );
                        return null;
                    }
                } else {
                    //agregar error de que no se esta accediendo a una funcion
                    semanticErrors.add(errorsRep.invalidCategoryAccessError(
                            name.getName(), 
                            rowST.getCategory().getName(), 
                            Category.Function.getName(), 
                            name.getPosition())
                    );
                    return null;
                }
            } else {
                currentTab = currentTab.getFather();
            }
        }
        semanticErrors.add(errorsRep.undefiniteFunctionError(name.getName(), name.getPosition()));
        return null;
    }
    
    public String getTypeReturnFun(Label name, SymbolTable symbolTable, TypeTable typeTable, 
            List<InfParam> typeArgs){
        SymbolTable currentTab = symbolTable;
        while (currentTab != null) {            
            if (currentTab.containsKey(name.getName())) {
                RowST rowST = currentTab.get(name.getName());
                if (rowST instanceof FunctionST) {
                    FunctionST functionST = (FunctionST) rowST;
                    
                    //exactly match
                    if (this.hasTheSameArgs(functionST.getParams(), typeArgs)) {
                        return functionST.getType();
                    } else {
                        int index = 1;
                        while (currentTab.containsKey(
                                this.getSTName(name.getName(), index))) {
                            FunctionST f1 = (FunctionST) currentTab.get(this.getSTName(name.getName(), index));
                            if (this.hasTheSameArgs(f1.getParams(), typeArgs)) {
                                return f1.getType();
                            }
                            index++;
                        }
                    }
                    //converted match
                    if (this.hasTheSameConvertArgs(functionST.getParams(), typeArgs)) {
                        return functionST.getType();
                    } else {
                        int index = 1;
                        while (currentTab.containsKey(
                                this.getSTName(name.getName(), index))) {
                            FunctionST f1 = (FunctionST) currentTab.get(this.getSTName(name.getName(), index));
                            if (this.hasTheSameConvertArgs(f1.getParams(), typeArgs)) {
                                return f1.getType();
                            }
                            index++;
                        }
                    }
                    
                } else {
                    return null;
                }
            } else {
                currentTab = currentTab.getFather();
            }
        }
        return null;
    }
    
    
    public boolean hasTheSameArgs(List<InfParam> oficialList, List<InfParam> newList){
        if(oficialList.size() != newList.size()){
            return false;
        } else {
            for (int i = 0; i < oficialList.size(); i++) {
                if(!oficialList.get(i).getType().equals(newList.get(i).getType())){
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean hasTheSameConvertArgs(List<InfParam> oficialList, List<InfParam> newList){
        if(oficialList.size() != newList.size()){
            return false;
        } else {
            for (int i = 0; i < oficialList.size(); i++) {
                if(!oficialList.get(i).equals(newList.get(i))
                        && !tConvert.canUpgradeType(oficialList.get(i).getType(), newList.get(i).getType())
                        ){
                    return false;
                }
            }
        }
        return true;
    }
    
    public String getSTName(String name, int index){
        return name + Analyzator.FUNCTION_SEPARATOR + index;
    }
}
