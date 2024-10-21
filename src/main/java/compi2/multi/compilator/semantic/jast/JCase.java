
package compi2.multi.compilator.semantic.jast;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analysis.typet.convert.TConvertidor;
import compi2.multi.compilator.analyzator.StmtsAnalizator;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.ErrorsRep;
import compi2.multi.compilator.util.Position;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter @Setter
public class JCase {
    private JExpression passExp;
    private List<JStatement> stmts;
    private Position initPos;
    
    private TConvertidor tConvertidor;
    private ErrorsRep errorsRep;
    private StmtsAnalizator stmtsAnalizator;

    public JCase(Position initPos, JExpression passExp, List<JStatement> stmts) {
        this.initPos = initPos;
        this.passExp = passExp;
        this.stmts = stmts;
        this.tConvertidor = new TConvertidor();
        this.errorsRep = new ErrorsRep();
        this.stmtsAnalizator = new StmtsAnalizator();
    }

    public JCase(Position initPos, List<JStatement> stmts) {
        this.initPos = initPos;
        this.stmts = stmts;
        this.tConvertidor = new TConvertidor();
        this.errorsRep = new ErrorsRep();
        this.stmtsAnalizator = new StmtsAnalizator();
    }
    
    public boolean isDefault(){
        return this.passExp == null;
    }
    
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions, String typeLabel) {
        validateLabel(semanticErrors, typeLabel);
        return stmtsAnalizator.validateInternalStmts(
                globalST, symbolTable, typeTable, jerar, semanticErrors, restrictions, stmts
        );
    }
    
    private void validateLabel(List<String> semanticErrors, String typeLabel){
        if (passExp != null) {
            Label type = passExp.validateSimpleData(semanticErrors);
            if (!type.getName().equals(typeLabel)
                    && !tConvertidor.canUpgradeType(typeLabel, type.getName())) {
                semanticErrors.add(errorsRep.incorrectTypeError(
                        type.getName(),
                        typeLabel,
                        type.getPosition()
                ));
            }
        }
    }
    
}
