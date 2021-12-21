package com.TCourse.GameState;

import java.awt.Graphics2D;

import com.TCourse.Manager.Content;
import com.TCourse.Manager.GameStateManager;
import com.TCourse.Manager.JukeBox;
import com.TCourse.Manager.Keys;

public class PauseState extends GameState {
  
  public PauseState(GameStateManager gsm) {
    super(gsm);
  }

  public void init() {}

  public void update() {
    handleInput();
  }

  public void draw(Graphics2D g) {
    
    Content.drawString(g, "paused", 40, 20);
    
    Content.drawString(g, "arrow", 12, 56);
    Content.drawString(g, "keys", 16, 64);
    Content.drawString(g, ": move", 52, 60);
    
    Content.drawString(g, "space", 12, 76);
    Content.drawString(g, ": action", 52, 76);
    
    Content.drawString(g, "F1:", 36, 92);
    Content.drawString(g, "return", 68, 88);
    Content.drawString(g, "to menu", 68, 96);
    
  }
  public void handleInput() {
    if (Keys.isPressed(Keys.ESCAPE)) {
      JukeBox.play("press_key");
      gsm.setPaused(false);
    }
    if (Keys.isPressed(Keys.F1)) {
      JukeBox.play("press_key");
      gsm.setPaused(false);
      gsm.setState(GameStateManager.MENU);
    }
  }

}
