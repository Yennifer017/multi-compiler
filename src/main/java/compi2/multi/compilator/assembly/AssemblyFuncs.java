/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi2.multi.compilator.assembly;

import compi2.multi.compilator.assembly.config.AssemblyFuncsName;
import compi2.multi.compilator.assembly.interfaces.AssemblyTransformable;

/**
 *
 * @author blue-dragon
 */
public class AssemblyFuncs implements AssemblyTransformable{
    
    @Override
    public void generateAssemblyNasmCode(StringBuilder builder, AssemblyComps ac) {
        
        builder.append("""
                       ; ============================
                       ; Functions
                       ; ============================
                       """);
        
        builder.append(String.format("""
                       %s:
                       
                           mov rax, 1
                           mov rdi, 1
                           mov rsi, newline
                           mov rdx, 1
                       
                           syscall
                       
                           ret
                                     
                                     """, AssemblyFuncsName.PRINT_LN_FUNCT_NAME));
        
        builder.append(String.format("""
                      
                       %s:
                       
                           mov rax, 1          ; syscall write
                           mov rdi, 1          ; stdout
                       
                           syscall
                       
                           ret
                                     
                       """, AssemblyFuncsName.PRINT_STRING_FUNCT_NAME));
        builder.append(String.format("""
                       print_int:
                           sub rsp, 32         ; Mover el sp para tener 32 bits
                           lea rsi, [rsp + 20]
                           mov rcx,0
                       .loop:
                           xor rdx,rdx
                           mov r10,10
                           div r10
                           add dl,'0'
                           dec rsi             ; mover el puntero para atras
                           mov [rsi],dl
                           inc rcx
                           test rax,rax
                           jnz .loop
                           mov rax,1           ; fragmento para imprimir
                           mov rdi,1
                           mov rdx,rcx
                           syscall
                       
                           add rsp, 32         ; Devolver el sp a su posicion original
                           ret
                                     """, AssemblyFuncsName.PRINT_INTEGER_FUNCT_NAME));
    }
    
}
