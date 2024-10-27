
package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.semantic.jclases.components.JArg;
import compi2.multi.compilator.analysis.symbolt.AccessMod;
import compi2.multi.compilator.analysis.symbolt.AdditionalInfoST;
import compi2.multi.compilator.analysis.symbolt.InfParam;
import compi2.multi.compilator.analysis.symbolt.ReturnRow;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.DirInstanceST;
import compi2.multi.compilator.analysis.symbolt.clases.HeapDirecST;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.RefAnalyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.util.Label;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public abstract class JFunction extends JDef{
    protected Label nameClass;
    protected List<JArg> args;
    protected List<JStatement> internalStmts;

    public JFunction(AccessMod access, List<JArg> args, List<JStatement> internalStmts) {
        super(access);
        this.args = args;
        this.internalStmts = internalStmts;
        this.refAnalyzator = new RefAnalyzator();
    }
    
    protected List<InfParam> generateArgsList(){
        List<InfParam> list = new ArrayList<>();
        if(args != null && !args.isEmpty()){
            for (JArg arg : args) {
                //list.add(arg.getType().getCompleateName());
                list.add(new InfParam(arg.getType().getCompleateName(), arg.getName().getName()));
            }
        }
        return list;
    }
    
    protected SymbolTable generateInternalST(boolean hasReturn){
        SymbolTable internal = new SymbolTable();
        if(hasReturn){
            internal.put(AdditionalInfoST.DIR_RETORNO_ROW.getNameRow(),
                    new ReturnRow(internal.getLastDir())
            );
            internal.incrementLastDir(1);
        }
        
        internal.put(
                AdditionalInfoST.DIR_HEAP_ROW.getNameRow(), new HeapDirecST(internal.getLastDir())
        );
        internal.incrementLastDir(1);
        internal.put(
                AdditionalInfoST.DIR_INSTANCE_ROW.getNameRow(), new DirInstanceST(internal.getLastDir())
        );
        internal.incrementLastDir(1);
        return internal;
    }
    
    protected abstract String getNameFunctionForST(SymbolTable symbolTable, List<InfParam> argsStringList);
    
    protected void validateArgs(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, List<String> semanticErrors){
        if(this.args != null && !this.args.isEmpty()){
            for (JArg arg : this.args) {
                arg.validateSemantic(
                        globalST, 
                        symbolTable,
                        typeTable, 
                        semanticErrors
                );
            }
        }
    }
    
    protected void validateInternalStmts(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, List<String> semanticErrors){
        if(this.internalStmts != null && !this.internalStmts.isEmpty()){
            
        }
    }
    
    public abstract void generateCuartetas(AdmiMemory admiMemory, SymbolTable fields);
    
    protected void generateInternalCuartetas(AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, Memory temporals){
        for (JStatement internalStmt : internalStmts) {
            internalStmt.generateCuartetas(
                    admiMemory, internalCuartetas, temporals, new C3Dpass()
            );
        }
    }
    
    protected String getFinalName(String parcialName){
        return "JAVA_" + nameClass.getName() + "_" + parcialName;
    }
}
