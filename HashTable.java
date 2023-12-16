/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2lab2;

/**
 *
 * @author nathalia.romero
 */
public class HashTable {
    private Entry head;
    
public HashTable(){
    this.head=null;
}

public void add(String username, long pos){
    Entry newEntry=new Entry(username, pos);
    
    if(head==null){
        head=newEntry;
    }else{
        Entry current=head;
        while(current.getNext() !=null){
            current=current.getNext();
        }
        current.setNext(newEntry);
    }
}

public void remove(String username){
    if(head==null){
        return;
    }
    
    if(head.getUsername().equals(username)){
        head=head.getNext();
        return;
    }
    Entry current=head;
    Entry previous=null;
    
    while(current!=null && !current.getUsername().equals(username)){
        previous=current;
        current=current.getNext();
    }
    
    if(current!=null){
        previous.setNext(current.getNext());
    }
}

public long search(String username){
    Entry current=head;
    
    while(current!=null){
      if (current.getUsername().equals(username)) {
                return current.getPos();
            }
            current = current.getNext();
        }

        return -1;
}

}