// Super class of all game objects
// has all logic to move around a tile map based
package com.TCourse.Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.TCourse.TileMap.Tile;
import com.TCourse.TileMap.TileMap;

public abstract class Entity {
  
  // for dimensions
  protected int width;
  protected int height;
  protected int cWidth;
  protected int cHeight;
  
  // for position
  protected int x;
  protected int y;
  protected int xDest;
  protected int yDest;
  protected int rowTile;
  protected int colTile;
  
  // for movement
  protected boolean moving;
  protected boolean left;
  protected boolean right;
  protected boolean up;
  protected boolean down;
  
  // for attributes
  protected int moveSpeed;
  
  // for tilemap
  protected TileMap tileMap;
  protected int tileSize;
  protected int xMap;
  protected int yMap;
  
  // for animation
  protected Animation animation;
  protected int currentAnimation;
  
  public Entity(TileMap tm) {
    tileMap = tm;
    tileSize = tileMap.getTileSize();
    animation = new Animation();
  }
  
  public int getX() { return x; }
  public int getY() { return y; }
  public int getRow() { return rowTile; }
  public int getCol() { return colTile; }
  
  public void setPosition(int i1, int i2) {
    x = i1;
    y = i2;
    xDest = x;
    yDest = y;
  }
  public void setMapPosition() {
    xMap = tileMap.getX();
    yMap = tileMap.getY();
  }
  public void setTilePosition(int i1, int i2) {
    y = i1 * tileSize + tileSize / 2;
    x = i2 * tileSize + tileSize / 2;
    xDest = x;
    yDest = y;
  }
  
  public void setLeft() {
    if (moving) return;
    left = true;
    moving = validateNextPosition();
  }
  public void setRight() {
    if (moving) return;
    right = true;
    moving = validateNextPosition();
  }
  public void setUp() {
    if (moving) return;
    up = true;
    moving = validateNextPosition();
  }
  public void setDown() {
    if (moving) return;
    down = true;
    moving = validateNextPosition();
  }
  
  public boolean intersects(Entity o) {
    return getRectangle().intersects(o.getRectangle());
  }
  
  public Rectangle getRectangle() {
    return new Rectangle(x, y, cWidth, cHeight);
  }
  
  // check if player can move or not
  // into the next position
  public boolean validateNextPosition() {
    
    if (moving) return true;
    
    rowTile = y / tileSize;
    colTile = x / tileSize;
    
    if (left) {
      if (colTile == 0 || tileMap.getType(rowTile, colTile - 1) == Tile.BLOCKED) {
        return false;
      }
      else {
        xDest = x - tileSize;
      }
    }
    if (right) {
      if (colTile == tileMap.getNumCols() || tileMap.getType(rowTile, colTile + 1) == Tile.BLOCKED) {
        return false;
      }
      else {
        xDest = x + tileSize;
      }
    }
    if (up) {
      if (rowTile == 0 || tileMap.getType(rowTile - 1, colTile) == Tile.BLOCKED) {
        return false;
      }
      else {
        yDest = y - tileSize;
      }
    }
    if (down) {
      if (rowTile == tileMap.getNumRows() - 1 || tileMap.getType(rowTile + 1, colTile) == Tile.BLOCKED) {
        return false;
      }
      else {
        yDest = y + tileSize;
      }
    }
    
    return true;
    
  }
  
  // calculates the destination coordinates.
  public void getNextPosition() {
    
    if (left && x > xDest) x -= moveSpeed;
    else left = false;
    if (left && x < xDest) x = xDest;
    
    if (right && x < xDest) x += moveSpeed;
    else right = false;
    if (right && x > xDest) x = xDest;
    
    if (up && y > yDest) y -= moveSpeed;
    else up = false;
    if (up && y < yDest) y = yDest;
    
    if(down && y < yDest) y += moveSpeed;
    else down = false;
    if(down && y > yDest) y = yDest;
    
  }
  
  public void update() {
    
    // get next position
    if(moving) getNextPosition();
    
    // check stop moving
    if(x == xDest && y == yDest) {
      left = right = up = down = moving = false;
      rowTile = y / tileSize;
      colTile = x / tileSize;
    }
    
    // update animation
    animation.update();
    
  }
  
  // Draws the entity.
  public void draw(Graphics2D g) {
    setMapPosition();
    g.drawImage(
      animation.getImage(),
      x + xMap - width / 2,
      y + yMap - height / 2,
      null
    );
  }
  
}
