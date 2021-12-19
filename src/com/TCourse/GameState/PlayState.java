package com.TCourse.GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.TCourse.Entity.Book;
import com.TCourse.Entity.Item;
import com.TCourse.Entity.Player;
import com.TCourse.Entity.Sparkle;
import com.TCourse.HUD.Hud;
import com.TCourse.Main.GamePanel;
import com.TCourse.Manager.Content;
import com.TCourse.Manager.DataTime;
import com.TCourse.Manager.GameStateManager;
import com.TCourse.Manager.Keys;
import com.TCourse.TileMap.TileMap;

public class PlayState extends GameState {
  
  private Player player;
  
  private TileMap tileMap;
  
  private ArrayList<Book> books;

  private ArrayList<Item> items;
  
  private ArrayList<Sparkle> sparkles;
  
  private int xSector;
  private int ySector;
  private int sectorSize; 
  
  private Hud hud;
  
  private boolean blockInput;
  private boolean eventStart;
  private boolean eventFinish;
  private boolean firstItem;
  private boolean updateItem;
  private int eventTick;
  private int currentPage;
  private int prevPage;
  
  private ArrayList<Rectangle> boxes;
  
  public PlayState(GameStateManager gsm) {
    super(gsm);
  }
  
  public void init() {
    
    books = new ArrayList<Book>();
    sparkles = new ArrayList<Sparkle>();
    items = new ArrayList<Item>();
    
    tileMap = new TileMap(16);
    tileMap.loadTiles("/Tilesets/tilesets.gif");
    tileMap.loadMap("/Maps/testtileset.map");
    
    player = new Player(tileMap);
    
    firstItem = true;
    updateItem = false;
    populateBooks();
    populateItems();
    
    player.setTilePosition(17, 17);
    player.setTotalCourses(20);
    
    sectorSize = GamePanel.WIDTH;
    xSector = player.getX() / sectorSize;
    ySector = player.getY() / sectorSize;
    tileMap.setPositionImmediately(-xSector * sectorSize, -ySector * sectorSize);
    
    hud = new Hud(player, 37);

    currentPage = -1;
    prevPage = -1;
    
    boxes = new ArrayList<Rectangle>();
    eventStart = true;
    eventStart();
      
  }

  private void populateBooks() {
    
    Book b;

    b = new Book(tileMap, "DASPROG");
    b.setTilePosition(20, 20);
		books.add(b);
    b = new Book(tileMap, "MAT 1");
    b.setTilePosition(20,21);
		b.addChange(new int[] { 31, 17, 1 });
		books.add(b);
    b = new Book(tileMap, "FIS 1");
    b.setTilePosition(20, 22);
    b.addChange(new int[] {27, 7, 1});
		b.addChange(new int[] {28, 7, 1});
		books.add(b);
    b = new Book(tileMap, "KIM");
    b.setTilePosition(20, 19);
    b.addChange(new int[] { 31, 21, 1 });
		books.add(b);


    b = new Book(tileMap, "BIN");
    b.setTilePosition(20, 18);
		books.add(b);
    b = new Book(tileMap, "PANCASILA");
    b.setTilePosition(21, 20);
		books.add(b);
    b = new Book(tileMap, "MAT 2");
    b.setTilePosition(21, 21);
		books.add(b);
    b = new Book(tileMap, "FIS 2");
    b.setTilePosition(21, 22);
		books.add(b);
    b = new Book(tileMap, "KWN");
    b.setTilePosition(18, 20);
		books.add(b);
    b = new Book(tileMap, "BIG");
    b.setTilePosition(17, 22);
		books.add(b);
    b = new Book(tileMap, "STRUKDAT");
    b.setTilePosition(19, 21);
		books.add(b);
    b = new Book(tileMap, "SISDIG");
    b.setTilePosition(19, 20);
		books.add(b);
    b = new Book(tileMap, "AGAMA");
    b.setTilePosition(18, 19);
		books.add(b);
    b = new Book(tileMap, "MATDIS");
    b.setTilePosition(18, 18);
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

    if (!firstItem) {
      item = new Item(tileMap);
      item.setType(Item.KEY);
      item.setTilePosition(37, 35);
      items.add(item);
      updateItem = true;
    }
    
    if (firstItem) {

      item = new Item(tileMap);
      item.setType(Item.AXE);
      item.setTilePosition(22, 22);
      items.add(item);
      
      item = new Item(tileMap);
      item.setType(Item.BOAT);
      item.setTilePosition(17, 22);
      items.add(item);
  
      item = new Item(tileMap);
      item.setType(Item.PICKAXE);
      item.setTilePosition(17, 22);
      items.add(item);
  
      item = new Item(tileMap);
      item.setType(Item.KEY);
      item.setTilePosition(20, 17);
      items.add(item);

      firstItem = false;
    }
    
  }
  
  public void update() {
    
    handleInput();
    
    if (eventStart) eventStart();
    if (eventFinish) eventFinish();
    if (player.passedSemester2() && !updateItem) populateItems();
    
    if (player.numCourses() == player.getTotalCourses()) {
      eventFinish = blockInput = true;
    }
    
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

        if (player.courseInSemester2(b.getCourseName()) && !player.passedSemester1()) {
          hud.hasNotFinished(1);
          return;
        }

        if (player.courseInSemester3(b.getCourseName()) && !player.passedSemester2()) {
          hud.hasNotFinished(2);
          return;
        }
        
        player.takeCourse(b.getCourseName());
        hud.alreadyTaken(b.getCourseName());
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
    
    if (currentPage == 0 && player.getCourseTaken().size() > 0) {
      int positionX = 16;
      int positionY = 16;
      String sks = new String();
      for (int i = 0;  i < 6; i++) {
        if (player.isTwoCredits(player.getCourseTaken().get(i))) sks = "2";
        if (player.isThreeCredits(player.getCourseTaken().get(i))) sks = "3";
        if (player.isFourCredits(player.getCourseTaken().get(i))) sks = "4";
        Content.drawString(g, player.getCourseTaken().get(i), positionX, positionY);
        Content.drawString(g, sks, 90, positionY);
        positionY += 16;
        if (i == player.getCourseTaken().size() - 1 && player.getCourseTaken().size() < 6) break;
      }
    }
    if (currentPage == 1 && player.getCourseTaken().size() > 6) {
      int positionX = 16;
      int positionY = 16;
      String sks = new String();
      for (int i = 6;  i < 12; i++) {
        if (player.isTwoCredits(player.getCourseTaken().get(i))) sks = "2";
        if (player.isThreeCredits(player.getCourseTaken().get(i))) sks = "3";
        if (player.isFourCredits(player.getCourseTaken().get(i))) sks = "4";
        Content.drawString(g, player.getCourseTaken().get(i), positionX, positionY);
        Content.drawString(g, sks, 90, positionY);
        positionY += 16;
        if (i == player.getCourseTaken().size() - 1 && player.getCourseTaken().size() < 12) break;
      }
    }
    if (currentPage == 2 && player.getCourseTaken().size() > 12) {
      int positionX = 16;
      int positionY = 16;
      String sks = new String();
      for (int i = 12;  i < 18; i++) {
        if (player.isTwoCredits(player.getCourseTaken().get(i))) sks = "2";
        if (player.isThreeCredits(player.getCourseTaken().get(i))) sks = "3";
        if (player.isFourCredits(player.getCourseTaken().get(i))) sks = "4";
        Content.drawString(g, player.getCourseTaken().get(i), positionX, positionY);
        Content.drawString(g, sks, 90, positionY);
        positionY += 16;
        if (i == player.getCourseTaken().size() - 1 && player.getCourseTaken().size() < 18) break;
      }
    }
    if (currentPage == 3 && player.getCourseTaken().size() > 18) {
      int positionX = 16;
      int positionY = 16;
      String sks = new String();
      for (int i = 18;  i < 24; i++) {
        if (player.isTwoCredits(player.getCourseTaken().get(i))) sks = "2";
        if (player.isThreeCredits(player.getCourseTaken().get(i))) sks = "3";
        if (player.isFourCredits(player.getCourseTaken().get(i))) sks = "4";
        Content.drawString(g, player.getCourseTaken().get(i), positionX, positionY);
        Content.drawString(g, sks, 90, positionY);
        positionY += 16;
        if (i == player.getCourseTaken().size() - 1 && player.getCourseTaken().size() < 24) break;
      }
    }

    g.setColor(java.awt.Color.BLACK);
    for (int i = 0; i < boxes.size(); i++) {
      g.fill(boxes.get(i));
    }

  }
  
  public void handleInput() {
    if (Keys.isPressed(Keys.ESCAPE)) {
      gsm.setPaused(true);
    }
    if (Keys.isPressed(Keys.F2)) {
      if (player.getCourseTaken().size() > 18 && prevPage == 2) currentPage = 3;
      else if (player.getCourseTaken().size() > 12 && prevPage == 1) currentPage = 2;
      else if (player.getCourseTaken().size() > 6 && prevPage == 0) currentPage = 1;
      else if (player.getCourseTaken().size() > 0 && prevPage == -1) currentPage = 0;
      else currentPage = -1;
      prevPage = currentPage;
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
