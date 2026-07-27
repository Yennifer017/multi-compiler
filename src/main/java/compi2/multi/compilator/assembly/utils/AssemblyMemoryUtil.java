/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi2.multi.compilator.assembly.utils;

/**
 *
 * @author blue-dragon
 */
public class AssemblyMemoryUtil {
    
    public static int getAlignedMemorySize(int countInBytes){
        int size = countInBytes * 4;

        int remainder = size % 16;

        if(remainder != 0){
            size += 16 - remainder;
        }

        return size;
    }
}
