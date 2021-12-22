// Loads tilesets and 2d array tilemap
package com.TCourse.TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import com.TCourse.Main.GamePanel;

public class TileMap {
  
  // for position
  private int x;
  private int y;
  private int xDest;
  private int yDest;
  private int speed;
  private boolean moving;
  
  // for bounds
  private int xMin;
  private int yMin;
  private int xMax;
  private int yMax;
  
  // for map
  private int[][] map;
  private int tileSize;
  private int numRows;
  private int numCols;
  private int width;
  private int height;
  
  // tileset
  private BufferedImage tileset;
  private int numTilesAcross;
  private Tile[][] tiles;
  
  // drawing
  private int rowOffset;
  private int colOffset;
  private int numRowsToDraw;
  private int numColsToDraw;
  
  public TileMap(int tileSize) {
    this.tileSize = tileSize;
    numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
    numColsToDraw = GamePanel.WIDTH / tileSize + 2;
    speed = 3;
  }
  
  public void loadTiles(String s) {
    
    try {

      tileset = ImageIO.read(
            getClass().getResourceAsStream(s)
          );
      numTilesAcross = tileset.getWidth() / tileSize;
      tiles = new Tile[2][numTilesAcross];
      
      BufferedImage subimage;
      for (int col = 0; col < numTilesAcross; col++) {
        subimage = tileset.getSubimage(
              col * tileSize,
              0,
              tileSize,
              tileSize
            );
        tiles[0][col] = new Tile(subimage, Tile.NORMAL);
        subimage = tileset.getSubimage(
              col * tileSize,
              tileSize,
              tileSize,
              tileSize
            );
        tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
      }
      
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  public void loadMap(String s) {
    
    try {
      
      InputStream in = getClass().getResourceAsStream(s);
      BufferedReader br = new BufferedReader(
            new InputStreamReader(in)
          );
      
      numCols = Integer.parseInt(br.readLine());
      numRows = Integer.parseInt(br.readLine());
      map = new int[numRows][numCols];
      width = numCols * tileSize;
      height = numRows * tileSize;
      
      xMin = GamePanel.WIDTH - width;
      xMin = -width;
      xMax = 0;
      yMin = GamePanel.HEIGHT - height;
      yMin = -height;
      yMax = 0;
      
      String delims = "\\s+";
      for(int row = 0; row < numRows; row++) {
        String line = br.readLine();
        String[] tokens = line.split(delims);
        for(int col = 0; col < numCols; col++) {
          map[row][col] = Integer.parseInt(tokens[col]);
        }
      }
      
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    
  }
  
  public int getTileSize() { return tileSize; }
  public int getX() { return x; }
  public int getY() { return y; }
  public int getWidth() { return width; }
  public int getHeight() { return height; }
  public int getNumRows() { return numRows; }
  public int getNumCols() { return numCols; }
  public int getType(int row, int col) {
    int rc = map[row][col];
    int r = rc / numTilesAcross;
    int c = rc % numTilesAcross;
    return tiles[r][c].getType();
  }
  public int getIndex(int row, int col) {
    return map[row][col];
  }
  public boolean isMoving() { return moving; }
  
  public void setTile(int row, int col, int index) {
    map[row][col] = index;
  }
  public void replace(int i1, int i2) {
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCols; col++) {
        if (map[row][col] == i1) map[row][col] = i2;
      }
    }
  }
  
  public void setPosition(int x, int y) {
    xDest = x;
    yDest = y;
  }
  public void setPositionImmediately(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public void fixBounds() {
    if (x < xMin) x = xMin;
    if (y < yMin) y = yMin;
    if (x > xMax) x = xMax;
    if (y > yMax) y = yMax;
  }
  
  public void update() {
    if (x < xDest) {
      x += speed;
      if (x > xDest) {
        x = xDest;
      }
    }
    if (x > xDest) {
      x -= speed;
      if(x < xDest) {
        x = xDest;
      }
    }
    if (y < yDest) {
      y += speed;
      if (y > yDest) {
        y = yDest;
      }
    }
    if (y > yDest) {
      y -= speed;
      if (y < yDest) {
        y = yDest;
      }
    }
    
    fixBounds();
    
    colOffset = -this.x / tileSize;
    rowOffset = -this.y / tileSize;
    
    if (x != xDest || y != yDest) moving = true;
    else moving = false;
    
  }
  
  public void draw(Graphics2D g) {
    
    for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
    
      if (row >= numRows) break;
      
      for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
        
        if (col >= numCols) break;
        if (map[row][col] == 0) continue;
        
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;
        
        g.drawImage(
          tiles[r][c].getImage(),
          x + col * tileSize,
          y + row * tileSize,
          null
        );
        
      }
      
    }
    
  }
}
