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
  private BufferedImage book;
  private BufferedImage boat;
  private BufferedImage axe;

  private Player player;

  private int creditUnit;

  private Font font;
  private Color textColor;

  public Hud(Player p, int b) {

    player = p;
    creditUnit = b;
    yOffset = GamePanel.HEIGHT;

    bar = Content.BAR[0][0];
    book = Content.BOOK[0][0];
    boat = Content.ITEMS[0][0];
    axe = Content.ITEMS[0][1];

    font = new Font("Arial", Font.PLAIN, 10);
    textColor = new Color(47, 64, 126);

  }

  public void draw(Graphics2D g) {

    g.drawImage(bar, 0, yOffset, null);

    g.setColor(textColor);
    g.fillRect(8, yOffset + 6, (int)(28.0 * player.currentCredit() / creditUnit), 4);

    g.setColor(textColor);
    g.setFont(font);
    String s = player.currentCredit() + "/" + creditUnit;
    Content.drawString(g, s, 40, yOffset + 3);

    if (player.hasBoat()) g.drawImage(boat, 100, yOffset, null);
    if (player.hasAxe()) g.drawImage(axe, 112, yOffset, null);

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

  }

}
