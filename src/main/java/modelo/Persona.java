/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author davidvargas
 */
public class Persona implements Observer{
    
    private String nombre;
    //CONSTRUCTOR
    public Persona(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public void update(int temperatura) {
         String color;
        if (temperatura <= 50) {
            color = "verde";
        } else if (temperatura <= 80) {
            color = "naranja";
        } else {
            color = "rojo";
        }
    }
    
    public String getNombre() {
        return nombre;
    }
}
