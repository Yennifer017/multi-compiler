
package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.semantic.jclases.components.JType;
import compi2.multi.compilator.semantic.jclases.components.Typable;
import compi2.multi.compilator.semantic.jclases.components.JArg;
import compi2.multi.compilator.analysis.symbolt.AccessMod;
import compi2.multi.compilator.analysis.symbolt.InfParam;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.MethodST;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.cuartetas.FunctionC3D;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.jclases.components.JReferType;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JMethod extends JFunction implements Typable{
    private JType type;
    private MethodST methodSt;

    public JMethod(Label name, AccessMod access, List<JArg> args, List<JStatement> internalStmts) {
        super(access, args, internalStmts);
        super.name = name;
    }

    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors) {
        List<InfParam> params = super.generateArgsList();
            String nameForST = getNameFunctionForST(symbolTable, params);
        if(nameForST != null){
            if(type.getRefType() == JReferType.Void){
                this.methodSt = new MethodST(
                        nameForST,
                        super.generateInternalST(false), 
                        params, 
                        access,
                        type.getArrayDimensions()
                );
            } else {
                this.methodSt = new MethodST(
                        nameForST, 
                        type.getName().getName(), 
                        super.generateInternalST(true), 
                        params, 
                        access, 
                        type.getArrayDimensions()
                );
            }
            String finalName = super.getFinalName(methodSt.getName());
            methodSt.setCompleateName(finalName);
            return this.methodSt;
        } else {
            semanticErrors.add(
                super.errorsRep.redeclareFunctionError(name.getName(), params, name.getPosition())
            );
        }
        return null;
    }

    @Override
    public void defineType(JType type) {
        this.type = type;
    }
    
    /**
     *
     * @param symbolTable
     * @param argsStringList
     * @return
     */
    @Override
    protected String getNameFunctionForST(SymbolTable symbolTable, List<InfParam> argsStringList){
        if(symbolTable.containsKey(name.getName())){            
            RowST rowST = symbolTable.get(name.getName());
            if(rowST instanceof MethodST){
                MethodST functionST = (MethodST) rowST;
                if(refFun.hasTheSameArgs(functionST.getParams(), argsStringList)){
                    return null;
                } else {
                    return validateOtherMethods(symbolTable, argsStringList);
                }
            } else {
                return validateOtherMethods(symbolTable, argsStringList);
            }
        } else {
            return this.name.getName();
        }
    }
    
    private String validateOtherMethods(SymbolTable symbolTable, List<InfParam> argsStringList){
        int index = 1;
        while (symbolTable.containsKey(
                refFun.getSTName(this.name.getName(), index))) {
            RowST anotherRowST = symbolTable.get(refFun.getSTName(this.name.getName(), index));
            if (anotherRowST instanceof MethodST) {
                MethodST f1 = (MethodST) anotherRowST;
                if (refFun.hasTheSameArgs(f1.getParams(), argsStringList)) {
                    return null;
                }
            }
            index++;
        }
        return refFun.getSTName(this.name.getName(), index);
    }

    @Override
    public void validateInternal(JSymbolTable globalST, TypeTable typeTable, List<String> semanticErrors) {
        this.type.validateSemantic(globalST, typeTable, semanticErrors, true);
        super.validateArgs(globalST, methodSt.getInternalST(), typeTable, semanticErrors);
        SemanticRestrictions restrictions = new SemanticRestrictions(
                false, 
                false, 
                this.type.getCompleateName()
        );
        super.validateInternalStmts(
                globalST, 
                methodSt.getInternalST(), 
                typeTable, 
                semanticErrors, 
                restrictions
        );
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, SymbolTable fields) {
        List<Cuarteta> internalCuartetas = new LinkedList<>();
        Memory temporals = new Memory("internal");
        super.generateInternalCuartetas(admiMemory, internalCuartetas, temporals);
        admiMemory.getCuartetas().add(
                new FunctionC3D(
                    methodSt.getCompleateName(), 
                    temporals, 
                    internalCuartetas)
        );
        admiMemory.getDefinitions().add(methodSt.getCompleateName());
    }
}
