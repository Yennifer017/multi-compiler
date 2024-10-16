
package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.analysis.symbolt.AccessMod;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.FunctionRefAnalyzator;
import compi2.multi.compilator.analyzator.RefAnalyzator;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.ErrorsRep;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public abstract class JDef{
    protected Label name;
    protected AccessMod access;
    protected ErrorsRep errorsRep;
    protected RefAnalyzator refAnalyzator;
    protected FunctionRefAnalyzator refFun;
    
    public JDef(AccessMod access){
        this.access = access;
        this.refAnalyzator = new RefAnalyzator();
        this.errorsRep = new ErrorsRep();
        this.refFun = new FunctionRefAnalyzator();
    }
    
    public abstract RowST generateRowST(SymbolTable symbolTable, 
            TypeTable typeTable, List<String> semanticErrors);
    
    public abstract void validateInternal(JSymbolTable globalST,
            TypeTable typeTable, List<String> semanticErrors);
    
}
