package com.TCourse.GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.TCourse.Entity.Book;
import com.TCourse.Entity.Course;
import com.TCourse.Entity.Item;
import com.TCourse.Entity.Player;
import com.TCourse.Entity.Sparkle;
import com.TCourse.HUD.Hud;
import com.TCourse.Main.GamePanel;
import com.TCourse.Manager.DataTime;
import com.TCourse.Manager.GameStateManager;
import com.TCourse.Manager.Keys;
import com.TCourse.TileMap.TileMap;

public class PlayState extends GameState {
  
  private Player player;
  
  private TileMap tileMap;
  
  private ArrayList<Book> books;

  private ArrayList<Course> courses;
  
  private ArrayList<Item> items;
  
  private ArrayList<Sparkle> sparkles;
  
  private int xSector;
  private int ySector;
  private int sectorSize; 
  
  private Hud hud;
  
  private boolean blockInput;
  private boolean eventStart;
  private boolean eventFinish;
  private int eventTick;
  
  private ArrayList<Rectangle> boxes;
  
  public PlayState(GameStateManager gsm) {
    super(gsm);
  }
  
  public void init() {
    
    books = new ArrayList<Book>();
    courses = new ArrayList<Course>();
    sparkles = new ArrayList<Sparkle>();
    items = new ArrayList<Item>();
    
    tileMap = new TileMap(16);
    tileMap.loadTiles("/Tilesets/tilesets.gif");
    tileMap.loadMap("/Maps/testtileset.map");
    
    player = new Player(tileMap);
    
    populateBooks();
    populateItems();
    
    player.setTilePosition(17, 17);
    player.setTotalCourses(20);
    
    sectorSize = GamePanel.WIDTH;
    xSector = player.getX() / sectorSize;
    ySector = player.getY() / sectorSize;
    tileMap.setPositionImmediately(-xSector * sectorSize, -ySector * sectorSize);
    
    hud = new Hud(player, 37);
    
    boxes = new ArrayList<Rectangle>();
    eventStart = true;
    eventStart();
      
  }

  private void populateBooks() {
    
    Book b;

    b = new Book(tileMap, "DASPROG");
    b.setTilePosition(20, 20);
		b.addChange(new int[] { 23, 19, 1 });
		b.addChange(new int[] { 23, 20, 1 });
		books.add(b);
    b = new Book(tileMap, "MAT 1");
    b.setTilePosition(12,36);
		b.addChange(new int[] { 31, 17, 1 });
		books.add(b);
    b = new Book(tileMap, "FIS 1");
    b.setTilePosition(28, 4);
    b.addChange(new int[] {27, 7, 1});
		b.addChange(new int[] {28, 7, 1});
		books.add(b);
    b = new Book(tileMap, "KIM");
    b.setTilePosition(4, 34);
    b.addChange(new int[] { 31, 21, 1 });
		books.add(b);


    b = new Book(tileMap, "BIN");
    b.setTilePosition(28, 19);
		books.add(b);
    b = new Book(tileMap, "PANCASILA");
    b.setTilePosition(35, 26);
		books.add(b);
    b = new Book(tileMap, "MAT 2");
    b.setTilePosition(38, 36);
		books.add(b);
    b = new Book(tileMap, "FIS 2");
    b.setTilePosition(27, 28);
		books.add(b);
    b = new Book(tileMap, "KWN");
    b.setTilePosition(20, 30);
		books.add(b);
    b = new Book(tileMap, "BIG");
    b.setTilePosition(14, 25);
		books.add(b);
    b = new Book(tileMap, "STRUKDAT");
    b.setTilePosition(4, 21);
		books.add(b);
    b = new Book(tileMap, "SISDIG");
    b.setTilePosition(9, 14);
		books.add(b);
    b = new Book(tileMap, "MATDIS");
    b.setTilePosition(4, 3);
		books.add(b);
    // b = new Book(tileMap);
    // b.setTilePosition(20, 14);
		// books.add(b);
    // b = new Book(tileMap);
    // b.setTilePosition(13, 20);
		// books.add(b);

  }
  
  private void populateItems() {
    
    Item item;
    
    item = new Item(tileMap);
    item.setType(Item.AXE);
    item.setTilePosition(26, 37);
    items.add(item);
    
    item = new Item(tileMap);
    item.setType(Item.BOAT);
    item.setTilePosition(12, 4);
    items.add(item);
    
  }
  
  public void update() {
    
    handleInput();
    
    if (eventStart) eventStart();
    if (eventFinish) eventFinish();
    
    if (player.numCourses() == player.getTotalCourses()) {
      eventFinish = blockInput = true;
    }
    
    int oldxs = xSector;
    int oldys = ySector;
    xSector = player.getX() / sectorSize;
    ySector = player.getY() / sectorSize;
    tileMap.setPosition(-xSector * sectorSize, -ySector * sectorSize);
    tileMap.update();
    
    if (tileMap.isMoving()) return;
    
    player.update();
    
    for (int i = 0; i < books.size(); i++) {
      
      Book b = books.get(i);
      b.update();

      boolean hasFound = false;
      
      if (player.intersects(b)) {
        
        books.remove(i);
        i--;
        
        if (!hasFound) {
          if (b.twoCredits()) {
            player.collectedCourse(2);
            hasFound = true;
          }
          if (b.threeCredits()) {
            player.collectedCourse(3);
            hasFound = true;
          }
          if (b.fourCredits()) {
            player.collectedCourse(4);
            hasFound = true;
          }
        }
        
        Sparkle s = new Sparkle(tileMap);
        s.setPosition(b.getX(), b.getY());
        sparkles.add(s);
        
        ArrayList<int[]> ali = b.getChanges();
        for (int[] j : ali) {
          tileMap.setTile(j[0], j[1], j[2]);
        }
        
      }
    }
    
    for (int i = 0; i < sparkles.size(); i++) {
      Sparkle s = sparkles.get(i);
      s.update();
      if (s.shouldRemove()) {
        sparkles.remove(i);
        i--;
      }
    }
    
    for (int i = 0; i < items.size(); i++) {
      Item item = items.get(i);
      if (player.intersects(item)) {
        items.remove(i);
        i--;
        item.collected(player);
        Sparkle s = new Sparkle(tileMap);
        s.setPosition(item.getX(), item.getY());
        sparkles.add(s);
      }
    }
    
  }
  
  public void draw(Graphics2D g) {
    
    tileMap.draw(g);
    
    player.draw(g);
    
    for (Book b : books) {
      b.draw(g);
    }
    
    for (Sparkle s : sparkles) {
      s.draw(g);
    }
    
    for (Item i : items) {
      i.draw(g);
    }
    
    hud.draw(g);
    
    g.setColor(java.awt.Color.BLACK);
    for (int i = 0; i < boxes.size(); i++) {
      g.fill(boxes.get(i));
    }
    
  }
  
  public void handleInput() {
    if (Keys.isPressed(Keys.ESCAPE)) {
      gsm.setPaused(true);
    }
    if (blockInput) return;
    if (Keys.isDown(Keys.LEFT)) player.setLeft();
    if (Keys.isDown(Keys.RIGHT)) player.setRight();
    if (Keys.isDown(Keys.UP)) player.setUp();
    if (Keys.isDown(Keys.DOWN)) player.setDown();
    if (Keys.isPressed(Keys.SPACE)) player.setAction();
  }
  
  //===============================================
  
  private void eventStart() {
    eventTick++;
		if (eventTick == 1) {
			boxes.clear();
			for(int i = 0; i < 9; i++) {
				boxes.add(new Rectangle(0, i * 16, GamePanel.WIDTH, 16));
			}
		}
		if (eventTick > 1 && eventTick < 32) {
			for(int i = 0; i < boxes.size(); i++) {
				Rectangle r = boxes.get(i);
				if(i % 2 == 0) {
					r.x -= 4;
				}
				else {
					r.x += 4;
				}
			}
		}
		if (eventTick == 33) {
			boxes.clear();
			eventStart = false;
			eventTick = 0;
		}
  }
  
  private void eventFinish() {
    eventTick++;
    if (eventTick == 1) {
      boxes.clear();
      for (int i = 0; i < 9; i++) {
        if (i % 2 == 0) boxes.add(new Rectangle(-128, i * 16, GamePanel.WIDTH, 16));
        else boxes.add(new Rectangle(128, i * 16, GamePanel.WIDTH, 16));
      }
    }
    if (eventTick > 1) {
      for (int i = 0; i < boxes.size(); i++) {
        Rectangle r = boxes.get(i);
        if (i % 2 == 0) {
          if (r.x < 0) r.x += 4;
        }
        else {
          if (r.x > 0) r.x -= 4;
        }
      }
    }
    if (eventTick > 33) {
      if (eventFinish) {
        DataTime.setTime(player.getTicks());
        gsm.setState(GameStateManager.GAMEOVER);
      }
    }
  }
  
}
