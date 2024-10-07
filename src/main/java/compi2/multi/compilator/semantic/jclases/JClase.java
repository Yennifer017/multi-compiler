
package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.DefAst;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class JClase extends DefAst{
    
    private Label herence;
    
    public JClase(Label name, Label herence){
        super();
        super.name = name;
        this.herence = herence;
    }

    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors) {
        //validar la herencia
        boolean existSuperClass = false;
        if(herence != null){
            existSuperClass = super.refAnalyzator.existReference(
                    symbolTable, semanticErrors, herence
            );
        }
        
        if(super.canInsert(symbolTable, semanticErrors)){
            SymbolTable auxST =  null;
            if(existSuperClass) {
                RowST rowST = symbolTable.get(herence.getName());
            }
            
        }
        return null;
    }
    
}
