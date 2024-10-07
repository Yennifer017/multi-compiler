
package compi2.multi.compilator.semantic.pobj;


import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.DefAst;
import compi2.multi.compilator.analysis.symbolt.estruc.ArrayST;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.Limits;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.exceptions.SemanticException;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class ArrayDef extends DefAst{
    
    private Range range;
    private Label base;
    
    public ArrayDef(Label name, Label base, Range range){
        super();
        super.name = name;
        this.base = base;
        this.range = range;
    }

    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors) {
        if(canInsert(symbolTable, semanticErrors)){
            Limits limits = range.validate(symbolTable, semanticErrors, this);
            if(refAnalyzator.existReference(typeTable, semanticErrors, base)){
                try {
                    ArrayST arrayST = new ArrayST(
                            name.getName(), 
                            base.getName(), 
                            limits, 
                            symbolTable.getLastDir()
                    );
                    symbolTable.incrementLastDir(arrayST.getTotalIndex());
                    return arrayST;
                } catch (SemanticException ex) {
                    semanticErrors.add(super.errorsRep.negativeIndexError(name.getName(), 
                            name.getPosition()));
                }
            }
        }
        return null;
    }
}
