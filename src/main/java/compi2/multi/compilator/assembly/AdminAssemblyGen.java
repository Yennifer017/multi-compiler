/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi2.multi.compilator.assembly;

import compi2.multi.compilator.assembly.interfaces.AssemblyPreparable;
import compi2.multi.compilator.c3d.Cuarteta;
import compi2.multi.compilator.exceptions.CompilationException;
import compi2.multi.compilator.files.UtilForFiles;
import compi2.multi.compilator.generator.Compilator;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public class AdminAssemblyGen {

    private List<Cuarteta> cuartetas;
    private AssemblyFuncs assemblyFuncs;

    private UtilForFiles utilForFiles;
    private Compilator compilator;

    public AdminAssemblyGen(List<Cuarteta> cuartetas) {
        this.cuartetas = cuartetas;
        this.assemblyFuncs = new AssemblyFuncs();
        this.utilForFiles = new UtilForFiles();
        this.compilator = new Compilator();
    }

    private String generateAssemblyNasmCode() {
        StringBuilder builder = new StringBuilder();
        AssemblyComps ac = new AssemblyComps();

        if (cuartetas != null) {
            for (Cuarteta cuarteta : cuartetas) {
                if (cuarteta instanceof AssemblyPreparable ap) {
                    ap.prepareForAssembly(ac);
                }
            }
            generateRoadDataCode(builder, ac);
            generateMainSectionCode(builder);
            assemblyFuncs.generateAssemblyNasmCode(builder, ac);
            for (Cuarteta cuarteta : cuartetas) {
                cuarteta.generateAssemblyNasmCode(builder, ac);
            }
        }

        return builder.toString();
    }

    private void generateRoadDataCode(StringBuilder builder, AssemblyComps ac) {
        builder.append("""
                           section .rodata
                           
                           ; ============================
                           ; Tabla de literales
                           ; ============================
                           
                           newline:
                               db 10
                           
                           """);

        ac.getLiteralPool().generateAssemblyNasmCode(builder, ac);
    }

    private void generateMainSectionCode(StringBuilder builder) {
        builder.append("""
                           section .text
                           
                           global _start
                           """);
    }

    public void compilate() throws InterruptedException, IOException, CompilationException {
        String code = this.generateAssemblyNasmCode();
        utilForFiles.saveFile(code, new File("./codigoNASM.nasm"));
        compilator.compilateNASM("./codigoNASM.nasm", "./", "NASM");
        compilator.executeBinary("./NASM");
    }

}
