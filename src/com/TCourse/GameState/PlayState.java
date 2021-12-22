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
import com.TCourse.Manager.JukeBox;
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
  private boolean lastItem;
  private boolean updateItem;
  private boolean updateCredit;
  private boolean spring;
  private boolean summer;
  private boolean accel;
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
    lastItem = false;
    updateItem = false;
    updateCredit = false;
    spring = true;
    summer = false;
    accel = false;
    populateBooks();
    populateItems();
    
    player.setTilePosition(17, 17);
    player.setTotalCourses(28);
    player.setTotalCredit(75);
    
    sectorSize = GamePanel.WIDTH;
    xSector = player.getX() / sectorSize;
    ySector = player.getY() / sectorSize;
    tileMap.setPositionImmediately(-xSector * sectorSize, -ySector * sectorSize);
    
    hud = new Hud(player, 39);

    // load music
    JukeBox.load("/Music/bgmusic_spring.mp3", "music_spring");
    System.out.println("1");
    JukeBox.setVolume("music_spring", -10);
    System.out.println("2");
    JukeBox.loop("music_spring", 1000, 1000, JukeBox.getFrames("music_spring") - 1000);
    System.out.println("3");
    JukeBox.load("/Music/bgmusic_summer.mp3", "music_summer");
    System.out.println("4");
    JukeBox.setVolume("music_summer", -10);
    System.out.println("5");
    JukeBox.load("/Music/bgmusic_finish.mp3", "finish");
    System.out.println("6");
    JukeBox.setVolume("finish", -10);

    // load sfx
    System.out.println("7");
    JukeBox.load("/SFX/collect_book.wav", "collect_book");
    System.out.println("8");
    JukeBox.load("/SFX/collect_item.wav", "collect_item");
    System.out.println("9");
    JukeBox.load("/SFX/mapmove.wav", "mapmove");
    System.out.println("10");
    JukeBox.load("/SFX/splash.wav", "watersplash");
    System.out.println("11");
    JukeBox.load("/SFX/remove_wood.wav", "remove_wood");
    System.out.println("12");
    JukeBox.load("/SFX/remove_rock.wav", "remove_rock");
    System.out.println("13");
    JukeBox.load("/SFX/tilechange.wav", "tilechanges");
    System.out.println("14");
    JukeBox.load("/SFX/open_gate.wav", "open_gate");
    System.out.println("15");
    JukeBox.load("/SFX/finish_semester.wav", "finish_semester");
    System.out.println("16");
    JukeBox.load("/SFX/press_key.wav", "press_key");
    System.out.println("17");

    currentPage = -1;
    prevPage = -1;
    
    boxes = new ArrayList<Rectangle>();
    eventStart = true;
    eventStart();
      
  }

  private void populateBooks() {
    // Semester 1
    insertBook("DASPROG", 29, 13);
    insertBook("MAT 1", 27, 26);
    insertBook("FIS 1", 30, 33);
    insertBook("KIM", 14, 25);
    insertBook("BIN", 13, 35);
    insertBook("PANCASILA", 36, 3);
    // Semester 2
    insertBook("STRUKDAT", 35, 26);
    insertBook("SISDIG", 26, 3);
    insertBook("FIS 2", 3, 3);
    insertBook("MAT 2", 9, 14);
    insertBook("BIG", 3, 33);
    insertBook("AGAMA", 12, 20);
    insertBook("KWN", 3, 23);
    // Semester 2 Akselerasi
    insertBook("MATDIS", 20, 14);
    // Semester 3
    insertBook("ALIN", 41, 25);
    insertBook("KOMNUM", 49, 30);
    insertBook("ORKOM", 69, 29);
    insertBook("PBO", 77, 1);
    insertBook("SBD", 75, 9);
    // Semester 3 Akselerasi
    insertBook("PROBSTAT", 70, 9);
    insertBook("PAA", 75, 28);
    // Semester 4
    insertBook("APSI", 69, 6);
    insertBook("KB", 59, 1);
    insertBook("MBD", 43, 10);
    insertBook("SISOP", 66, 37);
    // Semester 4 Akselerasi
    insertBook("IMK", 40, 38);
    insertBook("TGO", 43, 16);
    insertBook("PBKK", 51, 8);

  }

  private void insertBook(String s, int y, int x) {
    Book b = new Book(tileMap, s);
    b.setTilePosition(y, x);
    books.add(b);
    if(s.equals("DASPROG"))b.addChange(new int[]{31,17,1}); 
    if(s.equals("KIM"))b.addChange(new int[]{31,21,1}); 
    if(s.equals("SISDIG")){
      b.addChange(new int[]{27,7,1}); 
      b.addChange(new int[]{28,7,1}); 
    } 
    if(s.equals("ALIN"))b.addChange(new int[]{43,24,6});
    if(s.equals("PBO"))b.addChange(new int[]{72,2,6});
    if(s.equals("APSI"))b.addChange(new int[]{63,6,6});
    if(s.equals("PBKK")){
      b.addChange(new int[]{47,3,6});
      b.addChange(new int[]{48,3,6});
    }
    
    
  }
  
  private void populateItems() {
    
    Item item;

    if (!firstItem) {
      int x;
      item = new Item(tileMap);
      item.setType(Item.KEY);
      x = player.passedSemester4() ? 77 : 37;
      item.setTilePosition(x, 35);
      items.add(item);
      updateItem = true;
      lastItem = player.passedSemester4() ? true : false;
    }
    
    if (firstItem) {

      item = new Item(tileMap);
      item.setType(Item.AXE);
      item.setTilePosition(26, 37);
      items.add(item);
      
      item = new Item(tileMap);
      item.setType(Item.BOAT);
      item.setTilePosition(12, 4);
      items.add(item);
  
      item = new Item(tileMap);
      item.setType(Item.PICKAXE);
      item.setTilePosition(60, 21);
      items.add(item);
  
      item = new Item(tileMap);
      item.setType(Item.KEY);
      item.setTilePosition(20, 22);
      items.add(item);

      firstItem = false;
    }
    
  }

  private boolean eligible(String s) {
    if (s.equals("MATDIS") && player.passedSemester1())
      return true;
    if ((s.equals("PROBSTAT") || s.equals("PAA")) && player.passedSemester2())
      return true;
    if ((s.equals("IMK") || s.equals("PBKK") || s.equals("TGO")) && player.passedSemester3()) {
      // player.setTotalCredit(player.getTotalCredit() + 3);
      // accel = true;
      return true;
    }
    return false;
  }

  private boolean changeMusic() {
    if (summer && !JukeBox.isPlaying("music_summer")) {
      JukeBox.stop("music_spring");
      JukeBox.loop("music_summer", 1000, 1000, JukeBox.getFrames("music_summer") - 1000);
      return true;
    }
    if (spring && !JukeBox.isPlaying("music_spring")) {
      JukeBox.stop("music_summer");
      JukeBox.loop("music_spring", 1000, 1000, JukeBox.getFrames("music_spring") - 1000);
      return true;
    }
    return false;
  }
  
  public void update() {
    
    handleInput();

    if (!player.inSpring()) {
      spring = false;
      summer = true;
      if (changeMusic()) summer = false;
    }
    if (player.inSpring()) {
      spring = true;
      summer = false;
      if (changeMusic()) spring = false;
    }
    
    if (eventStart) eventStart();
    if (eventFinish) eventFinish();
    if (player.passedSemester2() && !updateItem) populateItems();
    if (player.passedSemester4() && !lastItem) populateItems();
    if (player.passedSemester2() && !updateCredit && player.hasKey()) {
      hud.setCreditUnit(player.getTotalCredit());
      updateCredit = true;
    }
    // if (accel) {
    //   hud.setCreditUnit(player.getTotalCredit());
    //   accel = false;
    // }
    
    // if (player.currentCredit() == player.getTotalCredit()) {
    //   eventFinish = blockInput = true;
    // }
    System.out.println(player.finish());
    if (player.finish()) {
      eventFinish = blockInput = true;
    }
    
    int oldX = xSector;
    int oldY = ySector;
    xSector = player.getX() / sectorSize;
    ySector = player.getY() / sectorSize;
    tileMap.setPosition(-xSector * sectorSize, -ySector * sectorSize);
    tileMap.update();

    if (oldX != xSector || oldY != ySector) {
      JukeBox.play("mapmove");
    }
    
    if (tileMap.isMoving()) return;
    
    player.update();
    
    for (int i = 0; i < books.size(); i++) {
      
      Book b = books.get(i);
      b.update();

      boolean hasFound = false;
      
      if (player.intersects(b)) {

        if ((player.courseInSemester2(b.getCourseName()) || player.courseInSemester3(b.getCourseName()) || 
            player.courseInSemester4(b.getCourseName()) || player.courseInSemester6(b.getCourseName())) && 
            !player.passedSemester1()) {
          hud.hasNotFinished(1);
          return;
        }

        if (!eligible(b.getCourseName()) && (player.courseInSemester3(b.getCourseName()) ||
            player.courseInSemester4(b.getCourseName()) || player.courseInSemester6(b.getCourseName())) &&
            !player.passedSemester2()) {
          hud.hasNotFinished(2);
          return;
        }

        if (!eligible(b.getCourseName()) && (player.courseInSemester4(b.getCourseName()) ||
            player.courseInSemester6(b.getCourseName())) && !player.passedSemester3()) {
          hud.hasNotFinished(3);
          return;
        }

        if (eligible(b.getCourseName()) && player.passedSemester3()) {
          player.setTotalCredit(player.getTotalCredit() + 3);
          hud.setCreditUnit(player.getTotalCredit());
        }
        
        player.takeCourse(b.getCourseName());
        // if (b.getCourseName().equals("IMK")) 
        //   player.setTotalCredit(player.getTotalCredit() + 3);
        // if ((b.getCourseName().equals("IMK") || b.getCourseName().equals("PBKK") || b.getCourseName().equals("TGO"))) {
        //   player.setTotalCredit(player.getTotalCredit() + 3);
        //   hud.setCreditUnit(player.getTotalCredit());
        // }
        JukeBox.play("collect_book");
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
        if (ali.size() != 0) {
          JukeBox.play("tilechanges");
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
        JukeBox.play("collect_item");
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
    if (currentPage == 4 && player.getCourseTaken().size() > 24) {
      int positionX = 16;
      int positionY = 16;
      String sks = new String();
      for (int i = 24;  i < 28; i++) {
        if (player.isTwoCredits(player.getCourseTaken().get(i))) sks = "2";
        if (player.isThreeCredits(player.getCourseTaken().get(i))) sks = "3";
        if (player.isFourCredits(player.getCourseTaken().get(i))) sks = "4";
        Content.drawString(g, player.getCourseTaken().get(i), positionX, positionY);
        Content.drawString(g, sks, 90, positionY);
        positionY += 16;
        if (i == player.getCourseTaken().size() - 1 && player.getCourseTaken().size() < 28) break;
      }
    }

    g.setColor(java.awt.Color.BLACK);
    for (int i = 0; i < boxes.size(); i++) {
      g.fill(boxes.get(i));
    }

  }
  
  public void handleInput() {
    if (Keys.isPressed(Keys.ESCAPE)) {
      JukeBox.play("press_key");
      gsm.setPaused(true);
    }
    if (Keys.isPressed(Keys.F2)) {
      JukeBox.play("press_key");
      if (player.getCourseTaken().size() > 24 && prevPage == 3) currentPage = 4;
      else if (player.getCourseTaken().size() > 18 && prevPage == 2) currentPage = 3;
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
      JukeBox.stop("music_spring");
      JukeBox.stop("music_summer");
      JukeBox.play("finish");
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
      if (!JukeBox.isPlaying("finish")) {
        DataTime.setTime(player.getTicks());
        gsm.setState(GameStateManager.FINISH);
      }
    }
  }
  
}
