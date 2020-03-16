import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.impworld.*;      // the abstract World class and the big-bang library
// for imperative worlds
import java.awt.*;
import java.util.ArrayList;     // the arraylist library from java
// and predefined colors (Red, Green, Yellow, Blue, Black, White)

// represents a util class
class Util {
  // generates a centipede body given the length and the speed of the centipede
  ArrayList<BodySeg> generateCentBody(int length, int speed) {
    ArrayList<BodySeg> body = new ArrayList<>();
    for (int index = 0; index < length; index += 1) {
      boolean head = index == length - 1;
      Posn pos = new Posn((index - length + 1) * ITile.WIDTH + ITile.WIDTH / 2,
          ITile.HEIGHT / 2);
      Posn vel = new Posn(speed, 0);
      BodySeg curr = new BodySeg(pos, vel, head, true, true);
      body.add(curr);
    }
    return body;
  }

  // takes in an element and generates an arraylist with solely that element
  <T> ArrayList<T> singletonList(T item) {
    ArrayList<T> list = new ArrayList<>();
    list.add(item);
    return list;
  }

  // generates an arraylist of grass tiles given the width and height of the board to represent
  // the board
  ArrayList<ITile> generateGrassBoard(int row, int col) {
    ArrayList<ITile> garden = new ArrayList<>();
    for (int index = 0; index < row; index += 1) {
      this.generateGrassRow(garden, index, col);
    }
    return garden;
  }

  // EFFECT: modifies the arraylist given to add the current row of grass tiles
  // generates an arraylist of col amount of grass tiles for a given row number
  void generateGrassRow(ArrayList<ITile> garden, int row_ind, int col) {
    for (int index = 0; index < col; index += 1) {
      garden.add(new GrassTile(row_ind * ITile.WIDTH + ITile.WIDTH / 2,
          index * ITile.HEIGHT + ITile.HEIGHT / 2));
    }
  }
}

// represents a tile and introduces the tile's height and width constants
interface ITile {
  int HEIGHT = 40;

  int WIDTH = 40;

  // draws this tile onto the world scene given
  void draw(WorldScene s);

  //are these the tile's coordinates?
  boolean samePos(Posn pos);

  // in effect "replaces" this tile with a new tile with the same position given the
  // mouse button name and the bottom column of the board
  ITile replaceTile(String bName, int botCol);

  // to return the result of applying the given visitor to this tile
  <R> R accept(ITileVisitor<R> visitor);
}

// implements ITile and introduces the row and col fields, which represent x and y indices
abstract class ATile implements ITile {
  int row;
  int col;

  // the constructor
  ATile(int row, int col) {
    this.row = row;
    this.col = col;
  }

  // draws this ATile - to be implemented by classes that extend ATile
  public abstract void draw(WorldScene s);

  // is this ATile at the same position of the given Posn?
  public boolean samePos(Posn pos) {
    return this.row == pos.x && this.col == pos.y;
  }

  // if this tile's indices match a given set of indices, returns a GrassTile with
  // those indices
  public ITile replaceTile(String bName, int botCol) {
    /*
     * replaceTile template: everything in the ATile template, plus Fields of
     * parameters: none Methods on parameters: none
     */
    if (bName.equals("LeftButton") && this.col != botCol) {
      return new GrassTile(this.row, this.col);
    } else {
      return this;
    }
  }

  // to return the result of applying the given visitor to this ATile
  public abstract <R> R accept(ITileVisitor<R> visitor);
}

// represents a tile with no obstacles on it
class GrassTile extends ATile {
  // the constructor
  GrassTile(int row, int col) {
    super(row, col);
  }

  // draws a GrassTile, a solid green cube and a black outline, onto the given world scene
  public void draw(WorldScene s) {
    WorldImage outline = new RectangleImage(WIDTH, HEIGHT, OutlineMode.SOLID, Color.BLACK);
    WorldImage grass = new RectangleImage(WIDTH - 1,
        HEIGHT - 1, OutlineMode.SOLID, Color.GREEN);
    s.placeImageXY(outline, this.row, this.col);
    s.placeImageXY(grass, this.row, this.col);
  }

  // if this tile's indices match a given set of indices, returns a Dandelion
  // if left botton is clicked) or Pebble (if right botton is clicked) with those
  // indices
  public ITile replaceTile(String bName, int botCol) {
    /*
     * replaceTile template: everything in the ATile template, plus Fields of
     * parameters: none Methods on parameters: none
     */
    if (bName.equals("LeftButton") && this.col != botCol) {
      return new DandelionTile(this.row, this.col);
    }
    else if (bName.equals("RightButton") && this.col != botCol) {
      return new PebbleTile(this.row, this.col);
    }
    return this;
  }

  // to return the result of applying the given visitor to this GrassTile
  public <R> R accept(ITileVisitor<R> visitor) {
    return visitor.visitGrass(this);
  }
}

// represents a tile with a pebble tile
class PebbleTile extends ATile {
  // the constructor
  PebbleTile(int row, int col) {
    super(row, col);
  }

  // draws a PebbleTile, a solid gray cube and a black outline, onto the given world scene
  public void draw(WorldScene s) {
    WorldImage outline = new RectangleImage(WIDTH, HEIGHT, OutlineMode.SOLID, Color.BLACK);
    WorldImage grass = new RectangleImage(WIDTH - 1,
        HEIGHT - 1, OutlineMode.SOLID, Color.GRAY);
    s.placeImageXY(outline, this.row, this.col);
    s.placeImageXY(grass, this.row, this.col);
  }

  // to return the result of applying the given visitor to this PebbleTile
  public <R> R accept(ITileVisitor<R> visitor) {
    return visitor.visitPeb(this);
  }
}

// represents a tile with a dandelion tile
class DandelionTile extends ATile {
  // the constructor
  DandelionTile(int row, int col) {
    super(row, col);
  }

  // draws a DandelionTile, a solid yellow cube and a black outline, onto the given world scene
  public void draw(WorldScene s) {
    WorldImage outline = new RectangleImage(WIDTH, HEIGHT, OutlineMode.SOLID, Color.BLACK);
    WorldImage grass = new RectangleImage(WIDTH - 1,
        HEIGHT - 1, OutlineMode.SOLID, Color.YELLOW);
    s.placeImageXY(outline, this.row, this.col);
    s.placeImageXY(grass, this.row, this.col);
  }

  // to return the result of applying the given visitor to this DandelionTile
  public <R> R accept(ITileVisitor<R> visitor) {
    return visitor.visitDan(this);
  }
}

// represents the player in the centipede game
class Gnome {
  int x;
  int y;
  int speed;

  // the constructor
  Gnome(int x, int y, int speed) {
    this.x = x;
    this.y = y;
    this.speed = speed;
  }

  // EFFECT: changes the given world scene by adding this gnome onto it
  // draws this gnome onto the given world scene
  void draw(WorldScene s) {
    WorldImage player =
        new StarImage(ITile.WIDTH / 2 - 1, 8,
            2, OutlineMode.SOLID, Color.ORANGE);
    s.placeImageXY(player, this.x, this.y);
  }

  // moves the gnome (towards the playerDirection specified by the key) by one unit
  // (speed).
  // the gnome stays if it tries to move off the edge of the screen.
  void moveCell(String key, int edge) {
    /*
     * TEMPLATE: Everything in the gnome class template plus Methods on parameters:
     * key.equals(String) -- boolean
     */
    if (key.equals("left") && this.x - this.speed >= ITile.WIDTH / 2) {
      this.x = this.x - ITile.WIDTH;
    } else if (key.equals("right") && this.x + this.speed <= edge - ITile.WIDTH / 2) {
      this.x = this.x + ITile.WIDTH;
    }
  }

  // moves the gnome (towards the playerDirection specified by the key) by one unit (speed).
  // the gnome stays if it tries to move off the edge of the screen.
  void move(String key, int rightEdge, int botEdge) {
    /*
     * TEMPLATE: Everything in the gnome class template plus Methods on parameters:
     * key.equals(String) -- boolean
     */
    if (key.equals("left") && this.x - this.speed >= ITile.WIDTH / 2) {
      this.x -= this.speed;
    }
    else if (key.equals("right") && this.x + this.speed <= rightEdge - ITile.WIDTH/2) {
      this.x += this.speed;
    }
    else if (key.equals("up") &&
        this.y - this.speed >= botEdge - 2 * ITile.HEIGHT - ITile.HEIGHT/2) {
      this.y -= this.speed;
    }
    else if (key.equals("down") && this.y + this.speed <= botEdge - ITile.HEIGHT/2) {
      this.y += this.speed;
    }
  }
}

// represents a centipede in the centipede game
class Centipede {
  ArrayList<BodySeg> body; // represents all the body segments of this centipede
  // NOTE: the centipede's head is at the end of the list
  int speed; // how fast the centipede should be moving
  ArrayList<Posn> encountered; // represents a list of obstacles that this centipede has encountered

  // the constructor
  Centipede(ArrayList<BodySeg> body, int speed, ArrayList<Posn> encountered) {
    if (body.size() == 0) {
      throw new IllegalArgumentException("Centipede cannot have an empty body");
    }
    this.body = body;
    this.speed = speed;
    this.encountered = encountered;
  }

  // the default constructor - constructs the starting centipede in the centipede game
  Centipede(int length) {
    this(new Util().generateCentBody(length, ITile.WIDTH / 10),
        ITile.WIDTH / 10, new ArrayList<>());
  }

  // EFFECT: changes the given world scene by adding this centipede onto it
  // draws this centipede onto the given world scene
  void draw(WorldScene s) {
    for (BodySeg bodyPart : this.body) {
      bodyPart.draw(s);
    }
  }

  // EFFECT: changes the all the elements in this centipede's list of body positions,
  // essentially moving it along in the world
  // moves the centipede along the board in the world
  void move(int width, int height, ArrayList<ITile> garden) {
    BodySeg head = this.body.get(this.body.size() - 1);
    if (head.aheadDandelion(garden) && !head.trapped(this.encountered, width)) {
      this.encountered.add(head.nextTilePosn());
    }
    for (BodySeg bodySeg : this.body) {
      bodySeg.move(width, height, this.speed, this.encountered);
    }
  }
}

//represents a body segment of a centipede
class BodySeg {
  Posn pos; // represents the position of this body segment
  Posn velocity; // represents the velocity of this body segment
  boolean head; // is this body segment the head?
  boolean down; // is this body segment going down?
  boolean right; // is this body segment going right?

  // the constructor
  BodySeg(Posn pos, Posn velocity, boolean head, boolean down, boolean right) {
    this.pos = pos;
    this.velocity = velocity;
    this.head = head;
    this.down = down;
    this.right = right;
  }

  // EFFECT: changes the given world scene by adding this body segment onto it
  // draws this body segment onto the given world scene
  void draw(WorldScene s) {
    Color color = Color.BLUE;
    if (this.head) {
      color = Color.CYAN;
    }

    WorldImage bodyPart = new CircleImage(ITile.WIDTH / 2 - 1, OutlineMode.SOLID, color);
    s.placeImageXY(bodyPart, this.pos.x, this.pos.y);
  }

  // EFFECT: changes the value of whether or not this body segment is a head
  // (changes the head boolean)
  // turns this body segment into a head
  void toHead() {
    this.head = true;
  }

  // EFFECT: changes the position and velocity of this body segment
  // moves this body segment
  void move(int width, int height, int speed, ArrayList<Posn> encountered) {
    boolean leftEdge = this.pos.x == ITile.WIDTH / 2;
    boolean rightEdge = this.pos.x == width - ITile.WIDTH / 2;
    boolean topRow = this.pos.y == ITile.HEIGHT / 2;
    boolean botRow = this.pos.y == height - ITile.HEIGHT / 2;
    boolean inRow = (this.pos.y - ITile.HEIGHT / 2) % ITile.HEIGHT == 0;

    if (leftEdge && inRow && !this.right
        || rightEdge && inRow && this.right
        || this.nextEncountered(encountered)) {
      if (this.down && botRow || !this.down && topRow) {
        this.down = !this.down;
      }

      if (!this.down) {
        speed *= -1;
      }

      this.right = !this.right;
      this.velocity = new Posn(0, speed);

    } else if (inRow && this.velocity.x == 0) {
      if (!this.right) {
        speed *= -1;
      }
      this.velocity = new Posn(speed, 0);
    }

    this.pos = new Posn(this.pos.x + this.velocity.x, this.pos.y + this.velocity.y);
  }

  // is there a position to the right or left of this body segment (depending on direction)
  // where it will collide in the given list?
  boolean nextEncountered(ArrayList<Posn> encountered) {
    Posn pos = new Posn(this.pos.x + ITile.WIDTH, this.pos.y);
    if (!this.right) {
      pos = new Posn(this.pos.x - ITile.WIDTH, this.pos.y);
    }
    for (Posn p : encountered) {
      if (p.equals(pos)) {
        return true;
      }
    }
    return false;
  }

  // gives the next position (depending on direction) of this body segment
  // NOTE: this will give an invalid position if the centipede is at one of the edges in which
  // the body segment maintains its direction towards that edge
  Posn nextTilePosn() {
    if (this.right) {
      return new Posn(this.pos.x + ITile.WIDTH, this.pos.y);
    }
    return new Posn(this.pos.x - ITile.WIDTH, this.pos.y);
  }

  // gives the previous position (depending on direction) of this body segment
  // NOTE: this will give an invalid position if the centipede is at one of the edges in which
  // the body segment maintains its opposite direction towards that edge
  Posn prevTilePosn() {
    if (this.right) {
      return new Posn(this.pos.x - ITile.WIDTH, this.pos.y);
    }
    return new Posn(this.pos.x + ITile.WIDTH, this.pos.y);
  }

  // is there a dandelion ahead of this body segment?
  boolean aheadDandelion(ArrayList<ITile> garden) {
    IsDandelion isDandelion = new IsDandelion();
    Posn ahead = new Posn(this.pos.x + ITile.WIDTH, this.pos.y);
    if (!this.right) {
      ahead = new Posn(this.pos.x - ITile.WIDTH, this.pos.y);
    }
    for (ITile tile : garden) {
      if (isDandelion.apply(tile) && tile.samePos(ahead)) {
        return true;
      }
    }
    return false;
  }

  // can this BodySeg be trapped by the board?
  boolean trapped(ArrayList<Posn> encountered, int width) {
    Posn ahead = this.nextTilePosn();
    Posn ahead_away2y = new Posn(ahead.x, ahead.y - 2 * ITile.HEIGHT);
    Posn prev = this.prevTilePosn();
    Posn prev_away1y = new Posn(prev.x, prev.y - ITile.HEIGHT);
    if (!this.down) {
      ahead_away2y = new Posn(ahead.x, ahead.y + 2 * ITile.HEIGHT);
      prev_away1y = new Posn(ahead.x, ahead.y + ITile.HEIGHT);
    }

    boolean obstacleTwoYNext = encountered.contains(ahead_away2y);
    boolean obstacleOneYPrev = encountered.contains(prev_away1y) || prev.x < 0 || prev.x > width;

    return obstacleTwoYNext && obstacleOneYPrev;
  }
}

// represents the actual game world when the player can control the gnome
class CGameState extends GameState {
  ArrayList<Centipede> cents; // represents all the centipedes in the current world
  ArrayList<ITile> garden; // represents all the tiles in the current world
  Posn playerDirection; // -1 if player is moving left, 0 is player is not moving, and 1 if player is
  // moving right for the x component, and the same respectively for moving down and up
  Gnome gnome;
  int width;
  int height;

  // the constructor
  CGameState(ArrayList<Centipede> cents, ArrayList<ITile> garden, Posn playerDirection, Gnome gnome,
             int width, int height) {
    if (width < 2 * ITile.WIDTH || height < 2 * ITile.HEIGHT) {
      throw new IllegalArgumentException("Invalid dimensions");
    }
    this.cents = cents;
    this.garden = garden;
    this.playerDirection = playerDirection;
    this.gnome = gnome;
    this.width = width;
    this.height = height;
  }

  // the default constructor, only requiring how big the board should be
  CGameState(int x, int y, ArrayList<ITile> garden, Gnome gnome) {
    this(new Util().singletonList(new Centipede(10)), garden,
        new Posn(0, 0), gnome, ITile.WIDTH * x,
        ITile.HEIGHT * y);
  }

  @Override
  // TODO : elaborate on your effect statement
  // EFFECT: changes all the fields except width and height
  // moves every element in the game accordingly after each tick
  public void onTick() {
    for (Centipede c : this.cents) {
      c.move(this.width, this.height, this.garden);
    }

    this.movePlayer();
  }

  // EFFECT: modifies the player position of this CGameState based on the player direction
  // moves the player accordingly based on the key input the user gave
  void movePlayer() {
    if (this.playerDirection.x == 1) {
      this.gnome.move("right", this.width, this.height);
    }
    if (this.playerDirection.x == -1) {
      this.gnome.move("left", this.width, this.height);
    }

    if (this.playerDirection.y == 1) {
      this.gnome.move("up", this.width, this.height);
    }
    if (this.playerDirection.y == -1) {
      this.gnome.move("down", this.width, this.height);
    }
  }

  @Override
  // draws all the elements in the game
  public WorldScene makeScene() {
    WorldScene s = new WorldScene(this.width, this.height);
    for (ITile tile : this.garden) {
      tile.draw(s);
    }
    for (Centipede c : this.cents) {
      c.draw(s);
    }
    this.gnome.draw(s);
    return s;
  }

  @Override
  // EFFECT: modifies the player direction of this CGameState based on the key given by the user
  // moves the player accordingly based on the key input the user gave
  public void onKeyEvent(String s) {
    if (s.equals("left")) {
      this.playerDirection = new Posn(-1, this.playerDirection.y);
    }
    if (s.equals("right")) {
      this.playerDirection = new Posn(1, this.playerDirection.y);
    }

    if (s.equals("up")) {
      this.playerDirection = new Posn(this.playerDirection.x, 1);
    }
    if (s.equals("down")) {
      this.playerDirection = new Posn(this.playerDirection.x, -1);
    }
  }

  @Override
  // resets the player's direction to 0 (means not moving) for both components
  public void onKeyReleased(String s) {
    if (s.equals("left") || s.equals("right")) {
      this.playerDirection = new Posn(0, this.playerDirection.y);
    }

    if (s.equals("up") || s.equals("down")) {
      this.playerDirection = new Posn(this.playerDirection.x, 0);
    }
  }

  // continues this CGameState to be used in GameMaster
  public CGameState toCGameState() {
    return this;
  }
}