
package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.semantic.jclases.components.JArg;
import compi2.multi.compilator.analysis.symbolt.AccessMod;
import compi2.multi.compilator.analysis.symbolt.AdditionalInfoST;
import compi2.multi.compilator.analysis.symbolt.ReturnRow;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.DirInstanceST;
import compi2.multi.compilator.analysis.symbolt.clases.HeapDirecST;
import compi2.multi.compilator.analyzator.RefAnalyzator;
import compi2.multi.compilator.semantic.Statement;
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
    protected List<Statement> internalStmts;
    protected RefAnalyzator refAnalyzator;

    public JFunction(AccessMod access, List<JArg> args, List<Statement> internalStmts) {
        super(access);
        this.args = args;
        this.internalStmts = internalStmts;
        this.refAnalyzator = new RefAnalyzator();
    }
    
    protected List<String> generateArgsList(){
        List<String> list = new ArrayList<>();
        if(args != null && !args.isEmpty()){
            for (JArg arg : args) {
                list.add(arg.getType().getName().getName());
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
    
    protected abstract String getNameFunctionForST(SymbolTable symbolTable, List<String> argsStringList);
    
}
