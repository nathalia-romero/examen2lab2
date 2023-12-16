/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2lab2;

/**
 *
 * @author nathalia.romero
 */
public class Entry {

    static long USERNAME_SIZE;
    private String username;
    private long pos;
    private Entry next;
    
 public Entry(String username, long pos){
     this.username=username;
     this.pos=pos;
     this.next=null;
 }
 
 public String getUsername(){
     return username;
 }
 
 public long getPos(){
     return pos;
 }
 
 public Entry getNext(){
     return next;
 }
 
 public void setNext(Entry next){
     this.next=next;
 }
 
 }
    /*

// Enumeración Trophy
public enum Trophy {
    PLATINO(5),
    ORO(3),
    PLATA(2),
    BRONCE(1);

    public final int points;

    Trophy(int points) {
        this.points = points;
    }
}

    */

