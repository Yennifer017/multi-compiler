
package compi2.multi.compilator.c3d.generators.stmts;

import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.c3d.Memory;
import compi2.multi.compilator.c3d.cuartetas.GotoC3D;
import compi2.multi.compilator.c3d.cuartetas.LabelC3D;
import compi2.multi.compilator.c3d.generators.ExpGenC3D;
import compi2.multi.compilator.c3d.interfaces.ExpressionGenerateC3D;
import compi2.multi.compilator.c3d.interfaces.StmtGenerateC3D;
import compi2.multi.compilator.c3d.util.C3Dpass;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class CyclesStmtsGenC3D {
    
    private ExpGenC3D expGenC3D;
    private StmtsGeneratorC3D stmtsGeneratorC3D;
    
    public CyclesStmtsGenC3D(){
        this.expGenC3D = new ExpGenC3D();
        stmtsGeneratorC3D = new StmtsGeneratorC3D();
    }
    
    public void generateDoWhileCuartetas(
            AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, 
            Memory temporals, 
            C3Dpass pass,
            List<? extends StmtGenerateC3D> internalStmts, 
            ExpressionGenerateC3D condition
    ){
        int firstLabel = admiMemory.getCountLabels();
        int finalLabel = firstLabel + 1;
        admiMemory.setCountLabels(admiMemory.getCountLabels() + 2);
        
        internalCuartetas.add(
                new LabelC3D(firstLabel)
        );
        
        C3Dpass passInternal = new C3Dpass(finalLabel, firstLabel);
        passInternal.setEndIfLabel(pass.getEndIfLabel());
        passInternal.setEndMethod(pass.getEndMethod());
        
        stmtsGeneratorC3D.generateInternalCuartetas(
                admiMemory, internalCuartetas, temporals, pass, internalStmts
        );
        
        expGenC3D.generateConditionCuartetas(
                admiMemory, internalCuartetas, temporals, pass, passInternal, condition, firstLabel
        );
        
        internalCuartetas.add(
                new GotoC3D(finalLabel)
        );
        internalCuartetas.add(
                new LabelC3D(finalLabel)
        );
    }
    
    public void generateWhileCuartetas(
            AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, 
            Memory temporals, 
            C3Dpass pass,
            List<? extends StmtGenerateC3D> internalStmts, 
            ExpressionGenerateC3D condition
    ){
        int firstLabel = admiMemory.getCountLabels();
        int trueLabel = firstLabel + 1;
        int falseLabel = firstLabel + 2;
        admiMemory.setCountLabels(admiMemory.getCountLabels() + 3);
        
        C3Dpass internalPass = new C3Dpass(falseLabel,firstLabel);
        internalPass.setEndIfLabel(pass.getEndIfLabel());
        internalPass.setEndMethod(pass.getEndMethod());
        
        internalCuartetas.add(
                new LabelC3D(firstLabel)
        );
        expGenC3D.generateConditionCuartetas(
                admiMemory, internalCuartetas, temporals, pass, internalPass, condition, trueLabel
        );
        internalCuartetas.add(
                new GotoC3D(falseLabel)
        );
        internalCuartetas.add(
                new LabelC3D(trueLabel)
        );
        stmtsGeneratorC3D.generateInternalCuartetas(
                admiMemory, internalCuartetas, temporals, internalPass, internalStmts
        );
        internalCuartetas.add(
                new GotoC3D(firstLabel)
        );
        internalCuartetas.add(
                new LabelC3D(falseLabel)
        );
    }
    
    public void generateForCuartetas(
            AdmiMemory admiMemory, 
            List<Cuarteta> internalCuartetas, 
            Memory temporals, 
            C3Dpass pass,
            List<? extends StmtGenerateC3D> internalStmts, 
            StmtGenerateC3D firstStmt,
            ExpressionGenerateC3D condition,
            StmtGenerateC3D lastStmt
    ){
        if(firstStmt != null){
            firstStmt.generateCuartetas(admiMemory, internalCuartetas, temporals, pass);
        }
        int etInicio = admiMemory.getCountLabels();
        int etTrue =  etInicio + 1;
        int etFalse = etInicio + 2;
        admiMemory.setCountLabels(admiMemory.getCountLabels() + 3);
        
        internalCuartetas.add(
                new LabelC3D(etInicio)
        );
        
        //validate condition
        C3Dpass passInternal = new C3Dpass(etFalse, etInicio);
        passInternal.setEndIfLabel(pass.getEndIfLabel());
        passInternal.setEndMethod(pass.getEndMethod());
        
        expGenC3D.generateConditionCuartetas(
                admiMemory, internalCuartetas, temporals, pass, passInternal, condition, etTrue
        );
        internalCuartetas.add(
                new GotoC3D(etFalse)
        );
        internalCuartetas.add(
                new LabelC3D(etTrue)
        );
        stmtsGeneratorC3D.generateInternalCuartetas(
                admiMemory, internalCuartetas, temporals, passInternal, internalStmts
        );
        if(lastStmt != null){
            lastStmt.generateCuartetas(admiMemory, internalCuartetas, temporals, pass);
        }
        internalCuartetas.add(
                new GotoC3D(etInicio)
        );
        internalCuartetas.add(
                new LabelC3D(etFalse)
        );
    }
    
    
}
