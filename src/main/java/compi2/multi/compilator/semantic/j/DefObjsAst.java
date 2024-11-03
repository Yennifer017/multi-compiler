
package compi2.multi.compilator.semantic.j;

import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.RefAnalyzator;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.util.ErrorsRep;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public abstract class DefObjsAst {
    protected Label name;
    protected ErrorsRep errorsRep;
    protected RefAnalyzator refAnalyzator;
    
     public DefObjsAst(){
        errorsRep = new ErrorsRep();
        refAnalyzator = new RefAnalyzator();
    }
    
    
    public abstract RowST generateRowST(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, List<String> semanticErrors);
    
    protected boolean canInsert(SymbolTable symbolTable, List<String> semanticErrors){
        return refAnalyzator.canInsert(this.name, symbolTable, semanticErrors);
    }
}
