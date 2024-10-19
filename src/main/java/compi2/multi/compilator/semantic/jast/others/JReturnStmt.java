package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.j.JExpression;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.Position;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class JReturnStmt extends JStatement {

    private JExpression passExp;

    public JReturnStmt(Position initPos, JExpression passExp) {
        super(initPos);
        this.passExp = passExp;
    }

    @Override
    public ReturnCase validate(JSymbolTable globalST, SymbolTable symbolTable,
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors,
            SemanticRestrictions restrictions) {
        Label type = passExp.validateData(globalST, symbolTable, typeTable,
                jerar, semanticErrors, restrictions);
        if (!type.getName().equals(restrictions.getReturnType())
                && !tConvert.canUpgradeType(restrictions.getReturnType(), type.getName())) {
            semanticErrors.add(errorsRep.incorrectTypeError(
                    type.getName(),
                    restrictions.getReturnType(),
                    type.getPosition())
            );
        }
        return new ReturnCase(true);
    }

}
