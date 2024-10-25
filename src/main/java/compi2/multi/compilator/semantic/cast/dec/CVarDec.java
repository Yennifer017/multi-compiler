package compi2.multi.compilator.semantic.cast.dec;

import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.c.CDef;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter
@Setter
public class CVarDec extends CDef {

    private PrimitiveType type;
    private CExp exp;

    public CVarDec(Label name, PrimitiveType type, CExp exp) {
        super.name = name;
        this.type = type;
        this.exp = exp;
    }

    public CVarDec(Label name, PrimitiveType type) {
        super.name = name;
        this.type = type;
    }

    @Override
    public RowST generateRowST(CImports imports, JSymbolTable clasesST, SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, List<String> semanticErrors) {
        if (exp != null) {
            Label typeExp = exp.validateComplexData(
                    imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
            );
            if (!typeExp.getName().equals(this.type.getName())
                    && !super.tConvert.canUpgradeType(this.type.getCName(), typeExp.getName())) {
                semanticErrors.add(super.errorsRep.incorrectTypeError(
                        typeExp.getName(),
                        this.type.getName(),
                        typeExp.getPosition())
                );
            }
        }
        if (super.refAnalyzator.canInsert(name, symbolTable, semanticErrors)) {
            int lastDir = symbolTable.getLastDir();
            symbolTable.incrementLastDir(1);
            return new SingleData(
                    name.getName(),
                    Category.Variable,
                    this.type.getCName(),
                    lastDir
            );
        }
        return null;
    }

}
