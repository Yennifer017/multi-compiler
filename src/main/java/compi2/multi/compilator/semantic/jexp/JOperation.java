
package compi2.multi.compilator.semantic.jexp;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.exceptions.ConvPrimitiveException;
import compi2.multi.compilator.semantic.DefiniteOperation;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JOperation extends JExpression{
    
    private JExpression right;
    private JExpression left;
    
    private DefiniteOperation operation;
    
    public JOperation(Position pos, DefiniteOperation operation, 
            JExpression right, JExpression left){
        super(pos);
        this.operation = operation;
        this.left = left;
        this.right = right;
    }

    @Override
    public Label validateData(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        Label leftType = left.validateData(
                globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
        );
        Label rightType = right.validateData(
                globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
        );
        return new Label(
                super.tConvert.complexConvert(operation, leftType, rightType, semanticErrors), 
                pos
        );
    }

    @Override
    public Label validateSimpleData(List<String> semanticErrors) {
        Label leftType = left.validateSimpleData(semanticErrors);
        Label rightType = right.validateSimpleData(semanticErrors);
        try {
            return new Label(
                    super.tConvert.simpleConvert(operation, leftType, rightType, semanticErrors),
                    pos);
        } catch (ConvPrimitiveException ex) {
            return new Label(Analyzator.ERROR_TYPE, pos);
        }
    }
    
}
