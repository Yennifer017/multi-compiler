
package compi2.multi.compilator.analyzator;

import compi2.multi.compilator.c3d.AdmiMemory;
import compi2.multi.compilator.exceptions.CompilationException;
import compi2.multi.compilator.files.UtilForFiles;
import compi2.multi.compilator.generator.Compilator;
import compi2.multi.compilator.semantic.DefAst;
import compi2.multi.compilator.semantic.c.CMain;
import compi2.multi.compilator.semantic.jclases.JClass;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class GeneratorC3D {
    
    private UtilForFiles utilForFiles;
    private Compilator compilator;
    public GeneratorC3D(){
        this.utilForFiles = new UtilForFiles();
        this.compilator = new Compilator();
    }
    
    public void generateJavaC3D(List<JClass> clases, AdmiMemory admiMemory){
        if(clases != null && !clases.isEmpty()){
            for (JClass clase : clases) {
                clase.generateCuartetas(admiMemory);
            }
        }
    }
    
    public void generateMainC3D(CMain cmain, AdmiMemory admiMemory){
        
    }
    
    public void generatePascalC3D(List<DefAst> pascalFunctions, AdmiMemory admiMemory){
        if(pascalFunctions != null && !pascalFunctions.isEmpty()){
            
        }
    }
    
    public void compilate(AdmiMemory admiMemory) 
            throws InterruptedException, IOException, CompilationException{
        StringBuilder builder = new StringBuilder();
        admiMemory.generateCcode(builder);
        utilForFiles.saveFile(builder.toString(), new File("./codigo3direcciones.cpp"));
        compilator.compilateCPP("./codigo3direcciones.cpp", "./", "c3d.o");
    }
}
