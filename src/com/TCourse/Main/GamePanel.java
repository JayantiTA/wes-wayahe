// Drawing canvas
// contains game loop
package com.TCourse.Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.TCourse.Manager.GameStateManager;
import com.TCourse.Manager.Keys;

public class GamePanel extends JPanel implements Runnable, KeyListener {  

  // dimensions
	// HEIGHT is the playing area size
	// HEIGHT_TOTAL includes a bar on the bottom window
  public static final int WIDTH = 128;
  public static final int HEIGHT = 128;
  public static final int HEIGHT_TOTAL = HEIGHT + 16;
  public static final int SCALE = 3;

  // game loop stuff
  private Thread thread;
  private boolean isRunning;
  private final int FPS = 30;
  private final int TARGET_TIME = 1000/FPS;

  // drawing stuff
  private BufferedImage image;
  private Graphics2D g;
  
  // game state manager
  private GameStateManager gsm;

  // the constructor
  public GamePanel() {
    setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT_TOTAL * SCALE));
    setFocusable(true);
    requestFocus();
  }

  // read to display
  public void addNotify() {

    super.addNotify();

    if (thread == null) {
      addKeyListener(this);
      thread = new Thread(this);
      thread.start();
    }

  }

  // run the new thread
  public void run() {

    init();

    long start;
    long elapsed;
    long wait;
    
    // game loop
    while (isRunning) {
      
      start = System.nanoTime();
      
      update();
      draw();
      drawToScreen();
      
      elapsed = System.nanoTime() - start;
      
      wait = TARGET_TIME - elapsed / 1000000;
      if (wait < 0)
        wait = TARGET_TIME;
      
      try {
        Thread.sleep(wait);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      
    }

  }

  // initialize fileds
  private void init() {
    isRunning = true;
    image = new BufferedImage(WIDTH, HEIGHT_TOTAL, 1);
    g = (Graphics2D) image.getGraphics();
    gsm = new GameStateManager();
  }
  
  // updates game
  private void update() {
    gsm.update();
    Keys.update();
  }
  
  // draws game
  private void draw() {
    gsm.draw(g);
  }
  
  // copy buffer to screen
  private void drawToScreen() {
    Graphics g2 = getGraphics();
    g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT_TOTAL * SCALE, null);
    g2.dispose();
  }
  
  // implements key event methods
  public void keyTyped(KeyEvent key) {}
  public void keyPressed(KeyEvent key) {
    Keys.keySet(key.getKeyCode(), true);
  }
  public void keyReleased(KeyEvent key) {
    Keys.keySet(key.getKeyCode(), false);
  }
  
}
