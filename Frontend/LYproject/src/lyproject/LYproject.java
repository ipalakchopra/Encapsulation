/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lyproject;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author arvin
 */
public class LYproject {
    
    
    /**
     * @param args the command line arguments
     */
    
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        Login LoginFrame=new Login(); //instance of login frame
        LoginFrame.setVisible(true);
        LoginFrame.pack();//resize to contain all components
        LoginFrame.setLocationRelativeTo(null);// makes it center
        
    }
    
}
