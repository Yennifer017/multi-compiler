
package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.InfParam;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.ClassST;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.MethodST;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.generators.MethodGenC3D;
import compi2.multi.compilator.c3d.util.RetJInvC3D;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.util.Label;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JMethodUse extends JInvocation{
    
    private List<JExpression> args;
    private boolean tryConvert;
    
    private MethodST methodST;
    private SymbolTable lastST;
    
    private MethodGenC3D methodGenC3D;
    
    public JMethodUse(Label inv, JContextRef context) {
        super(inv, context);
        this.args = new LinkedList<>();
        this.methodGenC3D = new MethodGenC3D();
    }
    
    
    public JMethodUse(Label inv, JContextRef context, List<JExpression> args) {
        super(inv, context);
        this.args = args;
        this.methodGenC3D = new MethodGenC3D();
    }

    @Override
    public Label validate(JSymbolTable globalST, SymbolTable symbolTable, TypeTable typeTable, 
            NodeJerarTree jerar, List<String> semanticErrors) {
        lastST = symbolTable;
        List<InfParam> argsStringList = null;
        switch (context) {
            case JContextRef.Local, JContextRef.FromObject:
                SymbolTable currentST = jerar.getClassST().getMethodsST();
                methodST = findMethod(currentST, argsStringList, semanticErrors, false);
                if(methodST != null){
                    return new Label(methodST.getType(), this.inv.getPosition());
                }
                if(tryConvert){
                    methodST = findMethod(currentST, argsStringList, semanticErrors, true);
                    if(methodST != null){
                        return new Label(methodST.getType(), this.inv.getPosition());
                    }
                }
            default: //from father
                NodeJerarTree currentNode = jerar.getFather();
                while (currentNode != null) {
                    currentST = currentNode.getClassST().getMethodsST();
                    methodST = findMethod(currentST, argsStringList, semanticErrors, false);
                    if(methodST != null){
                        return new Label(methodST.getType(), this.inv.getPosition());
                    }
                    if(tryConvert){
                        methodST = findMethod(currentST, argsStringList, semanticErrors, true);
                        if(methodST != null){
                            return new Label(methodST.getType(), this.inv.getPosition());
                        }
                    }
                    currentNode = currentNode.getFather();
                }
                semanticErrors.add(
                        super.errorsRep.undefiniteFunctionError(this.inv.getName(), this.inv.getPosition())
                );
                return new Label(Analyzator.ERROR_TYPE, this.inv.getPosition());
        }
    }
    
    public MethodST findMethod(SymbolTable symbolTable, List<InfParam> argsStringList, 
            List<String> semanticErrors, boolean convert){
        if(symbolTable.containsKey(this.inv.getName())){
            RowST rowST = symbolTable.get(this.inv.getName());
            if(rowST instanceof MethodST){
                MethodST functionST = (MethodST) rowST;
                if(convert){
                    if(refFun.hasTheSameConvertArgs(functionST.getParams(), argsStringList)){
                        return functionST;
                    } else {
                        return validateOtherMethods(symbolTable, argsStringList, convert);
                    }
                } else {
                    if(refFun.hasTheSameArgs(functionST.getParams(), argsStringList)){
                        return functionST;
                    } else {
                        return validateOtherMethods(symbolTable, argsStringList, convert);
                    }
                }
            } else {
                return validateOtherMethods(symbolTable, argsStringList, convert);
            }
        } else {
            return null;
        }
    }
    
    private MethodST validateOtherMethods(SymbolTable symbolTable, List<InfParam> argsStringList, boolean convert){
        int index = 1;
        while (symbolTable.containsKey(
                refFun.getSTName(this.inv.getName(), index))) {
            RowST anotherRowST = symbolTable.get(refFun.getSTName(this.inv.getName(), index));
            if (anotherRowST instanceof MethodST) {
                MethodST f1 = (MethodST) anotherRowST;
                if(convert){
                    if (refFun.hasTheSameConvertArgs(f1.getParams(), argsStringList)) {
                        return f1;
                    }
                } else {
                    if (refFun.hasTheSameArgs(f1.getParams(), argsStringList)) {
                        return f1;
                    }
                }
            }
            index++;
        }
        return null;
    }
    
    @Override
    public Label validate(JSymbolTable globalST, SymbolTable symbolTable, TypeTable typeTable, 
            NodeJerarTree jerar, List<String> semanticErrors, Label previus) {
        if (globalST.containsKey(previus.getName())) {
            ClassST classST = globalST.get(previus.getName());
            SymbolTable currentST = classST.getMethodsST();
            
            if (currentST.containsKey(this.inv.getName())) {
                methodST = (MethodST) currentST.get(this.inv.getName());
                return new Label(methodST.getType(), this.inv.getPosition());
            } else {
                semanticErrors.add(errorsRep.invalidInvocationError(
                        previus.getName(), this.inv.getName(), this.inv.getPosition())
                );
            }
        } else {
            semanticErrors.add(errorsRep.invalidInvocationError(
                    previus.getName(), this.inv.getName(), this.inv.getPosition())
            );
        }
        return new Label(Analyzator.ERROR_TYPE, this.inv.getPosition());
    }

    @Override
    public RetJInvC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, int instanceStackRef) {
        methodGenC3D.settingParams(args, admiMemory, internalCuartetas, temporals, lastST, methodST);
        methodGenC3D.invocateMethod(internalCuartetas, lastST, methodST.getCompleateName());
        //devolver el valor de retorno si existe
        if(methodST.getType().equals(Analyzator.VOID_METHOD)){
            return null;
        } else {
            throw new RuntimeException("No se pueden devolver cosas de un metodo aun");
        }
    }

    @Override
    public RetJInvC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, RetJInvC3D previus) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public boolean isStatement() {
        return true;
    }

    @Override
    public boolean hasReturnType() {
        return !methodST.getType().equals(Analyzator.VOID_METHOD);
    }

}
