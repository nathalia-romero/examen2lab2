/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen2lab2;
import java.io.*;
import java.util.Date;

/**
 *
 * @author nathalia.romero
 */

public class PSNUsers {
    private static final String FILE_PATH = "psn.dat";
    private static final String TROPHIES_FILE_PATH = "psn_trophies.txt";

    private RandomAccessFile raf;
    private HashTable users;

    public PSNUsers() {
        try {
            this.raf = new RandomAccessFile(FILE_PATH, "rw");
            this.users = new HashTable();
            reloadHashTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reloadHashTable() {
        try {
            raf.seek(0);

            while (raf.getFilePointer() < raf.length()) {
                String username = raf.readUTF();
                long pos = raf.getFilePointer();
                raf.readLong(); 
                raf.readInt();  
                boolean activated = raf.readBoolean();

                if (activated) {
                    users.add(username, pos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String username) {
        try {
            if (users.search(username) != -1) {
                System.out.println("User already exists: " + username);
                return;
            }

            raf.seek(raf.length());

            raf.writeUTF(username);
            raf.writeLong(0); 
            raf.writeInt(0);  
            raf.writeBoolean(true);  

            long pos = raf.getFilePointer();
            users.add(username, pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deactivateUser(String username) {
        long pos = users.search(username);
        if (pos != -1) {
            try {
                raf.seek(pos + Entry.USERNAME_SIZE); 
                raf.writeBoolean(false); 

                users.remove(username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addTrophieTo(String username, String trophyGame, String trophyName, Trophy type) {
        long pos = users.search(username);
        if (pos != -1) {
            try {
                
                raf.seek(pos + Entry.USERNAME_SIZE); 
                long points = raf.readLong();
                int trophyCount = raf.readInt();
                raf.seek(pos + Entry.USERNAME_SIZE); 

                points += type.points;
                trophyCount++;

                raf.writeLong(points);
                raf.writeInt(trophyCount);
                raf.writeBoolean(true);

                try (RandomAccessFile trophiesRaf = new RandomAccessFile(TROPHIES_FILE_PATH, "rw")) {
                    trophiesRaf.seek(trophiesRaf.length());
                    trophiesRaf.writeLong(pos);
                    trophiesRaf.writeUTF(type.name());
                    trophiesRaf.writeUTF(trophyGame);
                    trophiesRaf.writeUTF(trophyName);
                    trophiesRaf.writeUTF(new Date().toString()); 
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void playerInfo(String username) {
        long pos = users.search(username);
        if (pos != -1) {
            try {
                raf.seek(pos);
                String storedUsername = raf.readUTF();
                long points = raf.readLong();
                int trophyCount = raf.readInt();
                boolean activated = raf.readBoolean();

                System.out.println("Username: " + storedUsername);
                System.out.println("Points: " + points);
                System.out.println("Trophy Count: " + trophyCount);
                System.out.println("Activated: " + activated);

                try (RandomAccessFile trophiesRaf = new RandomAccessFile(TROPHIES_FILE_PATH, "r")) {
                    trophiesRaf.seek(0);
                    while (trophiesRaf.getFilePointer() < trophiesRaf.length()) {
                        long userPos = trophiesRaf.readLong();
                        if (userPos == pos) {
                            String trophyType = trophiesRaf.readUTF();
                            String trophyGame = trophiesRaf.readUTF();
                            String trophyName = trophiesRaf.readUTF();
                            String trophyDate = trophiesRaf.readUTF();

                            System.out.println("Trophy: " + trophyDate + " - " + trophyType + " - " + trophyGame + " - " + trophyName);
                        } else {
                            trophiesRaf.skipBytes(28); 
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User not found: " + username);
        }
    }

    public static void main(String[] args) {
        PSNUsers psnUsers = new PSNUsers();

        psnUsers.addUser("user1");
        psnUsers.addUser("user2");
        psnUsers.addUser("user3");
                
        psnUsers.deactivateUser("user2");

        psnUsers.addTrophieTo("user1", "Game1", "Trophy1", Trophy.PLATINO);
        psnUsers.addTrophieTo("user1", "Game2", "Trophy2", Trophy.ORO);
        psnUsers.addTrophieTo("user3", "Game3", "Trophy3", Trophy.PLATA);
        
        psnUsers.playerInfo("user1");
        psnUsers.playerInfo("user2"); 
        psnUsers.playerInfo("user3");
    }
}
