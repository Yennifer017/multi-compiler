
package compi2.multi.compilator.semantic.jast.inv;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.FunctionRefAnalyzator;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public abstract class JInvocation {
    protected Position position;
    protected JContextRef context;
    
    protected FunctionRefAnalyzator refFun;
    
    public JInvocation(Position position, JContextRef context){
        this.position = position;
        this.context = context;
        this.refFun =  new FunctionRefAnalyzator();
    }
    
    public abstract Label validate(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors);
    
    public abstract Label validate(JSymbolTable globalST, SymbolTable symbolTable,
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrorrs, Label previus);
}
