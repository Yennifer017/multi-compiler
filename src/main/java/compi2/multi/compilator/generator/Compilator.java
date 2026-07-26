
package compi2.multi.compilator.generator;

import compi2.multi.compilator.exceptions.CompilationException;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author blue-dragon
 */
public class Compilator {
    
    public void compilateNASM(String pathFile, String workerDirectory, String nameProcessedFile) 
        throws InterruptedException, IOException, CompilationException {
    
        String objectFile = nameProcessedFile + ".o";
        ProcessBuilder nasmBuilder = new ProcessBuilder(
                "nasm", "-f", "elf64", pathFile, "-o", objectFile);
        nasmBuilder.redirectErrorStream(true); 

        Process nasmProcess = nasmBuilder.start();
        if (nasmProcess.waitFor() != 0) {
            throw new CompilationException("Error en la etapa de ensamblado (nasm)");
        }

        ProcessBuilder ldBuilder = new ProcessBuilder(
                "ld", objectFile, "-o", nameProcessedFile);
        ldBuilder.directory(new File(workerDirectory));
        ldBuilder.redirectErrorStream(true);

        Process ldProcess = ldBuilder.start();
        if (ldProcess.waitFor() != 0) {
            throw new CompilationException("Error en la etapa de enlazado (ld)");
        }
    }
    
    public void compilateCPP(String pathFile, String workerDirectory, String nameProcessedFile) 
            throws InterruptedException, IOException, CompilationException{
        ProcessBuilder processBuilder = new ProcessBuilder(
                "g++", pathFile, "-o", nameProcessedFile, "-std=c++23");

        processBuilder.directory(new File(workerDirectory));
        
        Process process = processBuilder.start();

        int exitCode = process.waitFor();
        if(exitCode != 0){
            throw new CompilationException();
        }
    }
    
    public void executeBinary(String pathFile) 
            throws IOException, InterruptedException, CompilationException{
        ProcessBuilder processBuilder = getCommandToExecuteBin(pathFile);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        if(exitCode != 0){
            throw new CompilationException();
        }
    }
    
    private ProcessBuilder getCommandToExecuteBin(String pathFile){
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            return new ProcessBuilder("cmd.exe", "/c", pathFile);
        } else if (osName.toLowerCase().contains("macos")){
            return new ProcessBuilder("open", "-a", "Terminal", pathFile);
        } else {
            return new ProcessBuilder( "gnome-terminal", "--", pathFile);
        }
    }
    
}
