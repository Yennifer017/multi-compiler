
package compi2.multi.compilator.semantic.pmodule;

import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SingleData;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.DefAst;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class Argument extends DefAst{
    private boolean isForReference;
    private Label type;

    public Argument(boolean isForReference, Label name, Label type) {
        super();
        this.isForReference = isForReference;
        super.name = name;
        this.type = type;
    }


    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors) {
        if(canInsert(symbolTable, semanticErrors) && refAnalyzator.existReference(typeTable, semanticErrors, type)){
            int relativeDir = symbolTable.getLastDir();
            symbolTable.incrementLastDir(1);
            return new SingleData(
                    name.getName(),
                    isForReference ? Category.Param_ref : Category.Param_val, 
                    type.getName(), 
                    relativeDir
            );
        }
        return null;
    }
    
}
