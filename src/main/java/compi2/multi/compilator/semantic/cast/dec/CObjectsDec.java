
package compi2.multi.compilator.semantic.cast.dec;

import compi2.multi.compilator.analysis.symbolt.RowST;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.semantic.c.CDef;
import compi2.multi.compilator.semantic.c.CImports;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class CObjectsDec extends CDef{
    private List<CObjectDec> objects;
    
    public CObjectsDec(List<CObjectDec> objects){
        this.objects = objects;
    }

    @Override
    public RowST generateRowST(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<String> semanticErrors) {
        for (CObjectDec object : objects) {
            RowST rowST = object.generateRowST(
                    imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors
            );
            if(rowST != null){
                symbolTable.put(rowST.getName(), rowST);
            }
        }
        return null;
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, Memory temporals) {
        for (CObjectDec object : objects) {
            object.generateCuartetas(admiMemory, internalCuartetas, temporals);
        }
    }

    
}
