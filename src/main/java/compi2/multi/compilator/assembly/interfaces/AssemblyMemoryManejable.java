/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package compi2.multi.compilator.assembly.interfaces;

/**
 *
 * @author blue-dragon
 */
public interface AssemblyMemoryManejable {
    public String getReserveMemoryNasmCode();
    public String getFreeMemoryNasmCode(boolean withReturn);
    public int getMemorySize();
}
