
package compi2.multi.compilator.semantic.cast;

import compi2.multi.compilator.analysis.symbolt.SymbolTable;
import compi2.multi.compilator.analysis.symbolt.clases.JSymbolTable;
import compi2.multi.compilator.analysis.typet.TypeTable;
import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.generators.stmts.ConditionalStmtsGenC3D;
import compi2.multi.compilator.c3d.interfaces.ElifGenerateC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import compi2.multi.compilator.semantic.c.CExp;
import compi2.multi.compilator.semantic.c.CImports;
import compi2.multi.compilator.semantic.c.CStatement;
import compi2.multi.compilator.semantic.util.ReturnCase;
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
public class CIfAst extends CControlStmt implements ElifGenerateC3D{
    private CExp condition;
    private List<CIfAst> elifs;
    private CElseAst elseStmt;
    
    private ConditionalStmtsGenC3D stmtsGeneratorC3D;
    
    public CIfAst(Position initPos, CExp condition, List<CStatement> internalStmt) {
        super(initPos, internalStmt);
        this.condition = condition;
        stmtsGeneratorC3D = new ConditionalStmtsGenC3D();
    }
    
    public CIfAst(Position initPos, CExp condition, List<CStatement> statements, 
            List<CIfAst> elifs, CElseAst elseStmt) {
        super(initPos, statements);
        this.condition = condition;
        this.elifs = elifs;
        this.elseStmt = elseStmt;
        stmtsGeneratorC3D = new ConditionalStmtsGenC3D();
    }

    @Override
    public ReturnCase validate(CImports imports, JSymbolTable clasesST, SymbolTable symbolTable, 
            SymbolTable pascalST, TypeTable typeTable, List<String> semanticErrors, 
            SemanticRestrictions restrictions) {
        super.validateCondition(
                imports, clasesST, symbolTable, pascalST, typeTable, 
                semanticErrors, restrictions, condition
        );
        ReturnCase internalRC = super.validateInternal(
                imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors, restrictions
        );
        if(elifs != null && !elifs.isEmpty()){
            for (CIfAst ifAst : elifs) {
                ReturnCase pathRC = ifAst.validate(
                        imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors, restrictions
                );
                if(internalRC.isAllScenaries() && !pathRC.isAllScenaries()){
                    internalRC.setAllScenaries(false);
                }
            }
        }
        
        if(elseStmt != null){
            ReturnCase elseRC = elseStmt.validate(
                    imports, clasesST, symbolTable, pascalST, typeTable, semanticErrors, restrictions
            );
            if(internalRC.isAllScenaries() && !elseRC.isAllScenaries()){
                internalRC.setAllScenaries(false);
            }
        } else {
            internalRC.setAllScenaries(false);
        }
        return internalRC;
    }

    @Override
    public void generateCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass) {
        stmtsGeneratorC3D.generateIfCuartetas(
                admiMemory, 
                internalCuartetas, 
                temporals, 
                pass, 
                internalStmts, 
                condition, 
                elifs, 
                elseStmt
        );
    }

    @Override
    public void generateElifCuartetas(AdmiMemory admiMemory, List<Cuarteta> internalCuartetas, 
            Memory temporals, C3Dpass pass){
        stmtsGeneratorC3D.generateElifCuartetas(
                admiMemory, 
                internalCuartetas, 
                temporals, 
                pass, 
                condition, 
                internalStmts
        );
    }
    
}
