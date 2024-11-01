
package compi2.multi.compilator.semantic.j;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.analysis.typet.convert.TConvertidor;
import compi2.multi.compilator.analyzator.RefAnalyzator;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.util.RetParamsC3D;
import compi2.multi.compilator.semantic.util.Label;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.util.ErrorsRep;
import compi2.multi.compilator.util.Position;
import java.util.List;
import compi2.multi.compilator.c3d.util.ExpressionGenerateC3D;

/**
 *
 * @author blue-dragon
 */
public abstract class JExpression implements ExpressionGenerateC3D{
    protected Position pos;
    protected ErrorsRep errorsRep;
    protected TConvertidor tConvert;
    protected RefAnalyzator refAnalyzator;
    
    public JExpression(Position pos){
        this.pos = pos;
        errorsRep = new ErrorsRep();
        tConvert = new TConvertidor();
        refAnalyzator = new RefAnalyzator();
    }
    
    /**
     * Valida el tipo de la expresion
     * @param globalST
     * @param symbolTable
     * @param typeTable
     * @param jerar
     * @param semanticErrors
     * @param restrictions
     * @return el nombre del tipo encontrado
     */
    public abstract Label validateData(JSymbolTable globalST, SymbolTable symbolTable, TypeTable typeTable, 
            NodeJerarTree jerar, List<String> semanticErrors, SemanticRestrictions restrictions);
    
    public abstract Label validateSimpleData(List<String> semanticErrors);
    
    @Override
    public abstract RetParamsC3D generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass);
    
}
