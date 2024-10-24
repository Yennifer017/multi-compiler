package compi2.multi.compilator.semantic.jast.others;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.AdditionalInfoST;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.C3Dpass;
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
    
    private RowST rowST;

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
        } else {
            SymbolTable currentST = symbolTable;
            while (currentST != null) {                
                currentST = currentST.getFather();
            }
            rowST = currentST.get(AdditionalInfoST.DIR_RETORNO_ROW.getNameRow());
        }
        return new ReturnCase(true);
    }

}
