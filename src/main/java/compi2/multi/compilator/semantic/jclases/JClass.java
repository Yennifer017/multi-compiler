
package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.DefAst;
import compi2.multi.compilator.semantic.DefObjsAst;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JClass extends DefObjsAst{
    
    public static final String FATHER_OBJECT_CLASS = "Object";
    
    private Label herence;
    private List<JDef> definitions;
    
    public JClass(Label name, Label herence, List<JDef> definitions){
        super();
        super.name = name;
        if(herence == null){
            herence = new Label(FATHER_OBJECT_CLASS, name.getPosition());
        }
        this.herence = herence;
        this.definitions = definitions;
    }

    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, 
            NodeJerarTree jerarTree, List<String> semanticErrors) {
        //validar la herencia
        /*boolean existSuperClass = false;
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
            
        }*/
        return null;
    }

    
}
