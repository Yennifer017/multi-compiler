package compi2.multi.compilator.semantic.cast.inv.objs;

import compi2.multi.compilator.analysis.symbolt.Category;
import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.ClassST;
import compi2.multi.compilator.analysis.symbolt.clases.FieldST;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.symbolt.estruc.SingleData;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.generators.InvC3DGen;
import compi2.multi.compilator.c3d.util.RetJInvC3D;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.util.Label;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class CObjUse extends CInvocation {
    
    private InvC3DGen invC3DGen;

    private RowST rowST;

    public CObjUse(Label inv) {
        super(inv);
        this.invC3DGen = new InvC3DGen();
    }

    @Override
    public Label validate(CImports imports, JSymbolTable clasesST, SymbolTable symbolTable,
            SymbolTable pascalST, TypeTable typeTable, List<String> semanticErrors) {
        if (symbolTable.containsKey(this.inv.getName())) {
            rowST = symbolTable.get(this.inv.getName());
            if (rowST.getCategory().equals(Category.JObject)) {
                SingleData singleData = (SingleData) rowST;
                return new Label(singleData.getType(), this.inv.getPosition());
            } else {
                semanticErrors.add(
                        super.errorsRep.noObjectVarError(
                                this.inv.getName(),
                                this.inv.getPosition()
                        )
                );
            }
        } else {
            semanticErrors.add(
                    super.errorsRep.undefiniteVarUseError(
                            this.inv.getName(),
                            this.inv.getPosition()
                    )
            );
        }
        return new Label(Analyzator.ERROR_TYPE, this.inv.getPosition());
    }

    @Override
    public Label validate(CImports imports, JSymbolTable clasesST,
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable,
            List<String> semanticErrors, Label previus) {
        if (clasesST.containsKey(previus.getName())) {
            ClassST classST = clasesST.get(previus.getName());
            SymbolTable fieldsClassST = classST.getFieldsST();
            if (fieldsClassST.containsKey(this.inv.getName())) {
                rowST = fieldsClassST.get(this.inv.getName());
                return new Label(rowST.getType(), this.inv.getPosition());
            } else {
                semanticErrors.add(errorsRep.invalidInvocationError(
                        previus.getName(), this.inv.getName(), this.inv.getPosition())
                );
            }
        } else {
            semanticErrors.add(errorsRep.invalidInvocationError(
                    previus.getName(), this.inv.getName(), this.inv.getPosition())
            );
        }
        return new Label(Analyzator.ERROR_TYPE, this.inv.getPosition());
    }

    @Override
    public RetJInvC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, int instanceStackRef) {
        SingleData singleData = (SingleData) rowST;
        return invC3DGen.generateCuartAccessVar(
                admiMemory, internalCuartetas, temporals, instanceStackRef, singleData
        );
    }

    @Override
    public RetJInvC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, RetJInvC3D previus) {
        FieldST fieldST = (FieldST) rowST;
        return this.invC3DGen.generateCuartAccessVar(
                admiMemory, internalCuartetas, temporals, previus, fieldST
        );
    }

    @Override
    public boolean hasReturnType() {
        return true;
    }

    @Override
    public boolean isStatement() {
        return false;
    }

}
