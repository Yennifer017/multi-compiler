/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi2.multi.compilator.assembly;

import compi2.multi.compilator.assembly.interfaces.AssemblyTransformable;

/**
 *
 * @author blue-dragon
 */
public class AssemblyFuncs implements AssemblyTransformable{
    
    @Override
    public void generateAssemblyNasmCode(StringBuilder builder, AssemblyComps ac) {
        builder.append(String.format("""
                       ; ============================
                       ; Functions
                       ; ============================
                       
                       %s:
                       
                           mov rax, 1          ; syscall write
                           mov rdi, 1          ; stdout
                       
                           syscall
                       
                           ret
                                     
                       """, AssemblyConf.PRINT_STRING_FUNCT_NAME));
    }
    
}
