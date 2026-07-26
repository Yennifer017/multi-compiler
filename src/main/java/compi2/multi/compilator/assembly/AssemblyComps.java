/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi2.multi.compilator.assembly;

import compi2.multi.compilator.assembly.components.LiteralPool;
import lombok.Getter;

/**
 *
 * @author blue-dragon
 */
@Getter
public class AssemblyComps {
    
    private LiteralPool literalPool;
    
    public AssemblyComps(){
        literalPool = new LiteralPool();
    }
}
