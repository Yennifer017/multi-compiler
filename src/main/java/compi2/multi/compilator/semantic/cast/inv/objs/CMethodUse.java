
package compi2.multi.compilator.semantic.cast.inv.objs;

import compi2.multi.compilator.analysis.symbolt.InfParam;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.ClassST;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.MethodST;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.generators.MethodGenC3D;
import compi2.multi.compilator.c3d.util.RetJInvC3D;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.util.Label;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class CMethodUse extends CInvocation{
    
    private List<CExp> args;
    
    private MethodST methodST;
    private SymbolTable lastST;
    private PrimitiveType type;
    private boolean isObjectReturn;
    
    private MethodGenC3D methodGenC3D;
    
    
    public CMethodUse(Label inv) {
        super(inv);
        this.args = new LinkedList<>();
        this.methodGenC3D =  new MethodGenC3D();
    }

    public CMethodUse(Label inv, List<CExp> args) {
        super(inv);
        this.args = args;
        this.methodGenC3D =  new MethodGenC3D();
    }

    @Override
    public Label validate(CImports imports, JSymbolTable clasesST, SymbolTable symbolTable, 
            SymbolTable pascalST, TypeTable typeTable, List<String> semanticErrors) {
        semanticErrors.add(
                super.errorsRep.ilegalUseError(
                        this.inv.getName(), this.inv.getPosition()
                )
        );
        return new Label(Analyzator.ERROR_TYPE, this.inv.getPosition());
    } 
    
    public MethodST findMethod(SymbolTable symbolTable, List<InfParam> argsStringList,
            List<String> semanticErrors, boolean convert) {
        if (symbolTable.containsKey(this.inv.getName())) {
            RowST rowST = symbolTable.get(this.inv.getName());
            if (rowST instanceof MethodST) {
                MethodST functionST = (MethodST) rowST;
                if (convert) {
                    if (refFun.hasTheSameConvertArgs(functionST.getParams(), argsStringList)) {
                        return functionST;
                    } else {
                        return validateOtherMethods(symbolTable, argsStringList, convert);
                    }
                } else {
                    if (refFun.hasTheSameArgs(functionST.getParams(), argsStringList)) {
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
    
    private MethodST validateOtherMethods(SymbolTable symbolTable, List<InfParam> argsStringList, 
            boolean convert) {
        int index = 1;
        while (symbolTable.containsKey(
                refFun.getSTName(this.inv.getName(), index))) {
            RowST anotherRowST = symbolTable.get(refFun.getSTName(this.inv.getName(), index));
            if (anotherRowST instanceof MethodST) {
                MethodST f1 = (MethodST) anotherRowST;
                if (convert) {
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
    public Label validate(CImports imports, JSymbolTable clasesST, SymbolTable symbolTable, 
            SymbolTable pascalST, TypeTable typeTable, List<String> semanticErrors, Label previus) {
        lastST = symbolTable;
        List<InfParam> argsStringList = super.refFun.validateArgs(
                args, imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
        );
        if (clasesST.containsKey(previus.getName())) {
            ClassST classST = clasesST.get(previus.getName());
            SymbolTable currentST = classST.getMethodsST();
       
            methodST = findMethod(currentST, argsStringList, semanticErrors, false);
            if (methodST != null) {
                this.type = super.tconvert.convertAllPrimitive(methodST.getType());
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
        throw new AssertionError("No se puede invocar un metodo en el metodo estatico");
    }

    @Override
    public RetJInvC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, RetJInvC3D previus) {
        return this.methodGenC3D.generateMethodCuartetas(
                admiMemory, internalCuartetas, temporals, previus, methodST, lastST, type, args
        );
    }

    @Override
    public boolean hasReturnType() {
        return !methodST.getType().equals(Analyzator.VOID_METHOD);
    }

    @Override
    public boolean isStatement() {
        return true;
    }
    
}
