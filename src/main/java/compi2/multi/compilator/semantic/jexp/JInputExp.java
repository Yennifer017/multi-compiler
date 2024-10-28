
package compi2.multi.compilator.semantic.jexp;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.Analyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.access.RegisterUse;
import compi2.multi.compilator.c3d.access.TemporalUse;
import compi2.multi.compilator.c3d.cuartetas.AssignationC3D;
import compi2.multi.compilator.c3d.cuartetas.funcs.ScanC3D;
import compi2.multi.compilator.c3d.util.AdmiRegisters;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.util.Register;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
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
public class JInputExp extends JExpression{
    private JTypeInput type;
    private AdmiRegisters admiRegisters;
    
    public JInputExp(Position pos, JTypeInput type){
        super(pos);
        this.type = type;
        this.admiRegisters = new AdmiRegisters();
    }

    @Override
    public Label validateData(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        return new Label(this.type.getPrimType().getName(), pos);
    }

    @Override
    public Label validateSimpleData(List<String> semanticErrors) {
        semanticErrors.add(errorsRep.ilegalUseError("Funcion predefinida de input", pos));
        return new Label(Analyzator.ERROR_TYPE, pos);
    }

    @Override
    public RetParamsC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass) {
        Register register = admiRegisters.findRegister(type.getPrimType(), 1);
        int countTemp = temporals.getCount(type.getPrimType());
        temporals.increment(type.getPrimType(), 1);
        internalCuartetas.add(
                new ScanC3D(new RegisterUse(register))
        );
        internalCuartetas.add(
                new AssignationC3D(
                        new TemporalUse(type.getPrimType(), countTemp, temporals), 
                        new RegisterUse(register)
                )
        );
        return new RetParamsC3D(
                new TemporalUse(type.getPrimType(), countTemp, temporals)
        );
    }

    
}
