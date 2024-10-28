
package compi2.multi.compilator.semantic.cast.dec;

import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.InfParam;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.ConstructorST;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.ObjectST;
import compi2.multi.compilator.analysis.symbolt.estruc.FunctionST;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.FunctionRefAnalyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.AtomicValue;
import compi2.multi.compilator.c3d.access.StackPtrUse;
import compi2.multi.compilator.c3d.cuartetas.MethodInvC3D;
import compi2.multi.compilator.c3d.cuartetas.OperationC3D;
import compi2.multi.compilator.exceptions.NoDataFoundEx;
import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.c.CDef;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
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
public class CObjectDec extends CDef {
    
    private Label objectName;
    private List<CExp> args;
    
    private FunctionRefAnalyzator refFun;
    
    private ConstructorST constructorST;
    private SymbolTable st;
    private ObjectST objectST;
    
    public CObjectDec(Label name){
        super.name = name;
        args = new LinkedList<>();
        this.refFun = new FunctionRefAnalyzator();
    }
    
    public CObjectDec(Label name, List<CExp> args){
        super.name = name;
        this.args = args;
    }

    @Override
    public RowST generateRowST(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<String> semanticErrors) {
        if(symbolTable.containsKey(this.name.getName())){
            semanticErrors.add(super.errorsRep.repeatedDeclarationError(
                    name.getName(), name.getPosition())
            );
            return null;
        } else {
            List<InfParam> argsStringList = refFun.validateArgs(
                    args, imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
            );
            if(clasesST.containsKey(objectName.getName())){
                SymbolTable objST = clasesST.get(objectName.getName()).getMethodsST();
                constructorST = existReference(objST, argsStringList, semanticErrors);
                st = symbolTable;
                if(constructorST != null){
                    this.objectST = new ObjectST(this.name.getName(), objectName.getName());
                    return this.objectST;
                }
            } else {
                semanticErrors.add(
                        errorsRep.undefiniteClassError(
                                objectName.getName(), objectName.getPosition()
                        )
                );
            }
        }
        return null;
    }
    
    private ConstructorST existReference(SymbolTable symbolTable, List<InfParam> argsStringList, 
            List<String> semanticErrors){
        if(symbolTable.containsKey(objectName.getName())){
            RowST rowST = symbolTable.get(objectName.getName());
            //exactly match
            if(rowST instanceof ConstructorST){
                ConstructorST constructorST = (ConstructorST) rowST;
                if(refFun.hasTheSameArgs(constructorST.getParams(), argsStringList)){
                    return constructorST;
                } else {
                    try {
                        return verificateOthersConstructs(symbolTable, argsStringList);
                    } catch (NoDataFoundEx e) {
                    }
                }
            } else {
                try {
                    return verificateOthersConstructs(symbolTable, argsStringList);
                } catch (NoDataFoundEx e) {
                }
            }
            //converted match
            if(rowST instanceof ConstructorST){
                ConstructorST constructorST = (ConstructorST) rowST;
                if(refFun.hasTheSameConvertArgs(constructorST.getParams(), argsStringList)){
                    return constructorST;
                } else {
                    try {
                        return verificateTransConstructs(symbolTable, argsStringList);
                    } catch (NoDataFoundEx e) {
                    }
                }
            } else {
                try {
                    return verificateTransConstructs(symbolTable, argsStringList);
                } catch (NoDataFoundEx e) {
                }
            }
        }
        semanticErrors.add(
                super.errorsRep.undefiniteConstructorError(name.getPosition())
        );
        return null;
    }

    private ConstructorST verificateOthersConstructs(SymbolTable symbolTable, List<InfParam> argsStringList) 
            throws NoDataFoundEx{
        int index = 1;
        while (symbolTable.containsKey(
                refFun.getSTName(this.name.getName(), index))) {
            RowST anotherRowST = symbolTable.get(refFun.getSTName(this.name.getName(), index));
            if (anotherRowST instanceof ConstructorST) {
                ConstructorST f1 = (ConstructorST) anotherRowST;
                if (refFun.hasTheSameArgs(f1.getParams(), argsStringList)) {
                    return f1;
                }
            }
            index++;
        }
        throw new NoDataFoundEx();
    }
    
     private ConstructorST verificateTransConstructs(SymbolTable symbolTable, List<InfParam> argsStringList) 
            throws NoDataFoundEx{
        int index = 1;
        while (symbolTable.containsKey(
                refFun.getSTName(this.name.getName(), index))) {
            RowST anotherRowST = symbolTable.get(refFun.getSTName(this.name.getName(), index));
            if (anotherRowST instanceof ConstructorST) {
                ConstructorST f1 = (ConstructorST) anotherRowST;
                if (refFun.hasTheSameConvertArgs(f1.getParams(), argsStringList)) {
                    return f1;
                }
            }
            index++;
        }
        throw new NoDataFoundEx();
    }
     
    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals) {
        internalCuartetas.add(
                new OperationC3D(
                        new StackPtrUse(), 
                        new StackPtrUse(), 
                        new AtomicValue(st.getLastDir()), 
                        DefiniteOperation.Addition
                )
        );
        internalCuartetas.add(
                new MethodInvC3D(constructorST.getCompleateName())
        );
        internalCuartetas.add(
                new OperationC3D(
                        new StackPtrUse(), 
                        new StackPtrUse(), 
                        new AtomicValue(st.getLastDir()), 
                        DefiniteOperation.Substraction
                )
        );
        //recuperar la instancia del stack y setearla en la posicion original del stack
    }
    
}
