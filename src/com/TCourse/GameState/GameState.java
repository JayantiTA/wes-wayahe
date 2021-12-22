// Super class for all GameState subclasses
// reference to GameStateManager class
// has 4 methods that must be overriden
package com.TCourse.GameState;

import java.awt.Graphics2D;

import com.TCourse.Manager.GameStateManager;

public abstract class GameState {

  protected GameStateManager gsm;
  
  public GameState(GameStateManager gsm) {
    this.gsm = gsm;
  }
  
  public abstract void init();
  public abstract void update();
  public abstract void draw(Graphics2D g);
  public abstract void handleInput();
  
}
