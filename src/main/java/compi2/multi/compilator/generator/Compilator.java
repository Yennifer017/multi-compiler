
package compi2.multi.compilator.generator;

import compi2.multi.compilator.exceptions.CompilationException;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author blue-dragon
 */
public class Compilator {
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
