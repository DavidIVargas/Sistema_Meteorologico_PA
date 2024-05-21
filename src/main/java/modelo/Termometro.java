/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author davidvargas
 */
public class Termometro implements Subject{
    
    private List<Observer> observers;
    private int temperatura;
    //Constructor
    public Termometro() {
        this.observers = new ArrayList<>();
    }
    
    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperatura);
        }
    }
    
    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
        notifyObservers();
    }

    public int getTemperatura() {
        return temperatura;
    }
    
    public List<Observer> getObservers() {
        return observers;
    }
}
