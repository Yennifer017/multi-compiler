
package compi2.multi.compilator.analyzator;

import compi2.multi.compilator.analysis.jerarquia.NodeJerarTree;
import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.semantic.util.ReturnCase;
import compi2.multi.compilator.semantic.util.SemanticRestrictions;
import compi2.multi.compilator.semantic.p.Statement;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.semantic.j.JStatement;
import compi2.multi.compilator.util.ErrorsRep;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class StmtsAnalizator {
    private ErrorsRep errorsRep;
    
    public StmtsAnalizator(){
        errorsRep = new ErrorsRep();
    }
    
    public ReturnCase validateInternalStmts(SymbolTable symbolTable, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions, 
            List<Statement> internalStmts){
        boolean allCovered = false;
        if(internalStmts !=  null){
            for (int i = 0; i < internalStmts.size(); i++) {
                try {
                    Statement stmt = internalStmts.get(i);
                    ReturnCase retCase = stmt.validate(
                            symbolTable, typeTable, semanticErrors, 
                            restrictions
                    );
                    if(allCovered){
                        semanticErrors.add(errorsRep.unrachableCodeError(stmt.getInitPos()));
                    }
                    if(retCase.isAllScenaries() && i != internalStmts.size() && !allCovered){
                        allCovered = true;
                    } else if(retCase.isAllScenaries() && i == internalStmts.size()){
                        return new ReturnCase(true);
                    }
                } catch (NullPointerException e) {
                    semanticErrors.add("Ocurrio un error inesperado al intentar recuperar una instruccion");
                }
            }
        }
        return new ReturnCase(allCovered);
    }
    
    public ReturnCase validateInternalStmts(JSymbolTable globalST, SymbolTable symbolTable, 
            TypeTable typeTable, NodeJerarTree jerar, List<String> semanticErrors, 
            SemanticRestrictions restrictions, List<JStatement> internalStmts){
        boolean allCovered = false;
        if(internalStmts !=  null){
            for (int i = 0; i < internalStmts.size(); i++) {
                try {
                    JStatement stmt = internalStmts.get(i);
                    ReturnCase retCase = stmt.validate(
                            globalST, 
                            symbolTable, 
                            typeTable, 
                            jerar, 
                            semanticErrors, 
                            restrictions
                    );
                    if(allCovered){
                        semanticErrors.add(errorsRep.unrachableCodeError(stmt.getInitPos()));
                    }
                    if(retCase.isAllScenaries() && i != internalStmts.size() && !allCovered){
                        allCovered = true;
                    } else if(retCase.isAllScenaries() && i == internalStmts.size()){
                        return new ReturnCase(true);
                    }
                } catch (NullPointerException e) {
                    semanticErrors.add("Ocurrio un error inesperado al intentar recuperar una j-instruccion");
                }
            }
        }
        return new ReturnCase(allCovered);
    }
    
    public ReturnCase validateInternalStmts(CImports imports, JSymbolTable clasesST, 
            SymbolTable symbolTable, SymbolTable pascalST, TypeTable typeTable, 
            List<String> semanticErrors, SemanticRestrictions restrictions,
            List<CStatement> internalStmts){
        boolean allCovered = false;
        if(internalStmts !=  null){
            for (int i = 0; i < internalStmts.size(); i++) {
                try {
                    CStatement stmt = internalStmts.get(i);
                    ReturnCase retCase = stmt.validate(
                            imports, 
                            clasesST, 
                            symbolTable, 
                            pascalST, 
                            typeTable, 
                            semanticErrors, 
                            restrictions
                    );
                    if(allCovered){
                        semanticErrors.add(errorsRep.unrachableCodeError(stmt.getInitPos()));
                    }
                    if(retCase.isAllScenaries() && i != internalStmts.size() && !allCovered){
                        allCovered = true;
                    } else if(retCase.isAllScenaries() && i == internalStmts.size()){
                        return new ReturnCase(true);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    semanticErrors.add("Ocurrio un error inesperado al intentar recuperar una c instruction");
                }
            }
        }
        return new ReturnCase(allCovered);
    }
}
