package compi2.multi.compilator.semantic.cast.dec;

import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.typet.PrimitiveType;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.ExpGenC3D;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.AtomicValue;
import compi2.multi.compilator.c3d.access.MemoryAccess;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.StackAccess;
import compi2.multi.compilator.c3d.access.StackPtrUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.cuartetas.OperationC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.semantic.DefiniteOperation;
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

    private SingleData singleData;

    private ExpGenC3D expGenC3D;

    public CVarDec(Label name, PrimitiveType type, CExp exp) {
        super.name = name;
        this.type = type;
        this.exp = exp;
        this.expGenC3D = new ExpGenC3D();
    }

    public CVarDec(Label name, PrimitiveType type) {
        super.name = name;
        this.type = type;
        this.expGenC3D = new ExpGenC3D();
    }

    @Override
    public RowST generateRowST(CImports imports, JSymbolTable clasesST, SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, List<String> semanticErrors) {
        if (exp != null) {
            Label typeExp = exp.validateComplexData(
                    imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
            );
            if (!typeExp.getName().equals(this.type.getName())
                    && !super.tConvert.canUpgradeType(this.type.getName(), typeExp.getName())) {
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
            this.singleData = new SingleData(
                    name.getName(),
                    Category.Variable,
                    this.type.getName(),
                    lastDir
            );
            return singleData;
        }
        return null;
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory,
            List<Cuarteta> internalCuartetas, Memory temporals) {
        int tempCount = temporals.getIntegerCount();
        temporals.setIntegerCount(tempCount + 1);
        if(exp != null){
            MemoryAccess access = expGenC3D.getAccess(
                    exp, admiMemory, internalCuartetas, temporals, new C3Dpass()
            );
            internalCuartetas.add(
                    new OperationC3D(
                            new RegisterUse(Register.BX_INT), 
                            new StackPtrUse(), 
                            new AtomicValue(singleData.getRelativeDir()), 
                            DefiniteOperation.Addition
                    )
            );
            internalCuartetas.add(
                    new AssignationC3D(
                            new StackAccess(type, new RegisterUse(Register.BX_INT)),
                            access
                    )
            );
        } else {
            internalCuartetas.add(
                    new OperationC3D(
                            new RegisterUse(Register.BX_INT), 
                            new StackPtrUse(), 
                            new AtomicValue(singleData.getRelativeDir()), 
                            DefiniteOperation.Addition
                    )
            );
            internalCuartetas.add(
                    new AssignationC3D(
                            new StackAccess(type, new RegisterUse(Register.BX_INT)),
                            new AtomicValue(type.getDefaultVal())
                    )
            );
        }
    }

}
