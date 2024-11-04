
package compi2.multi.compilator.c3d.generators.stmts;

import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.cuartetas.GotoC3D;
import compi2.multi.compilator.c3d.cuartetas.LabelC3D;
import compi2.multi.compilator.c3d.generators.ExpGenC3D;
import compi2.multi.compilator.c3d.interfaces.ElifGenerateC3D;
import compi2.multi.compilator.c3d.interfaces.ExpressionGenerateC3D;
import compi2.multi.compilator.c3d.interfaces.StmtGenerateC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class ConditionalStmtsGenC3D {
    
    private ExpGenC3D expGenC3D;
    private StmtsGeneratorC3D stmtsGeneratorC3D;
    
    public ConditionalStmtsGenC3D(){
        this.expGenC3D = new ExpGenC3D();
        stmtsGeneratorC3D = new StmtsGeneratorC3D();
    }
    
    public void generateIfCuartetas(
            AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, 
            Memory temporals, 
            C3Dpass pass, 
            List<? extends StmtGenerateC3D> internalStatements, 
            ExpressionGenerateC3D condition, 
            List<? extends ElifGenerateC3D> elifs, 
            StmtGenerateC3D elseStmt
    ){
        int trueLabel = admiMemory.getCountLabels();
        int falseLabel = trueLabel + 1;
        int endLabel = trueLabel + 2;
        admiMemory.setCountLabels(admiMemory.getCountLabels() + 3);
        
        this.expGenC3D.generateConditionCuartetas(
                admiMemory, internalCuartetas, temporals, pass, pass, condition, trueLabel
        );
        internalCuartetas.add(
                new GotoC3D(falseLabel)
        );
        internalCuartetas.add(
                new LabelC3D(trueLabel)
        );
        stmtsGeneratorC3D.generateInternalCuartetas(
                admiMemory, internalCuartetas, temporals, pass, internalStatements
        );
        internalCuartetas.add(
                new GotoC3D(endLabel)
        );
        internalCuartetas.add(
                new LabelC3D(falseLabel)
        );
        
        //generate elifs
        C3Dpass newPass = new C3Dpass(
                pass.getEndLabel(), 
                pass.getStartBucleLabel(), 
                endLabel
        );
        newPass.setEndMethod(pass.getEndMethod());
        
        if(elifs != null && !elifs.isEmpty()){
            for (ElifGenerateC3D elif : elifs) {
                elif.generateElifCuartetas(admiMemory, internalCuartetas, temporals, newPass);
            }
        }
        //generate else
        if(elseStmt != null){
            elseStmt.generateCuartetas(admiMemory, internalCuartetas, temporals, newPass);
        }
        
        internalCuartetas.add(
                new LabelC3D(endLabel)
        );
    }
    
    public void generateElifCuartetas(
            AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, 
            Memory temporals, 
            C3Dpass pass, 
            ExpressionGenerateC3D condition, 
            List<? extends StmtGenerateC3D> internalStmts
    ){
        int trueLabel = admiMemory.getCountLabels();
        int falseLabel = trueLabel + 1;
        admiMemory.setCountLabels(admiMemory.getCountLabels() + 2);
        
        this.expGenC3D.generateConditionCuartetas(
                admiMemory, internalCuartetas, temporals, pass, pass, condition, trueLabel
        );
        internalCuartetas.add(
                new GotoC3D(falseLabel)
        );
        internalCuartetas.add(
                new LabelC3D(trueLabel)
        );
        stmtsGeneratorC3D.generateInternalCuartetas(admiMemory, internalCuartetas, temporals, pass, internalStmts);
        internalCuartetas.add(
                new GotoC3D(pass.getEndIfLabel())
        );
        internalCuartetas.add(
                new LabelC3D(falseLabel)
        );
    }
    
    
}
