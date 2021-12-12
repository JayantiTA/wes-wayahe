package com.TCourse.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.TCourse.Manager.Content;
import com.TCourse.TileMap.TileMap;

public class Book extends Entity {

  BufferedImage[] sprites;

  private ArrayList<int[]> tileChanges;
  
  public Book(TileMap tm) {
    
    super(tm);
    width = height = 16;
    cWidth = cHeight = 12;

    sprites = Content.BOOK[0];
    animation.setFrames(sprites);
    animation.setDelay(10);
    
    tileChanges = new ArrayList<int[]>();

  }
  
  public void addChange(int[] i) {
    tileChanges.add(i);
  }

  public ArrayList<int[]> getChanges() {
    return tileChanges;
  }
  
  public void update() {
    animation.update();
  }
  
  public void draw(Graphics2D g) {
    super.draw(g);
  }
  
}
