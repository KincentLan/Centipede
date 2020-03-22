import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.impworld.*;      // the abstract World class and the big-bang library
// for imperative worlds
import java.awt.*;
import java.util.ArrayList;     // the arraylist library from java
// and predefined colors (Red, Green, Yellow, Blue, Black, White)

// represents a util class
class Util {
  // generates a centipede body given the length and the currSpeed of the centipede
  ArrayList<BodySeg> generateCentBody(int length, int speed) {
    ArrayList<BodySeg> body = new ArrayList<>();
    for (int index = 0; index < length; index += 1) {
      boolean head = index == length - 1;
      Posn pos = new Posn((index - length + 1) * ITile.WIDTH + ITile.WIDTH / 2,
          ITile.HEIGHT / 2);
      Posn vel = new Posn(speed, 0);
      BodySeg curr = new BodySeg(pos, vel, head, true, true, 0);
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

  // gives an arraylist that is a copy of the given one
  <T> ArrayList<T> copy(ArrayList<T> src) {
    ArrayList<T> copy = new ArrayList<>();
    for (T item : src) {
      copy.add(item);
    }
    return copy;
  }

  // EFFECT: modifies the first given arraylist to include all the items in the second arraylist
  // in essence "appending" the lists together
  // appends the lists together
  <T> void append(ArrayList<T> src1, ArrayList<T> src2) {
    for (T item : src2) {
      src1.add(item);
    }
  }

  boolean inRange(Posn pos, Posn tilePosn) {
    return Math.abs(tilePosn.x - pos.x) <= ITile.WIDTH/2
        && Math.abs(tilePosn.y - pos.y) <= ITile.HEIGHT/2;
  }
}

// represents a tile and introduces the tile's height and width constants
interface ITile {
  int HEIGHT = 40;

  int WIDTH = 40;

  int DEF_HP = 3;

  // draws this tile onto the world scene given
  void draw(WorldScene s);

  //are these the tile's coordinates?
  boolean samePos(Posn pos);

  // in effect "replaces" this tile with a new tile with the same position given the
  // mouse button name and the bottom column of the board
  ITile replaceTile(String bName, int botCol);

  // to return the result of applying the given visitor to this tile
  <R> R accept(ITileVisitor<R> visitor);

  // is this tile in range of the given posn?
  boolean inRange(Posn pos);

  // lowers the HP of this ITile if it has an HP unit
  void lowerHP();

  // is the HP of this ITile zero or less?
  boolean noHP();

  ArrayList<Posn> hitBox(int width);
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

  @Override
  // draws this ATile - to be implemented by classes that extend ATile
  public abstract void draw(WorldScene s);

  @Override
  // is this ATile at the same position of the given Posn?
  public boolean samePos(Posn pos) {
    return this.row == pos.x && this.col == pos.y;
  }

  @Override
  // in effect, this gives the "replacement" of this ATile with a new tile
  // with the same position given the mouse button name and the bottom column of the board
  public ITile replaceTile(String bName, int botCol) {
    if (bName.equals("LeftButton") && this.col != botCol) {
      return new GrassTile(this.row, this.col);
    } else {
      return this;
    }
  }

  @Override
  // is this ATile in range of the given posn?
  public boolean inRange(Posn pos) {
    return Math.abs(this.row - pos.x) <= ITile.WIDTH / 2
        && Math.abs(this.col - pos.y) <= ITile.HEIGHT / 2;
  }

  @Override
  // to return the result of applying the given visitor to this ATile
  public abstract <R> R accept(ITileVisitor<R> visitor);

  @Override
  // by default, an ATile does not have an HP unit, so this method does nothing
  public void lowerHP() {
  }

  @Override
  // by default, an ATile does not have an HP unit, so it does not make sense to have noHP, so
  // it just returns false
  public boolean noHP() {
    return false;
  }

  public ArrayList<Posn> hitBox(int width) {
    ArrayList<Posn> hitBox = new ArrayList<>();
    hitBox.add(new Posn(this.row, this.col));
    return hitBox;
  }
}

// represents a tile with no obstacles on it
class GrassTile extends ATile {
  // the constructor
  GrassTile(int row, int col) {
    super(row, col);
  }

  @Override
  // draws a GrassTile, a solid green cube and a black outline, onto the given world scene
  public void draw(WorldScene s) {
    WorldImage outline = new RectangleImage(WIDTH, HEIGHT, OutlineMode.SOLID, Color.BLACK);
    WorldImage grass = new RectangleImage(WIDTH - 1,
        HEIGHT - 1, OutlineMode.SOLID, Color.GREEN);
    s.placeImageXY(outline, this.row, this.col);
    s.placeImageXY(grass, this.row, this.col);
  }

  @Override
  // if this tile's indices match a given set of indices, returns a Dandelion
  // if left botton is clicked) or Pebble (if right botton is clicked) with those
  // indices
  public ITile replaceTile(String bName, int botCol) {
    /*
     * replaceTile template: everything in the ATile template, plus Fields of
     * parameters: none Methods on parameters: none
     */
    if (bName.equals("LeftButton") && this.col != botCol) {
      return new DandelionTile(this.row, this.col, DEF_HP);
    } else if (bName.equals("RightButton") && this.col != botCol) {
      return new PebbleTile(this.row, this.col);
    }
    return this;
  }

  @Override
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

  @Override
  // draws a PebbleTile, a solid gray cube and a black outline, onto the given world scene
  public void draw(WorldScene s) {
    WorldImage outline = new RectangleImage(WIDTH, HEIGHT, OutlineMode.SOLID, Color.BLACK);
    WorldImage grass = new RectangleImage(WIDTH - 1,
        HEIGHT - 1, OutlineMode.SOLID, Color.GRAY);
    s.placeImageXY(outline, this.row, this.col);
    s.placeImageXY(grass, this.row, this.col);
  }

  @Override
  // to return the result of applying the given visitor to this PebbleTile
  public <R> R accept(ITileVisitor<R> visitor) {
    return visitor.visitPeb(this);
  }

  public ArrayList<Posn> hitBox(int width) {
    boolean leftEdge = ITile.WIDTH/2 == this.row;
    boolean rightEdge = width - ITile.WIDTH/2 == this.row;
    boolean topEdge = ITile.HEIGHT/2 == this.col;
    // pebbles will never be in the bottom edge

    ArrayList<Posn> pebbleHitBox = new ArrayList<>();
    pebbleHitBox.add(new Posn(this.row, this.col));

    if (!leftEdge) {
      pebbleHitBox.add(new Posn(this.row - ITile.WIDTH, this.col));
    }
    if (!rightEdge) {
      pebbleHitBox.add(new Posn(this.row + ITile.WIDTH, this.col));
    }
    if (!topEdge) {
      pebbleHitBox.add(new Posn(this.row, this.col - ITile.WIDTH));
    }
    pebbleHitBox.add(new Posn(this.row, this.col + ITile.WIDTH));

    return pebbleHitBox;
  }
}

// represents a tile with a dandelion tile
class DandelionTile extends ATile {
  int hp;

  // the constructor
  DandelionTile(int row, int col, int hp) {
    super(row, col);
    this.hp = hp;
  }

  @Override
  // draws a DandelionTile, with the color depending on the HP, onto the given world scene
  public void draw(WorldScene s) {
    Color color = new Color(255, 255, -(this.hp - DEF_HP) * 75);
    WorldImage outline = new RectangleImage(WIDTH, HEIGHT, OutlineMode.SOLID, Color.BLACK);
    WorldImage grass = new RectangleImage(WIDTH - 1,
        HEIGHT - 1, OutlineMode.SOLID, color);
    s.placeImageXY(outline, this.row, this.col);
    s.placeImageXY(grass, this.row, this.col);
  }

  @Override
  // to return the result of applying the given visitor to this DandelionTile
  public <R> R accept(ITileVisitor<R> visitor) {
    return visitor.visitDan(this);
  }

  @Override
  // lowers the HP of this DandelionTile
  public void lowerHP() {
    this.hp -= 1;
  }

  @Override
  // is the HP of this DandelionTile less than or equal to 0?
  public boolean noHP() {
    return this.hp <= 0;
  }
}

// represents a dart that can be fired in the centipede game
interface IDart {
  // draws this IDart onto the given world scene
  void draw(WorldScene s);

  // updates this IDart (with a new y position) after 1 tick;
  void move();

  // is this IDart off the screen?
  boolean offScreen();

  // is this IDart in the same tile as the given body seg?
  boolean hitBodySeg(BodySeg bodySeg);

  // can this IDart hit the given ITile?
  boolean hitTile(ITile tile);

  // did this dart miss?
  boolean missed();
}

// represents a non-existent dart in the centipede game
class NoDart implements IDart {
  // draws this NoDart onto the given world scene, which in essence does nothing
  public void draw(WorldScene s) {
  }

  // draws this NoDart onto the given world scene, which in essence does nothing
  public void move() {
  }

  // is this NoDart off the screen? Yes, always
  public boolean offScreen() {
    return true;
  }

  // does this NoDart have the given posn? Never.
  public boolean hitBodySeg(BodySeg bodySeg) {
    return false;
  }

  // can this NoDart hit any dandelion tile given the garden? No.
  public boolean hitTile(ITile tile) {
    return false;
  }

  // NoDart cannot miss since there isn't a dart
  public boolean missed() {
    return false;
  }
}

// represents a moving dart in the centipede game
class Dart implements IDart {
  int x; // represents the x position of the dart in pixels
  int y; // represents the y position of the dart in pixels
  int speed;

  // the constructor
  Dart(int x, int y, int speed) {
    this.x = x;
    this.y = y;
    this.speed = speed;
  }

  // EFFECT: modifies the given world scene to include this Dart
  // draws this Dart onto the given world scene
  public void draw(WorldScene s) {
    WorldImage dart = new CircleImage(5, OutlineMode.SOLID, Color.BLACK);
    s.placeImageXY(dart, this.x, this.y);
  }

  // draws this Dart onto the given world scene
  public void move() {
    this.y -= this.speed;
  }

  // is this Dart off the screen?
  public boolean offScreen() {
    return this.y <= 0;
  }

  // is this Dart in the same tile as the given position?
  public boolean hitBodySeg(BodySeg bodySeg) {
    return bodySeg.inRange(new Posn(this.x, this.y));
  }

  // can this Dart hit the given tile?
  public boolean hitTile(ITile tile) {
    return tile.inRange(new Posn(this.x, this.y));
  }

  // did this dart miss anything on the board?
  public boolean missed() {
    return this.y <= 0;
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

  // EFFECT: modifies the position of the gnome, meaning it modifies its x and y fields
  // moves the gnome (towards the playerDirection specified by the key) by one unit
  // (currSpeed), the gnome stays if it tries to move off the edge of the screen.
  void moveCell(String key, int edge) {
    if (key.equals("left") && this.x - this.speed >= ITile.WIDTH / 2) {
      this.x = this.x - ITile.WIDTH;
    } else if (key.equals("right") && this.x + this.speed <= edge - ITile.WIDTH / 2) {
      this.x = this.x + ITile.WIDTH;
    }
  }

  // moves the gnome (towards the playerDirection specified by the key) by one unit (currSpeed).
  // the gnome stays if it tries to move off the edge of the screen.
  void move(String key, int rightEdge, int botEdge, ArrayList<ITile> garden) {
    int x_away = 0;
    int y_away = 0;
    int x_dir = 0;
    int y_dir = 0;

    if (key.equals("left") && this.x - this.speed >= ITile.WIDTH / 2) {
      x_away = -this.speed;
      x_dir = -1;
    } else if (key.equals("right") && this.x + this.speed <= rightEdge - ITile.WIDTH / 2) {
      x_away = this.speed;
      x_dir = 1;
    } else if (key.equals("up") &&
        this.y - this.speed >= botEdge - 2 * ITile.HEIGHT - ITile.HEIGHT / 2) {
      y_away = -this.speed;
      y_dir = -1;
    } else if (key.equals("down") && this.y + this.speed <= botEdge - ITile.HEIGHT / 2) {
      y_away = this.speed;
      y_dir = 1;
    }
    IsDandelion isDandelion = new IsDandelion();
    boolean isDanAhead = false;
    for (ITile tile : garden) {
      isDanAhead = isDanAhead || isDandelion.apply(tile) && this.intersect(tile, x_dir, y_dir);
    }

    if (!isDanAhead) {
      this.x += x_away;
      this.y += y_away;
    }
  }

  // will this player's model intersect with the given tile given the direction
  // the player is moving in?
  boolean intersect(ITile tile, int x_dir, int y_dir) {
    if (x_dir == 0 && y_dir == 0) {
      return false;
    } else if (x_dir != 0) {
      int x_displace = this.x + x_dir * ITile.WIDTH / 2;
      return tile.inRange(new Posn(x_displace, this.y))
          || tile.inRange(new Posn(x_displace, (this.y + 1) - ITile.HEIGHT / 2))
          || tile.inRange(new Posn(x_displace, (this.y - 1) + ITile.HEIGHT / 2));
    }
    int y_displace = this.y + y_dir * ITile.HEIGHT / 2;
    return tile.inRange(new Posn(this.x, y_displace))
        || tile.inRange(new Posn((this.x + 1) - ITile.WIDTH / 2, y_displace))
        || tile.inRange(new Posn((this.x - 1) + ITile.WIDTH / 2, y_displace));
  }

  // generates an dart from the center of the tile where is gnome is at
  IDart generateDart() {
    return new Dart((this.x / ITile.WIDTH) * ITile.WIDTH + ITile.WIDTH / 2, this.y,
        ITile.HEIGHT / 2);
  }
}

// represents a centipede in the centipede game
class Centipede {
  ArrayList<BodySeg> body; // represents all the body segments of this centipede
  // NOTE: the centipede's head is at the end of the list
  int maxSpeed;
  int currSpeed; // how fast the centipede should be moving
  ArrayList<ObstacleList> encountered; // represents all the dandelions this centipede has
  // encountered for every direction it has been in
  ArrayList<ITile> pebsAlreadyOn; // represents all the pebbles this centipede has encountered

  // the constructor
  Centipede(ArrayList<BodySeg> body, int maxSpeed, int currSpeed,
            ArrayList<ObstacleList> encountered, ArrayList<ITile> pebsAlreadyOn) {
    if (body.size() == 0) {
      throw new IllegalArgumentException("Centipede cannot have an empty body");
    }
    if (maxSpeed > ITile.WIDTH|| currSpeed > ITile.WIDTH) {
      throw new IllegalArgumentException("Speed cannot shoot over a tile.");
    }
    this.body = body;
    this.maxSpeed = maxSpeed;
    this.currSpeed = currSpeed;
    this.encountered = encountered;
    this.pebsAlreadyOn = pebsAlreadyOn;
  }

  // the default constructor - constructs the starting centipede in the centipede game
  Centipede(int length) {
    this(new Util().generateCentBody(length, ITile.WIDTH / 10),
        ITile.WIDTH / 10, ITile.WIDTH / 10,
        new Util().singletonList(new ObstacleList(0)),
        new ArrayList<>());
  }

  // EFFECT: changes the given world scene by adding this centipede onto it
  // draws this centipede onto the given world scene
  void draw(WorldScene s) {
    for (BodySeg bodyPart : this.body) {
      bodyPart.draw(s);
    }
  }

  // did the given dart hit any part of this centipede?
  boolean targetHit(IDart dart) {
    for (BodySeg bodySeg : this.body) {
      if (dart.hitBodySeg(bodySeg)) {
        return true;
      }
    }
    return false;
  }

  // gets the position of where the dart hit this centipede
  Posn positionHit(IDart dart) {
    int indexHit = this.getIndexHit(dart);
    return this.body.get(indexHit).tilePosn();
  }

  // splits this centipede into multiple centipedes depending on where the dart hit this
  // centipede
  ArrayList<Centipede> split(IDart dart) {
    int indexHit = this.getIndexHit(dart);
    Util util = new Util();

    if (indexHit == 0) {
      if (this.body.size() == 1) {
        return new ArrayList<>();
      }
      ArrayList<BodySeg> restBody = util.copy(this.body);
      restBody.remove(0);
      restBody.get(restBody.size() - 1).toHead();
      return util.singletonList(new Centipede(restBody, this.currSpeed, this.maxSpeed,
          this.copyEncountered(), new ArrayList<>()));
    } else if (indexHit > 0 && indexHit < this.body.size() - 1) {
      ArrayList<BodySeg> frontBody = new ArrayList<>();
      ArrayList<BodySeg> backBody = new ArrayList<>();
      for (int index = 0; index < this.body.size(); index += 1) {
        if (index < indexHit) {
          frontBody.add(this.body.get(index));
        } else if (index > indexHit) {
          backBody.add(this.body.get(index));
        }
      }
      ArrayList<Centipede> cents = new ArrayList<>();
      frontBody.get(frontBody.size() - 1).toHead();
      backBody.get(backBody.size() - 1).toHead();
      cents.add(new Centipede(frontBody, this.currSpeed, this.maxSpeed,
          this.copyEncountered(), new ArrayList<>()));
      cents.add(new Centipede(backBody, this.currSpeed, this.maxSpeed,
          this.copyEncountered(), new ArrayList<>()));
      return cents;
    } else if (indexHit == this.body.size() - 1) {
      ArrayList<BodySeg> restBody = util.copy(this.body);
      restBody.remove(this.body.size() - 1);
      restBody.get(restBody.size() - 1).toHead();

      return util.singletonList(new Centipede(restBody, this.currSpeed, this.maxSpeed,
          this.copyEncountered(), new ArrayList<>()));
    }
    return util.singletonList(this);
  }

  // copies this list of ObstacleLists to another Array
  ArrayList<ObstacleList> copyEncountered() {
    ArrayList<ObstacleList> cpEncountered = new ArrayList<>();
    for (ObstacleList obl : this.encountered) {
      cpEncountered.add(new ObstacleList(obl));
    }
    return cpEncountered;
  }

  // gets the index of the body segment
  int getIndexHit(IDart dart) {
    for (int index = 0; index < this.body.size(); index += 1) {
      if (dart.hitBodySeg(this.body.get(index))) {
        return index;
      }
    }
    throw new RuntimeException("The dart did not hit any of the body segments.");
  }

  // ASSUMPTION: this method assumes that this centipede has hit a pebble that hasn't been
  // encountered
  // gets the pebble this centipede has hit
  ITile getPebOn(ArrayList<ITile> garden, int width) {
    for (BodySeg bodySeg : this.body) {
      if (bodySeg.hitPebbleTile(garden, this.pebsAlreadyOn, width)) {
        return bodySeg.pebbleTileHit(garden, this.pebsAlreadyOn, width);
      }
    }
    throw new RuntimeException("Pebble not found.");
  }

  // EFFECT: changes the all the elements in this centipede's list of body positions,
  // essentially moving it along in the world
  // moves the centipede along the board in the world
  void move(int width, int height, ArrayList<ITile> garden) {
    if (this.hitPebbleTile(garden, width)
        && !this.pebsAlreadyOn.contains(this.getPebOn(garden, width))) {
      this.pebsAlreadyOn.add(this.getPebOn(garden, width));
      this.halveBodyVelocity();
    }
    BodySeg head = this.body.get(this.body.size() - 1);
    if (head.reverseYDirection(height)) {
      this.encountered.add(head.generateObstacleList());
    }

    ObstacleList headObl = head.obstacleList(this.encountered);
    if (head.aheadDandelion(garden, this.currSpeed)
        && !head.trapped(width, headObl, this.currSpeed)) {
      headObl.addToObstacles(head.nextTilePosn(this.currSpeed));
    }
    for (BodySeg bodySeg : this.body) {
      bodySeg.reverseYDirection(height);
      bodySeg.move(width, this.currSpeed, bodySeg.obstacleList(this.encountered));
    }
    this.removeUnusedObl();
    this.removePebNotOn();
    this.maintainITileDis();
  }

  // did this centipede hit a pebble that hasn't been encountered already?
  boolean hitPebbleTile(ArrayList<ITile> garden, int width) {
    for (BodySeg bodySeg : this.body) {
      if (bodySeg.hitPebbleTile(garden, this.pebsAlreadyOn, width)) {
        return true;
      }
    }
    return false;
  }

  void removePebNotOn() {
    ArrayList<ITile> stillOnPeb = new ArrayList<>();
    for (ITile pebble : this.pebsAlreadyOn) {
      if (!this.hitTile(pebble)) {
        this.doubleBodyVelocity();
      } else {
        stillOnPeb.add(pebble);
      }
    }

    this.pebsAlreadyOn.clear();
    for (ITile pebble : stillOnPeb) {
      this.pebsAlreadyOn.add(pebble);
    }
  }

  boolean hitTile(ITile tile) {
    for (BodySeg bodySeg : this.body) {
      if (bodySeg.hitTile(tile)) {
        return true;
      }
    }
    return false;
  }

  // EFFECT: changes the velocity of each body segment and this centipede's currSpeed
  // halves the velocity of each body segment and this centipede's currSpeed
  void halveBodyVelocity() {
    if (this.currSpeed > 1) {
      this.currSpeed /= 2;
    }
    for (BodySeg bodySeg : this.body) {
      bodySeg.halveVelocity();
    }
  }

  void doubleBodyVelocity() {
    if (this.currSpeed * 2 <= this.maxSpeed) {
      this.currSpeed *= 2;
    } else {
      this.currSpeed = this.maxSpeed;
    }
    for (BodySeg bodySeg : this.body) {
      bodySeg.doubleVelocity(this.maxSpeed);
    }
  }

  // EFFECT: modifies the centipede's encountered to remove any ObstacleList that have not
  // been used
  // removes any unused obstacle lists from this centipede's encountered list
  void removeUnusedObl() {
    ArrayList<ObstacleList> used = new ArrayList<>();
    for (ObstacleList obl : this.encountered) {
      if (this.usedObl(obl)) {
        used.add(obl);
      }
    }
    this.encountered.clear();
    for (ObstacleList obl : used) {
      this.encountered.add(obl);
    }
  }

  // has the given obstacle list been used by any of the body segments?
  boolean usedObl(ObstacleList obl) {
    for (BodySeg bodySeg : this.body) {
      if (bodySeg.sameOblIteration(obl)) {
        return true;
      }
    }
    return false;
  }

  void maintainITileDis() {
    for (int index = this.body.size() - 2; index >= 0; index -= 1) {
       this.body.get(index).maintainITileDis(this.body.get(index + 1));
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
  int iteration; // represents the number of times this body segment has bounced
  // (switched directions)

  // the constructor
  BodySeg(Posn pos, Posn velocity, boolean head, boolean down, boolean right,
          int iteration) {
    this.pos = pos;
    this.velocity = velocity;
    this.head = head;
    this.down = down;
    this.right = right;
    this.iteration = iteration;
  }

  void maintainITileDis(BodySeg next) {
    if (this.pos.y == next.pos.y) {
      if (!this.right && Math.abs(this.pos.x - next.pos.x) != ITile.WIDTH) {
        this.pos = new Posn(next.pos.x + ITile.WIDTH, this.pos.y);
      } else if (this.right && Math.abs(this.pos.x - next.pos.x) != ITile.WIDTH) {
        this.pos = new Posn(next.pos.x - ITile.WIDTH, this.pos.y);
      }
    }
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

  // EFFECT: halves the velocity of this body segment, unless if the velocity is less than or
  // equal to one
  // halves the velocity of this body segment, to be used when the centipede that contains
  // this body segment slows down
  void halveVelocity() {
    int x_vel = this.velocity.x;
    int y_vel = this.velocity.y;
    if (Math.abs(this.velocity.x) > 1) {
      x_vel /= 2;
    }
    if (Math.abs(this.velocity.y) > 1) {
      y_vel /= 2;
    }
    this.velocity = new Posn(x_vel, y_vel);
  }

  // EFFECT: doubles the velocity of this body segment, unless if the velocity is less than or
  // equal to one
  // halves the velocity of this body segment, to be used when the centipede that contains
  // this body segment slows down
  void doubleVelocity(int maxSpeed) {
    int x_vel = this.velocity.x;
    int y_vel = this.velocity.y;
    if (Math.abs(this.velocity.x) * 2 <= maxSpeed) {
      x_vel *= 2;
    } else if (Math.abs(this.velocity.x) * 2 > maxSpeed) {
      x_vel = x_vel / Math.abs(x_vel) * maxSpeed;
    }

    if (Math.abs(this.velocity.y) * 2 <= maxSpeed) {
      y_vel *= 2;
    } else if (Math.abs(this.velocity.y) * 2 > maxSpeed) {
      y_vel = y_vel / Math.abs(y_vel) * maxSpeed;
    }

    this.velocity = new Posn(x_vel, y_vel);
  }

  // determines if the given obstacle list has the same iteration as this body segment
  boolean sameOblIteration(ObstacleList obl) {
    return obl.sameIteration(this.iteration);
  }

  // is this body segment in range of the given posn?
  boolean inRange(Posn p) {
    return Math.abs(this.pos.x - p.x) <= ITile.WIDTH / 2
        && Math.abs(this.pos.y - p.y) <= ITile.HEIGHT / 2;
  }

  // gives the obstacle list that has the same iteration as this body segment
  ObstacleList obstacleList(ArrayList<ObstacleList> encountered) {
    for (ObstacleList obl : encountered) {
      if (obl.sameIteration(this.iteration)) {
        return obl;
      }
    }
    throw new RuntimeException("No such Obstacle List found");
  }

  // generates a new obstacle list with this body segment's iteration
  ObstacleList generateObstacleList() {
    return new ObstacleList(this.iteration);
  }

  // EFFECT: reverses the direction of this body segment potentially
  // returns true if successful, false if otherwise
  boolean reverseYDirection(int height) {
    boolean topRow = this.pos.y == ITile.HEIGHT / 2;
    boolean botRow = this.pos.y == height - ITile.HEIGHT / 2;

    if (this.down && botRow || !this.down && topRow) {
      this.iteration += 1;
      this.down = !this.down;
      return true;
    }
    return false;
  }

  // EFFECT: changes the position and velocity of this body segment
  // moves this body segment
  void move(int width, int speed, ObstacleList obl) {
    boolean leftEdge = Math.abs(this.pos.x - ITile.WIDTH / 2) <= speed / 2;
    boolean rightEdge = Math.abs(this.pos.x - (width - ITile.WIDTH / 2)) <= speed / 2;
    boolean inRow = (this.pos.y - ITile.HEIGHT / 2) % ITile.HEIGHT <= speed / 2;

    if (leftEdge && inRow && !this.right || rightEdge && inRow && this.right
        || this.nextEncountered(obl, speed) && inRow) {
      this.pos = new Posn((this.pos.x / ITile.WIDTH) * ITile.WIDTH + ITile.WIDTH / 2,
          this.pos.y);
      if (!this.down) {
        speed *= -1;
      }

      this.right = !this.right;
      this.velocity = new Posn(0, speed);

    } else if (inRow && this.velocity.x == 0) {
      this.pos = new Posn(this.pos.x,
          (this.pos.y / ITile.HEIGHT) * ITile.HEIGHT + ITile.HEIGHT / 2);
      if (!this.right) {
        speed *= -1;
      }
      this.velocity = new Posn(speed, 0);
    }

    this.pos = new Posn(this.pos.x + this.velocity.x, this.pos.y + this.velocity.y);
  }

  // is there a position to the right or left of this body segment (depending on direction)
  // where it will collide in the given list?
  boolean nextEncountered(ObstacleList obl, int speed) {
    Posn pos = this.nextTilePosn(speed);
    return obl.inObstacles(pos);
  }

  // gets the tile position of this centipede
  Posn tilePosn() {
    int x = (this.pos.x / ITile.WIDTH) * ITile.WIDTH + ITile.WIDTH / 2;
    int y = (this.pos.y / ITile.HEIGHT) * ITile.HEIGHT + ITile.HEIGHT / 2;
    if (this.right && this.pos.x % ITile.WIDTH > ITile.WIDTH / 2) {
      x += ITile.WIDTH;
    } else if (!this.right && this.pos.x % ITile.WIDTH < ITile.WIDTH / 2) {
      x -= ITile.WIDTH;
    }
    return new Posn(x, y);
  }

  Posn center(int speed) {
    Posn pos = new Posn(this.pos.x, this.pos.y);
    if (Math.abs(this.pos.x - ITile.WIDTH/2) % ITile.WIDTH <= speed/2) {

      pos = new Posn((this.pos.x / ITile.WIDTH) * ITile.WIDTH + ITile.WIDTH / 2,
          (this.pos.y / ITile.HEIGHT) * ITile.HEIGHT + ITile.HEIGHT / 2);
    }
    return pos;
  }
  // gives the next position (depending on direction) of this body segment
  // NOTE: this will give an invalid position if the centipede is at one of the edges in which
  // the body segment maintains its direction towards that edge
  Posn nextTilePosn(int speed) {
    Posn ahead = this.center(speed);
    if (!this.right) {
      ahead = new Posn(ahead.x - ITile.WIDTH, ahead.y);
    }
    else {
      ahead = new Posn(ahead.x + ITile.WIDTH, ahead.y);
    }
    return ahead;
  }

  // gives the previous position (depending on direction) of this body segment
  // NOTE: this will give an invalid position if the centipede is at one of the edges in which
  // the body segment maintains its opposite direction towards that edge
  Posn prevTilePosn(int speed) {
    Posn behind = this.center(speed);
    if (!this.right) {
      behind = new Posn(behind.x + ITile.WIDTH, behind.y);
    }
    else {
      behind = new Posn(behind.x - ITile.WIDTH, behind.y);
    }
    return behind;
  }

  // is there a dandelion ahead of this body segment?
  boolean aheadDandelion(ArrayList<ITile> garden, int speed) {
    IsDandelion isDandelion = new IsDandelion();
    Posn ahead = this.nextTilePosn(speed);
    for (ITile tile : garden) {
      if (isDandelion.apply(tile) && tile.samePos(ahead)) {
        return true;
      }
    }
    return false;
  }

  // can this BodySeg be trapped by the board?
  boolean trapped(int width, ObstacleList obl, int speed) {
    Posn ahead = this.nextTilePosn(speed);
    Posn ahead_away2y = new Posn(ahead.x, ahead.y - 2 * ITile.HEIGHT);
    Posn prev = this.prevTilePosn(speed);
    Posn prev_away1y = new Posn(prev.x, prev.y - ITile.HEIGHT);
    if (!this.down) {
      ahead_away2y = new Posn(ahead.x, ahead.y + 2 * ITile.HEIGHT);
      prev_away1y = new Posn(ahead.x, ahead.y + ITile.HEIGHT);
    }

    boolean obstacleTwoYNext = obl.inObstacles(ahead_away2y);
    boolean obstacleOneYPrev = obl.inObstacles(prev_away1y)
        || prev.x < 0 || prev.x > width;

    return obstacleTwoYNext && obstacleOneYPrev;
  }

  // ASSUMPTION: this method assumes that this body segment has hit a pebble that hasn't
  // been encountered
  // gives the PebbleTile that has not been encountered that hit this body segment
  ITile pebbleTileHit(ArrayList<ITile> garden, ArrayList<ITile> pebEncountered, int width) {
    IsPebble isPebble = new IsPebble();
    for (ITile tile : garden) {
      if (isPebble.apply(tile) && this.hitHitBox(tile, width)
          && !pebEncountered.contains(tile)) {
        return tile;
      }
    }
    throw new RuntimeException("Pebble not found.");
  }

  // does this body segment hit a pebble tile in the garden that hasn't been encountered already?
  boolean hitPebbleTile(ArrayList<ITile> garden, ArrayList<ITile> pebEncountered, int width) {
    IsPebble isPebble = new IsPebble();
    for (ITile tile : garden) {
      if (isPebble.apply(tile) && this.hitHitBox(tile, width)
          && !pebEncountered.contains(tile)) {
        return true;
      }
    }
    return false;
  }

  boolean hitHitBox(ITile tile, int width) {
    ArrayList<Posn> hitBox = tile.hitBox(width);
    Util util = new Util();
    for (Posn hitBoxSeg : hitBox) {
      if (util.inRange(this.pos, hitBoxSeg)) {
        return true;
      }
    }
    return false;
  }

  boolean hitTile(ITile tile) {
    return tile.inRange(this.pos);
  }
}

// represents a list of all obstacles encountered during a certain period, or iteration, when
// the centipede was/is moving in
class ObstacleList {
  int iteration; // represents how many times the centipede has bounced
  ArrayList<Posn> obstacles; // all the obstacles encountered during this iteration

  ObstacleList(int iteration, ArrayList<Posn> obstacles) {
    this.iteration = iteration;
    this.obstacles = obstacles;
  }

  // the default constructor - constructs a new obstacle list with a new iteration with no
  // obstacles encountered
  ObstacleList(int iteration) {
    this(iteration, new ArrayList<>());
  }

  // constructs a copy of the given ObstacleList
  ObstacleList(ObstacleList other) {
    this(other.iteration, new Util().copy(other.obstacles));
  }

  // is this iteration the same as the one given?
  boolean sameIteration(int iteration) {
    return this.iteration == iteration;
  }

  // EFFECT: modifies this ObstacleList's obstacles by adding a new obstacle/posn to it
  // adds a new obstacle to this list of obstacles
  void addToObstacles(Posn p) {
    this.obstacles.add(p);
  }

  // is the given posn in this list of obstacles?
  boolean inObstacles(Posn p) {
    for (Posn obstacle : this.obstacles) {
      if (p.equals(obstacle)) {
        return true;
      }
    }
    return false;
  }
}

// represents the actual game world when the player can control the gnome
class CGameState extends GameState {
  ArrayList<Centipede> cents; // represents all the centipedes in the current world
  ArrayList<ITile> garden; // represents all the tiles in the current world
  IDart dart;
  Gnome gnome;
  Posn playerDirection; // -1 if player is moving left, 0 is player is not moving,
  // and 1 if player is moving right for the x component,
  // and the same respectively for moving down and up
  int score;
  int width;
  int height;

  // the constructor
  CGameState(ArrayList<Centipede> cents, ArrayList<ITile> garden, Posn playerDirection, Gnome gnome,
             IDart dart, int score, int width, int height) {
    if (width < 2 * ITile.WIDTH || height < 2 * ITile.HEIGHT) {
      throw new IllegalArgumentException("Invalid dimensions");
    }
    this.cents = cents;
    this.garden = garden;
    this.playerDirection = playerDirection;
    this.gnome = gnome;
    this.dart = dart;
    this.score = score;
    this.width = width;
    this.height = height;
  }

  // the default constructor, only requiring how big the board should be
  CGameState(int x, int y, ArrayList<ITile> garden, Gnome gnome) {
    this(new Util().singletonList(new Centipede(10)), garden,
        new Posn(0, 0), gnome, new NoDart(), 0, ITile.WIDTH * x,
        ITile.HEIGHT * y);
  }

  @Override
  // TODO : elaborate on the effect statement
  // EFFECT: changes all the fields except width and height
  // moves every element in the game accordingly after each tick
  public void onTick() {
    this.collidesDandelion();
    this.collidesCentipede();

    for (Centipede c : this.cents) {
      c.move(this.width, this.height, this.garden);
    }

    this.movePlayer();

    this.moveDart();
  }

  // EFFECT: modifies the player position of this CGameState based on the player direction
  // moves the player accordingly based on the key input the user gave
  void movePlayer() {
    if (this.playerDirection.x == 1) {
      this.gnome.move("right", this.width, this.height, this.garden);
    }
    if (this.playerDirection.x == -1) {
      this.gnome.move("left", this.width, this.height, this.garden);
    }

    if (this.playerDirection.y == 1) {
      this.gnome.move("up", this.width, this.height, this.garden);
    }
    if (this.playerDirection.y == -1) {
      this.gnome.move("down", this.width, this.height, this.garden);
    }
  }

  // EFFECT: modifies the IDart and score of this CGameState, either directly modifying the IDart
  // or setting it equal to a different IDart, and modifying the score when needed
  // moves the Dart in the game
  void moveDart() {
    if (this.dart.missed()) {
      this.score -= 1;
    }

    if (this.dart.offScreen()) {
      this.dart = new NoDart();
    } else {
      this.dart.move();
    }
  }

  // EFFECT: modifies the garden and the dart fields of this CGameState
  // alters the state of the game after possible collisions with a dandelion and a dart
  void collidesDandelion() {
    IsDandelion isDandelion = new IsDandelion();
    for (int index = 0; index < this.garden.size(); index += 1) {
      ITile tile = this.garden.get(index);
      if (isDandelion.apply(tile) && this.dart.hitTile(tile)) {
        this.dart = new NoDart();
        tile.lowerHP();
        if (tile.noHP()) {
          this.garden.set(index, new DanToPeb().apply(tile));
        }
      }
    }
  }

  // EFFECT: modifies the centipede, the dart, and the score fields of this CGameState
  // alters the state of the game after possible collisions with a centipede and a dart
  void collidesCentipede() {
    ArrayList<Centipede> cpCent = new ArrayList<>();
    for (Centipede cent : this.cents) {
      if (cent.targetHit(this.dart)) {
        this.score += 10;
        new Util().append(cpCent, cent.split(this.dart));
        this.sproutDandelion(cent.positionHit(this.dart));
        this.dart = new NoDart();
      } else {
        cpCent.add(cent);
      }
    }
    this.cents.clear();
    for (Centipede cent : cpCent) {
      this.cents.add(cent);
    }
  }

//  ArrayList<Posn> pebblePosns() {
//    ArrayList<Posn> pebbles = new ArrayList<>();
//    Util util = new Util();
//    IsPebble isPebble = new IsPebble();
//    for (ITile tile : this.garden) {
//      if (isPebble.apply(tile)) {
//        util.append(pebbles, tile.hitBox(width));
//      }
//    }
//    return pebbles;
//  }

  // EFFECT: modifies the garden to change one of the tiles to a dandelion
  // sprouts a dandelion where a centipede has recently been hit
  void sproutDandelion(Posn posHit) {
    IsGrass isGrass = new IsGrass();
    for (int index = 0; index < this.garden.size(); index += 1) {
      ITile tile = this.garden.get(index);
      if (isGrass.apply(tile) && tile.samePos(posHit)) {
        this.garden.set(index, new GrassToDan().apply(tile));
      }
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

    this.dart.draw(s);

    WorldImage score = new TextImage("Score: " + this.score, Color.BLACK);
    s.placeImageXY(score, this.width - 5 * ITile.WIDTH / 4, ITile.HEIGHT / 4);

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

    if (s.equals(" ")) {
      if (this.dart.offScreen()) {
        this.dart = this.gnome.generateDart();
      }
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

  @Override
  // continues this CGameState to be used in GameMaster
  public CGameState toCGameState() {
    return this;
  }
}