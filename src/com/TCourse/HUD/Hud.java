package com.TCourse.HUD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.TCourse.Entity.Player;
import com.TCourse.Main.GamePanel;
import com.TCourse.Manager.Content;

public class Hud {

  private int yOffset;

  private BufferedImage bar;
  private BufferedImage boat;
  private BufferedImage axe;
  private BufferedImage pickaxe;
  private BufferedImage key;

  private Player player;

  private int creditUnit;
  private int countTicks;
  private int countTicks2;
  private int semester;
  private int currentSemester;
  private String courseName;

  private Font font;
  private Color textColor;

  public Hud(Player p, int b) {

    player = p;
    creditUnit = b;
    yOffset = GamePanel.HEIGHT;

    bar = Content.BAR[0][0];
    axe = Content.ITEMS[0][0];
    pickaxe = Content.ITEMS[0][1];
    boat = Content.ITEMS[0][2];
    key = Content.ITEMS[0][3];


    font = new Font("Arial", Font.PLAIN, 10);
    textColor = new Color(47, 64, 126);

    semester = -1;
    currentSemester = 1;

  }

  public void alreadyTaken(String s) {
    countTicks = 0;
    courseName = s;
  }

  public void hasNotFinished(int i) {
    countTicks2 = 0;
    semester = i;
  }

  public void draw(Graphics2D g) {

    g.drawImage(bar, 0, yOffset, null);

    g.setColor(textColor);
    g.fillRect(8, yOffset + 6, (int)(28.0 * player.currentCredit() / creditUnit), 4);

    g.setColor(textColor);
    g.setFont(font);
    String s = player.currentCredit() + "/" + creditUnit;
    Content.drawString(g, s, 38, yOffset + 3);

    if (player.hasBoat()) g.drawImage(boat, 100, yOffset, null);
    if (player.hasAxe()) g.drawImage(axe, 88, yOffset, null);
    if (player.hasPickaxe()) g.drawImage(pickaxe, 112, yOffset, null);
    if (player.hasKey()) g.drawImage(key, 76, yOffset, null);

    int minutes = (int) (player.getTicks() / 1800);
    int seconds = (int) ((player.getTicks() / 30) % 60);
    if (minutes < 10) {
      if (seconds < 10) Content.drawString(g, "0" + minutes + ":0" + seconds, 85, 3);
      else Content.drawString(g, "0" + minutes + ":" + seconds, 85, 3);
    }
    else {
      if (seconds < 10) Content.drawString(g, minutes + ":0" + seconds, 85, 3);
      else Content.drawString(g, minutes + ":" + seconds, 85, 3);
    }

    countTicks++;
    if (countTicks <= 30 && player.numCourses() > 0) {
      Content.drawString(g, courseName, 16, 16);
    }
    
    countTicks2++;
    if (semester > 0 && countTicks2 <= 30) {
      Content.drawString(g, "Please finish", 12, 16);
      Content.drawString(g, "SEMESTER " + semester, 24, 32);
      Content.drawString(g, "first", 44, 48);
    }
    
    if (currentSemester == 4 && countTicks >= 30 && countTicks <= 70 && player.numCourses() > 12 && player.passedSemester3()) {
      Content.drawString(g, "You have passed", 4, 16);
      Content.drawString(g, "SEMESTER 4", 24, 32);
    }
    else if (currentSemester == 3 && countTicks >= 30 && countTicks <= 70 && player.numCourses() > 12 && player.passedSemester3()) {
      Content.drawString(g, "You have passed", 4, 16);
      Content.drawString(g, "SEMESTER 3", 24, 32);
    }
    else if (currentSemester == 2 && countTicks >= 30 && countTicks <= 70 && player.numCourses() > 6 && player.passedSemester2()) {
      Content.drawString(g, "You have passed", 4, 16);
      Content.drawString(g, "SEMESTER 2", 24, 32);
      if (countTicks == 70) currentSemester = 3;
    }
    else if (countTicks >= 30 && countTicks <= 70 && player.numCourses() > 0 && player.passedSemester1() && currentSemester == 1) {
      Content.drawString(g, "You have passed", 4, 16);
      Content.drawString(g, "SEMESTER 1", 24, 32);
      if (countTicks == 70) currentSemester = 2;
    }
    
  }

}
