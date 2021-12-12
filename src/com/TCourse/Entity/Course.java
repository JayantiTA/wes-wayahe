package com.TCourse.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.TCourse.Manager.Content;
import com.TCourse.TileMap.TileMap;

public class Course extends Entity {

  BufferedImage sprite;
  
  private ListCourses course;
  
  public enum ListCourses {
    INIT(0, false), MAT1(3, false), FIS1(4, false), KIM(3, false), BIN(2, false), PANCASILA(2, false), DASPROG(4, false),
    MAT2(3, false), FIS2(3, false), AGAMA(2, false), BIG(2, false), KWN(2, false), STRUKDAT(3, false), SISDIG(3, false),
    ALIN(3, false), KOMNUM(3, false), MATDIS(3, false), ORKOM(3, false), SBD(4, false), PBO(3, false),
    PROBSTAT(3, false), PAA(3, false);

    private int sks;
    private boolean hasCourse;
    ListCourses(int i, boolean b) {
      sks = i;
      hasCourse = b;
    }
    public void setCourse(boolean b) {
      hasCourse = b;
    }
    public int getSks() {
      return sks;
    }
    public boolean finishCourse() {
      return hasCourse;
    }
  }
  
  public Course(TileMap tm) {
    super(tm);
    course = ListCourses.INIT;
    width = height = 16;
    cWidth = cHeight = 12;
  }
  
  public void setCourse(ListCourses c) {
    sprite = Content.ITEMS[1][2];
  }
  
  public void update() {
    animation.update();
  }
  
  public void draw(Graphics2D g) {
    setMapPosition();
    g.drawImage(sprite, x + xMap - width / 2, y + yMap - height / 2, null);
  }
  
}
