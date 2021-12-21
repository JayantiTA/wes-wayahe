package com.TCourse.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.TCourse.Manager.Content;
import com.TCourse.Manager.JukeBox;
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

  private String[] semester1 = new String[] {"BIN", "PANCASILA", "MAT 1", "FIS 1", "KIM", "DASPROG"};
  private String[] semester2 = new String[] {"BIG", "KWN", "MAT 2", "FIS 2", "AGAMA", "STRUKDAT", "SISDIG"};
  private String[] semester3 = new String[] {"ALIN", "KOMNUM", "ORKOM", "PBO", "SBD", "MATDIS"};
  private String[] semester4 = new String[] {"PAA", "PROBSTAT", "APSI", "KB", "MBD", "SISOP"};

  private String[] twoCredits = new String[] {"BIN", "PANCASILA", "BIG", "KWN", "AGAMA"};
  private String[] threeCredits = new String[] {"MAT 1", "KIM", "MAT 2", "FIS 2", "STRUKDAT", "SISDIG", "MATDIS", "ALIN", "KOMNUM", 
                                  "PBO", "ORKOM", "PROBSTAT", "APSI", "KB", "SISOP", "IMK", "TGO", "PBKK"};
  private String[] fourCredits = new String[] {"DASPROG", "FIS 1", "SBD", "PAA", "MBD"};

  
  // gameplay
  private int numDiamonds;
  private int totalDiamonds;

  private int numCourses;
  private int tempCredit;
  private int totalCourses;
  private int totalCredit;
  private ArrayList<String> courseTaken;

  private boolean hasAxe;
  private boolean hasPickaxe;
  private boolean hasBoat;
  private boolean hasKey;
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
    courseTaken = new ArrayList<String>();
    
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
  
  public void collectedCourse(int i) { numCourses++; tempCredit += i;}
  public int numCourses() { return numCourses; }
  public int currentCredit() { return tempCredit; }
  public int getTotalCourses() { return totalCourses; }
  public int getTotalCredit() { return totalCredit; }
  public ArrayList<String> getCourseTaken() { return courseTaken;}
  public void setTotalCourses(int i) { totalCourses = i; }
  public void setTotalCredit(int i) { totalCredit = i; }
  public void takeCourse(String s) { courseTaken.add(s); }

  public boolean courseInSemester2(String s) {
    for (int i = 0; i < semester2.length; i++) {
      if (s.equals(semester2[i])) return true;
    }
    return false;
  }

  public boolean courseInSemester3(String s) {
    for (int i = 0; i < semester3.length; i++) {
      if (s.equals(semester3[i])) return true;
    }
    return false;
  }
  
  public boolean courseInSemester4(String s) {
    for (int i = 0; i < semester4.length; i++) {
      if (s.equals(semester4[i])) return true;
    }
    return false;
  }
  
  public boolean isTwoCredits(String s) {
    for (int i = 0; i < twoCredits.length; i++) {
      if (s.equals(twoCredits[i])) return true;
    }
    return false;
  }

  public boolean isThreeCredits(String s) {
    for (int i = 0; i < threeCredits.length; i++) {
      if (s.equals(threeCredits[i])) return true;
    }
    return false;
  }

  public boolean isFourCredits(String s) {
    for (int i = 0; i < fourCredits.length; i++) {
      if (s.equals(fourCredits[i])) return true;
    }
    return false;
  }

  public boolean passedSemester1() {
    if (numCourses == 0) return false;
    int check = 0;
    for (int i = 0; i < semester1.length; i++) {
      for (int j = 0; j < courseTaken.size(); j++) {
        if (semester1[i].equals(courseTaken.get(j))) check++;
        if (check == semester1.length) return true;
      }
    }
    return false;
  }

  public boolean passedSemester2() {
    int check = 0;
    for (int i = 0; i < semester2.length; i++) {
      for (int j = 0; j < courseTaken.size(); j++) {
        if (semester2[i].equals(courseTaken.get(j))) check++;
        if (check == semester2.length) return true;
      }
    }
    return false;
  }

  public boolean passedSemester3() {
    int check = 0;
    for (int i = 0; i < semester3.length; i++) {
      for (int j = 0; j < courseTaken.size(); j++) {
        if (semester3[i].equals(courseTaken.get(j))) check++;
        if (check == semester3.length) return true;
      }
    }
    return false;
  }

  public boolean passedSemester4() {
    int check = 0;
    for (int i = 0; i < semester4.length; i++) {
      for (int j = 0; j < courseTaken.size(); j++) {
        if (semester3[i].equals(courseTaken.get(j))) check++;
        if (check == semester4.length) return true;
      }
    }
    return false;
  }
  
  public void gotAxe() { hasAxe = true; }
  public void gotPickaxe() { hasPickaxe = true; }
  public void gotBoat() { hasBoat = true; tileMap.replace(22, 4); }
  public void gotKey() { hasKey = true; }
  public boolean hasAxe() { return hasAxe; }
  public boolean hasPickaxe() { return hasPickaxe; }
  public boolean hasBoat() { return hasBoat; }
  public boolean hasKey() { return hasKey; }
  
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
				JukeBox.play("remove");
      }
      if (currentAnimation == DOWN && tileMap.getIndex(rowTile + 1, colTile) == 21) {
        tileMap.setTile(rowTile + 1, colTile, 1);
				JukeBox.play("remove");
      }
      if (currentAnimation == LEFT && tileMap.getIndex(rowTile, colTile - 1) == 21) {
        tileMap.setTile(rowTile, colTile - 1, 1);
				JukeBox.play("remove");
      }
      if (currentAnimation == RIGHT && tileMap.getIndex(rowTile, colTile + 1) == 21) {
        tileMap.setTile(rowTile, colTile + 1, 1);
				JukeBox.play("remove");
      }
    }

    if (hasPickaxe) {
      if (currentAnimation == UP && tileMap.getIndex(rowTile - 1, colTile) == 26) {
        tileMap.setTile(rowTile - 1, colTile, 6);
      }
      if (currentAnimation == DOWN && tileMap.getIndex(rowTile + 1, colTile) == 26) {
        tileMap.setTile(rowTile + 1, colTile, 6);
      }
      if (currentAnimation == LEFT && tileMap.getIndex(rowTile, colTile - 1) == 26) {
        tileMap.setTile(rowTile, colTile - 1, 6);
      }
      if (currentAnimation == RIGHT && tileMap.getIndex(rowTile, colTile + 1) == 26) {
        tileMap.setTile(rowTile, colTile + 1, 6);
      }
    }

    if (hasKey) {
      if (currentAnimation == UP) {
        if (tileMap.getIndex(rowTile - 1, colTile) == 23) {
          tileMap.setTile(rowTile - 1, colTile, 1);
  				JukeBox.play("open_door");
          hasKey = false;
        }
        else if (tileMap.getIndex(rowTile - 1, colTile) == 24) {
          tileMap.setTile(rowTile - 1, colTile, 6);
  				JukeBox.play("open_door");
          hasKey = false;
        }
      }
      if (currentAnimation == DOWN) {
        if (tileMap.getIndex(rowTile + 1, colTile) == 23) {
          hasKey = false;
  				JukeBox.play("open_door");
          tileMap.setTile(rowTile + 1, colTile, 1);
        }
        else if (tileMap.getIndex(rowTile + 1, colTile) == 24) {
          tileMap.setTile(rowTile + 1, colTile, 6);
  				JukeBox.play("open_door");
          hasKey = false;
        }
      }
      if (currentAnimation == LEFT) {
        if (tileMap.getIndex(rowTile, colTile - 1) == 23) {
          tileMap.setTile(rowTile, colTile - 1, 1);
  				JukeBox.play("open_door");
          hasKey = false;
        }
        else if (tileMap.getIndex(rowTile, colTile - 1) == 24) {
          tileMap.setTile(rowTile, colTile - 1, 6);
  				JukeBox.play("open_door");
          hasKey = false;
        }
      }
      if (currentAnimation == RIGHT) {
        if (tileMap.getIndex(rowTile, colTile + 1) == 23) {
          tileMap.setTile(rowTile, colTile + 1, 1);
  				JukeBox.play("open_door");
          hasKey = false;
        }
        else if (tileMap.getIndex(rowTile, colTile + 1) == 24) {
          tileMap.setTile(rowTile, colTile + 1, 6);
  				JukeBox.play("open_door");
          hasKey = false;
        }
      }
    }

  }
  
  public void update() {
    
    ticks++;
    
    boolean current = onWater;
    int positionNow = tileMap.getIndex(yDest / tileSize, xDest / tileSize);
    
    if (positionNow == 4 || positionNow == 9 || positionNow == 10) {
      onWater = true;
    }
    else {
      onWater = false;
    }
    if (!current && onWater) {
      JukeBox.play("splash");
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
