
package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.exceptions.ConvPrimitiveException;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.ReturnCase;
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
public class JDefiniteFunc extends JStatement{
    
    private boolean withLn;
    private List<JExpression> listToPrint;

    public JDefiniteFunc(Position initPos, List<JExpression> listToPrint, boolean withLn) {
        super(initPos);
        this.listToPrint = listToPrint;
        this.withLn = withLn;
    }

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        if(!listToPrint.isEmpty()){
            for (JExpression jExpression : listToPrint) {
                Label type = jExpression.validateData(
                        globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions
                );
                try {
                    tConvert.convertPrimitive(type.getName());
                } catch (ConvPrimitiveException e) {
                    semanticErrors.add(errorsRep.incorrectTypeError(
                            type.getName(), "tipo primitivo", type.getPosition())
                    );
                }
            }
        }
        return new ReturnCase(false);
    }

}
