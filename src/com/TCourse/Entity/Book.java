package com.TCourse.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.TCourse.Manager.Content;
import com.TCourse.TileMap.TileMap;

public class Book extends Entity {

  BufferedImage[] sprites;

  private ArrayList<int[]> tileChanges;

  private String courseName;

  private String[] twoCredits = new String[] {"BIN", "PANCASILA", "BIG", "KWN"};
  private String[] threeCredits = new String[] {"MAT 1", "KIM", "MAT 2", "FIS 2", "STRUKDAT", "SISDIG", "MATDIS", "ALIN", "KOMNUM", "PBO", "ORKOM"};
  private String[] fourCredits = new String[] {"DASPROG", "FIS 1", "SBD"};
  
  public Book(TileMap tm, String s) {
    
    super(tm);
    courseName = s;
    width = height = 16;
    cWidth = cHeight = 12;

    sprites = Content.BOOK[0];
    animation.setFrames(sprites);
    animation.setDelay(10);
    
    tileChanges = new ArrayList<int[]>();

  }

  public boolean twoCredits() {
    for (int i = 0; i < twoCredits.length; i++) {
      if (courseName.equals(twoCredits[i])) return true;
    }
    return false;
  }

  public boolean threeCredits() {
    for (int i = 0; i < threeCredits.length; i++) {
      if (courseName.equals(threeCredits[i])) return true;
    }
    return false;
  }

  public boolean fourCredits() {
    for (int i = 0; i < fourCredits.length; i++) {
      if (courseName.equals(fourCredits[i])) return true;
    }
    return false;
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

  public String getCourseName() {
    return courseName;
  }
  
  public void draw(Graphics2D g) {
    super.draw(g);
  }
  
}
