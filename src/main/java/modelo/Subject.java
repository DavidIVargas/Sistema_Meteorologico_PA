/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author davidvargas
 */
public interface Subject {    
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();   
}
