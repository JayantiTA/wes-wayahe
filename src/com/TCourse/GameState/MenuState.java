// MenuState before playing the game
package com.TCourse.GameState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import com.TCourse.Manager.Content;
import com.TCourse.Manager.GameStateManager;
import com.TCourse.Manager.JukeBox;
import com.TCourse.Manager.Keys;

public class MenuState extends GameState{
  
  private BufferedImage background;
  private BufferedImage book;
  
  private int currentOption = 0;
  
  private String[] options = {
    "START",
    "QUIT"
  };
  
  public MenuState(GameStateManager gsm) {
    super(gsm);
  }
  
  public void init() {
    try {
      background = Content.MENUBG[0][0];
      book = Content.BOOK1[0][0];
      JukeBox.load("/SFX/collect_book.wav", "collect");
      JukeBox.load("/SFX/menuoption.wav", "menuoption");  
      JukeBox.load("/Music/bgmusic_menu.mp3", "music_menu");
      JukeBox.setVolume("music_menu", -10);
      JukeBox.loop("music_menu", 1000, 1000, JukeBox.getFrames("music_menu") - 1000);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public void update() {
    handleInput();
  }
  
  public void draw(Graphics2D g) {
    
    g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
    
    Content.drawString(g, options[0], 48, 100);
    Content.drawString(g, options[1], 48, 110);
    
    if (currentOption == 0) g.drawImage(book, 25, 96, null);
    else if (currentOption == 1) g.drawImage(book, 25, 106, null);
    
  }
  
  public void handleInput() {
    if (Keys.isPressed(Keys.DOWN) && currentOption < options.length - 1) {
      JukeBox.play("menuoption");
      currentOption++;
    }
    if (Keys.isPressed(Keys.UP) && currentOption > 0) {
      JukeBox.play("menuoption");
      currentOption--;
    }
    if (Keys.isPressed(Keys.ENTER)) {
      JukeBox.play("collect");
      selectOption();
    }
  }
  
  private void selectOption() {
    if (currentOption == 0) {
      JukeBox.stop("music_menu");
      gsm.setState(GameStateManager.PLAY);
    }
    if (currentOption == 1) {
      System.exit(0);
    }
  }
  
}
