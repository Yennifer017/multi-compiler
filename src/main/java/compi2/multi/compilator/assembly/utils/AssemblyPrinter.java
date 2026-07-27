/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi2.multi.compilator.assembly.utils;

import compi2.multi.compilator.assembly.config.AssemblyFuncsName;

/**
 *
 * @author blue-dragon
 */
public class AssemblyPrinter {

    /**
     * @param nasmValue el nombre de la variable en nasm
     * @return Codigo para invocar la llamada a imprimir un entero
     */
    public static String getPrintIntCode(String nasmValue) {
        return String.format("""
                                 mov rax, %s
                                 call %s
                                 
                                 """,
                nasmValue,
                AssemblyFuncsName.PRINT_INTEGER_FUNCT_NAME
        );
    }
}
