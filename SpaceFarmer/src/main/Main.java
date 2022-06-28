/**
 * @author c0nD
 * @version 1.0.0 BETA -- Do not change version number until 'release'
 * 
 * This passion-project is a 2D action-based RPG. 
 * Everything uses default java classes, and it can be run on
 * anything that uses JVM.
 * 
 * This will be the only page using JavaDocs documentation due to the scale
 * and nuanced complexity of creating a game from scratch.
 */

package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // Setting up the GUI/Interface
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Space Farm");
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

}
