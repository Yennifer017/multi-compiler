
package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.semantic.jclases.components.JArg;
import compi2.multi.compilator.analysis.symbolt.AccessMod;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.ConstructorST;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class JConstructor extends JFunction{
    
    private ConstructorST constructorST;
    
    public JConstructor(Label name, AccessMod access, List<JArg> args, List<JStatement> internalStmts) {
        super(access, args, internalStmts);
        super.name = name;
    }
    
    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors) {
        if(super.nameClass.getName().equals(this.name.getName())){
            List<String> params = super.generateArgsList();
            String nameForST = getNameFunctionForST(symbolTable, params);
            if(nameForST != null){
                this.constructorST = new ConstructorST(
                        nameForST, super.generateInternalST(true), params, access
                );
                return this.constructorST;
            } else {
                semanticErrors.add(
                        super.errorsRep.redeclareFunctionError(name.getName(), params, name.getPosition())
                );
            }
        } else {
            semanticErrors.add(super.errorsRep.noConstructorError(
                    super.nameClass.getName(), name.getName(), name.getPosition())
            );
        }
        return null;
    }
    
    @Override
    public void validateInternal(JSymbolTable globalST, TypeTable typeTable, List<String> semanticErrors) {
        if(globalST.getInitJerarTree().getFather().getFather() != null){
            //validar el uso de constructores
        }
        super.validateArgs(globalST, constructorST.getInternalST(), typeTable, semanticErrors);
        super.validateInternalStmts(globalST, constructorST.getInternalST(), typeTable, semanticErrors);
    }
    
    @Override
    protected String getNameFunctionForST(SymbolTable symbolTable, List<String> argsStringList){
        if(symbolTable.containsKey(name.getName())){            
            RowST rowST = symbolTable.get(name.getName());
            if(rowST instanceof ConstructorST){
                ConstructorST functionST = (ConstructorST) rowST;
                if(refFun.hasTheSameArgs(functionST.getTypesParams(), argsStringList)){
                    return null;
                } else {
                    return verificateOthersConstructs(symbolTable, argsStringList);
                }
            } else {
                return verificateOthersConstructs(symbolTable, argsStringList);
            }
        } else {
            return this.name.getName();
        }
    }
    
    private String verificateOthersConstructs(SymbolTable symbolTable, List<String> argsStringList){
        int index = 1;
        while (symbolTable.containsKey(
                refFun.getSTName(this.name.getName(), index))) {
            RowST anotherRowST = symbolTable.get(refFun.getSTName(this.name.getName(), index));
            if (anotherRowST instanceof ConstructorST) {
                ConstructorST f1 = (ConstructorST) anotherRowST;
                if (refFun.hasTheSameArgs(f1.getTypesParams(), argsStringList)) {
                    return null;
                }
            }
            index++;
        }
        return refFun.getSTName(this.name.getName(), index);
    }
    
}
