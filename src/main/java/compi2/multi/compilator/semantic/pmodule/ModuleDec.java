
package compi2.multi.compilator.semantic.pmodule;

import compi2.multi.compilator.analysis.symbolt.estruc.FunctionST;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.FunctionRefAnalyzator;
import compi2.multi.compilator.analyzator.GenSymbolTab;
import compi2.multi.compilator.analyzator.RefAnalyzator;
import compi2.multi.compilator.analyzator.StmtsAnalizator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.Statement;
import compi2.multi.compilator.semantic.DefAst;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public abstract class ModuleDec extends DefAst{
    protected List<Argument> args;
    protected List<Statement> statements;
    protected List<DefAst> varDef;
    
    protected GenSymbolTab genSymbolTab;
    protected StmtsAnalizator stmtsAnalizator;
    protected RefAnalyzator refAnalyzator;
    private FunctionRefAnalyzator refFun;
    
    protected FunctionST functionST;
    
    public ModuleDec(){
        super();
        genSymbolTab = new GenSymbolTab();
        stmtsAnalizator = new StmtsAnalizator();
        refAnalyzator = new RefAnalyzator();
        refFun = new FunctionRefAnalyzator();
    }
    
    /**
     * verifica si una funcion se puede insertar considerando sus parametross
     * @param symbolTable
     * @param argsStringList
     * @return el nombre de la funcion si se puede declarar, null de lo contrario.
     */
    protected String getNameFunctionForST(SymbolTable symbolTable, List<String> argsStringList){
        if(symbolTable.containsKey(name.getName())){
            RowST rowST = symbolTable.get(name.getName());
            if(rowST instanceof FunctionST){
                FunctionST functionST = (FunctionST) rowST;
                if(refFun.hasTheSameArgs(functionST.getTypesParams(), argsStringList)){
                    return null;
                } else {
                    int index = 1;

                    while (symbolTable.containsKey(
                            refFun.getSTName(this.name.getName(), index))
                            ) {                
                        FunctionST f1 = (FunctionST) 
                                symbolTable.get(refFun.getSTName(this.name.getName(), index));
                        if(refFun.hasTheSameArgs(f1.getTypesParams(), argsStringList)){
                            return null;
                        }
                        index ++;
                    }
                    return refFun.getSTName(this.name.getName(), index);
                }
            } else {
                return null;
            }
        } else {
            return this.name.getName();
        }
    }
    
    protected List<String> generateArgsStringList(){
        List<String> list = new ArrayList<>();
        if(args !=  null && !args.isEmpty()){
            for (Argument arg : args) {
                list.add(arg.getType().getName());
            }
        }
        return list;
    }
    
    public abstract void validate(TypeTable typeTable, 
            List<String> semanticErrors);
    
    protected void generateInternalCuartetas(AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, Memory temporals){
        if(!varDef.isEmpty()){
            for (DefAst defAst : varDef) {
                defAst.generateCuartetas(admiMemory, internalCuartetas, temporals);
            }
        }
        if(!statements.isEmpty()){
            for (Statement internalStmt : statements) {
                internalStmt.generateCuartetas(
                        admiMemory, internalCuartetas, temporals, new C3Dpass()
                );
            }
        }
    }
    
}
