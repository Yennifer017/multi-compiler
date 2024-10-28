
package compi2.multi.compilator.semantic.jclases;

import compi2.multi.compilator.semantic.jclases.components.JType;
import compi2.multi.compilator.semantic.jclases.components.Typable;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.FieldST;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JField extends JDef implements Typable{
    
    private JExpression expAssign;
    private JType type;
    
    private SymbolTable symbolTable;
    
    public JField(){
        super(null);
    }

    @Override
    public RowST generateRowST(SymbolTable symbolTable, TypeTable typeTable, List<String> semanticErrors) {
        String nameForST = getNameFunctionForST(symbolTable);
        if(nameForST != null){
            int dir = symbolTable.getLastDir();
            symbolTable.incrementLastDir(1);
            return new FieldST(
                    name.getName(), 
                    type.getName().getName(), 
                    access, 
                    dir, 
                    this
            );
        } else {
            semanticErrors.add(
                super.errorsRep.repeatedDeclarationError(name.getName(), name.getPosition())
            );
        }
        return null;
    }

    @Override
    public void defineType(JType type) {
        this.type = type;
    }
    
    protected String getNameFunctionForST(SymbolTable symbolTable){
        if(symbolTable.containsKey(name.getName())){            
            /*RowST rowST = symbolTable.get(name.getName());
            if(rowST instanceof FieldST){
                return null;
            } else {
                return verificateOthersFields(symbolTable);
            }*/
            return null;
        } else {
            return this.name.getName();
        }
    }
    
    /*private String verificateOthersFields(SymbolTable symbolTable){
        int index = 1;
        while (symbolTable.containsKey(
                refFun.getSTName(this.name.getName(), index))) {
            RowST anotherRowST = symbolTable.get(refFun.getSTName(this.name.getName(), index));
            if (anotherRowST instanceof FieldST) {
                return null;
            }
            index++;
        }
        return refFun.getSTName(this.name.getName(), index);
    }*/

    @Override
    public void validateInternal(JSymbolTable globalST, TypeTable typeTable, List<String> semanticErrors) {
        this.type.validateSemantic(globalST, typeTable, semanticErrors, false);
        if(expAssign != null){
            Label typeAssign = expAssign.validateData(globalST, symbolTable, 
                    typeTable, 
                    jerar, 
                    semanticErrors, 
                    new SemanticRestrictions(false, false, "")
            );
            if(!typeAssign.getName().equals(this.type.getCompleateName())){
                semanticErrors.add(errorsRep.incorrectTypeError(
                        typeAssign.getName(), 
                        this.type.getCompleateName(), 
                        typeAssign.getPosition())
                );
            }
        }
    }
    
}
