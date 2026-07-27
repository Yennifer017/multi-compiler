/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi2.multi.compilator.optimization.interfaces;

import compi2.multi.compilator.c3d.access.MemoryAccess;
import java.util.List;

/**
 *
 * @author blue-dragon
 */
public interface Optimizable {
    List<MemoryAccess> getUses();

    MemoryAccess getDefinition();

    void replaceUse(MemoryAccess oldOp, MemoryAccess newOp);

}
