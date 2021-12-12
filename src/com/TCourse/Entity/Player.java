package com.TCourse.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.TCourse.Manager.Content;
import com.TCourse.TileMap.TileMap;

public class Player extends Entity {
  
  // sprites
  private BufferedImage[] downSprites;
  private BufferedImage[] leftSprites;
  private BufferedImage[] rightSprites;
  private BufferedImage[] upSprites;
  private BufferedImage[] downBoatSprites;
  private BufferedImage[] leftBoatSprites;
  private BufferedImage[] rightBoatSprites;
  private BufferedImage[] upBoatSprites;
  
  // animation
  private final int DOWN = 0;
  private final int LEFT = 1;
  private final int RIGHT = 2;
  private final int UP = 3;
  private final int DOWNBOAT = 4;
  private final int LEFTBOAT = 5;
  private final int RIGHTBOAT = 6;
  private final int UPBOAT = 7;
  
  // gameplay
  private int numDiamonds;
  private int totalDiamonds;
  private int numCourses;
  private int totalCourses;
  private int totalSks;
  private boolean hasBoat;
  private boolean hasAxe;
  private boolean onWater;
  private long ticks;
  
  public Player(TileMap tm) {
    
    super(tm);
    
    width = 16;
    height = 16;
    cWidth = 12;
    cHeight = 12;
    
    moveSpeed = 2;
    
    numDiamonds = 0;
    numCourses = 0;
    
    downSprites = Content.PLAYER[0];
    leftSprites = Content.PLAYER[1];
    rightSprites = Content.PLAYER[2];
    upSprites = Content.PLAYER[3];
    downBoatSprites = Content.PLAYER[4];
    leftBoatSprites = Content.PLAYER[5];
    rightBoatSprites = Content.PLAYER[6];
    upBoatSprites = Content.PLAYER[7];
    
    animation.setFrames(downSprites);
    animation.setDelay(10);
  
  }
  
  private void setAnimation(int i, BufferedImage[] bi, int d) {
    currentAnimation = i;
    animation.setFrames(bi);
    animation.setDelay(d);
  }
  
  public void collectedDiamond() { numDiamonds++; }
  public int numDiamonds() { return numDiamonds; }
  public int getTotalDiamonds() { return totalDiamonds; }
  public void setTotalDiamonds(int i) { totalDiamonds = i; }
  
  public void collectedCourse(Course.ListCourses c) { 
    if (c == Course.ListCourses.PROBSTAT) {
      if (!Course.ListCourses.MATDIS.finishCourse()) {
        return;
      }
    }
    
    numCourses++;
    totalSks += c.getSks();
  }
  public int numCourses() { return numCourses; }
  public int getTotalCourses() { return totalCourses; }
  public int getTotalSks() { return totalSks; }
  public void gotCourse(Course.ListCourses c) { 
    c.setCourse(true);
    tileMap.replace(22, 4);
  }
  
  public void gotBoat() { hasBoat = true; tileMap.replace(22, 4); }
  public void gotAxe() { hasAxe = true; }
  public boolean hasBoat() { return hasBoat; }
  public boolean hasAxe() { return hasAxe; }
  
  public long getTicks() { return ticks; }
  
  public void setDown() {
    super.setDown();
  }
  public void setLeft() {
    super.setLeft();
  }
  public void setRight() {
    super.setRight();
  }
  public void setUp() {
    super.setUp();
  }
  
  public void setAction() {
    if (hasAxe) {
      if (currentAnimation == UP && tileMap.getIndex(rowTile - 1, colTile) == 21) {
        tileMap.setTile(rowTile - 1, colTile, 1);
      }
      if (currentAnimation == DOWN && tileMap.getIndex(rowTile + 1, colTile) == 21) {
        tileMap.setTile(rowTile + 1, colTile, 1);
      }
      if (currentAnimation == LEFT && tileMap.getIndex(rowTile, colTile - 1) == 21) {
        tileMap.setTile(rowTile, colTile - 1, 1);
      }
      if (currentAnimation == RIGHT && tileMap.getIndex(rowTile, colTile + 1) == 21) {
        tileMap.setTile(rowTile, colTile + 1, 1);
      }
    }
  }
  
  public void update() {
    
    ticks++;
    
    boolean current = onWater;
    if (tileMap.getIndex(yDest / tileSize, xDest / tileSize) == 4) {
      onWater = true;
    }
    else {
      onWater = false;
    }
    if (!current && onWater) {
    }
    
    if (down) {
      if (onWater && currentAnimation != DOWNBOAT) {
        setAnimation(DOWNBOAT, downBoatSprites, 10);
      }
      else if (!onWater && currentAnimation != DOWN) {
        setAnimation(DOWN, downSprites, 10);
      }
    }
    if (left) {
      if (onWater && currentAnimation != LEFTBOAT) {
        setAnimation(LEFTBOAT, leftBoatSprites, 10);
      }
      else if (!onWater && currentAnimation != LEFT) {
        setAnimation(LEFT, leftSprites, 10);
      }
    }
    if (right) {
      if (onWater && currentAnimation != RIGHTBOAT) {
        setAnimation(RIGHTBOAT, rightBoatSprites, 10);
      }
      else if (!onWater && currentAnimation != RIGHT) {
        setAnimation(RIGHT, rightSprites, 10);
      }
    }
    if (up) {
      if (onWater && currentAnimation != UPBOAT) {
        setAnimation(UPBOAT, upBoatSprites, 10);
      }
      else if (!onWater && currentAnimation != UP) {
        setAnimation(UP, upSprites, 10);
      }
    }
    
    super.update();
    
  }
  
  public void draw(Graphics2D g) {
    super.draw(g);
  }
  
}
