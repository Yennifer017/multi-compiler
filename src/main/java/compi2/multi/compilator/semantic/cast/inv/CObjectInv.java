package compi2.multi.compilator.semantic.cast.inv;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analyzator.RefAnalyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.generators.InvocationsC3DGen;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.semantic.cast.inv.objs.CInvocation;
import compi2.multi.compilator.semantic.jast.inv.InvocationsUtil;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author blue-dragon
 */
@Getter
@Setter
public class CObjectInv extends CStatement {

    private List<CInvocation> invocations;

    private InvocationsUtil invsUtil;
    private InvocationsC3DGen invC3DGen;
    private RefAnalyzator refAnalyzator;

    public CObjectInv(List<CInvocation> invocations) {
        super(null);
        this.invocations = invocations;
        this.invsUtil = new InvocationsUtil();
        this.invC3DGen = new InvocationsC3DGen();
        this.refAnalyzator = new RefAnalyzator();
    }

    @Override
    public ReturnCase validate(CImports imports, JSymbolTable clasesST, SymbolTable symbolTable,
            SymbolTable pascalST, TypeTable typeTable, List<String> semanticErrors,
            SemanticRestrictions restrictions) {
        try {
            CInvocation first = invocations.get(0);
            this.initPos = first.getInv().getPosition();
            invsUtil.validateInvocation(
                    imports, 
                    clasesST, 
                    symbolTable, 
                    pascalST, 
                    typeTable, 
                    semanticErrors, 
                    invocations, 
                    initPos, 
                    false
            );
        } catch (IndexOutOfBoundsException e) {
            //add error
        }

        return new ReturnCase(false);
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas,
            Memory temporals, C3Dpass pass) {
        this.invC3DGen.generateC3DInvocations(
                admiMemory, internalCuartetas, temporals, invocations, -1
        );
    }

}
