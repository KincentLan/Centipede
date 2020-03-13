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
      BodySeg curr = new BodySeg(pos, vel, head);
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
      garden.add(new GrassTile(row_ind * ITile.WIDTH + ITile.WIDTH/2,
          index * ITile.HEIGHT + ITile.HEIGHT/2));
    }
  }
}

// represents a tile and introduces the tile's height and width constants
interface ITile {
  int HEIGHT = 40;

  int WIDTH = 40;

  // draws this tile onto the world scene given
  void draw (WorldScene s);
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
    WorldImage grass = new RectangleImage(WIDTH-1,
        HEIGHT-1, OutlineMode.SOLID, Color.GREEN);
    s.placeImageXY(outline, this.row, this.col);
    s.placeImageXY(grass, this.row, this.col);
  }
}


// represents a centipede in the centipede game
class Centipede {
  ArrayList<BodySeg> body; // represents all the body segments of this centipede
  int speed;
  boolean down;
  ArrayList<Posn> encountered; // represents a list of obstacles that this centipede has encountered

  // the constructor
  Centipede(ArrayList<BodySeg> body, int speed, boolean down,
            ArrayList<Posn> encountered) {
    if (body.size() == 0) {
      throw new IllegalArgumentException("Centipede cannot have an empty body");
    }

    this.body = body;
    this.speed = speed;
    this.down = down;
    this.encountered = encountered;
  }

  // the default constructor - constructs the starting centipede in the centipede game
  Centipede() {
    this(new Util().generateCentBody(10, ITile.WIDTH / 10),
        ITile.WIDTH / 10, true, new ArrayList<>());
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
  void move(int width) {
    for (int index = 0; index < this.body.size(); index += 1) {
      this.body.get(index).move(width, this.speed, this.down);
    }
  }
}

//represents a body segment of a centipede
class BodySeg {
  Posn pos; // represents the position of this body segment
  Posn velocity; // represents the velocity of this body segment
  boolean head;

  // the constructor
  BodySeg(Posn pos, Posn velocity, boolean head) {
    this.pos = pos;
    this.velocity = velocity;
    this.head = head;
  }

  // EFFECT: changes the given world scene by adding this body segment onto it
  // draws this body segment onto the given world scene
  void draw(WorldScene s) {
    Color color = Color.BLUE;
    if (this.head) {
      color = Color.CYAN;
    }

    WorldImage bodyPartOutline = new CircleImage(ITile.WIDTH/2,
        OutlineMode.SOLID, Color.BLACK);
    WorldImage bodyPart = new CircleImage(ITile.WIDTH/2 - 1, OutlineMode.SOLID, color);
    s.placeImageXY(bodyPartOutline, this.pos.x, this.pos.y);
    s.placeImageXY(bodyPart, this.pos.x, this.pos.y);
  }

  // EFFECT: changes the position and velocity of this body segment
  // moves this body segment
  void move(int width, int speed, boolean down) {
    boolean leftEdge = this.pos.x == ITile.WIDTH / 2;
    boolean rightEdge = this.pos.x == width - ITile.WIDTH / 2;
    boolean inRow = (this.pos.y - ITile.HEIGHT / 2) % ITile.HEIGHT == 0;

    if (leftEdge && inRow && this.velocity.x < 0 || rightEdge && inRow && this.velocity.x > 0) {
      if (!down) {
        speed *= -1;
      }
      this.velocity = new Posn(0, speed);
    }

    else if ((leftEdge || rightEdge) && inRow && this.velocity.x == 0) {
      if (rightEdge) {
        speed *=-1;
      }
      this.velocity = new Posn(speed, 0);
    }

    this.pos = new Posn(this.pos.x + this.velocity.x, this.pos.y + this.velocity.y);
  }
}


// represents the actual game world when the player can control the gnome
class CGame extends World {
  ArrayList<Centipede> cents; // represents all the centipedes in the current world
  ArrayList<ITile> garden; // represents all the tiles in the current world
  int width;
  int height;

  // the constructor
  CGame(ArrayList<Centipede> cents, ArrayList<ITile> garden, int width, int height) {
    if (width < 2 * ITile.WIDTH || height < 2 * ITile.HEIGHT) {
      throw new IllegalArgumentException("Invalid dimensions");
    }
    this.cents = cents;
    this.garden = garden;
    this.width = width;
    this.height = height;
  }

  // the default constructor, only requiring how big the board should be
  CGame(int x, int y) {
    this(new Util().singletonList(new Centipede()),
        new Util().generateGrassBoard(x, y),
        ITile.WIDTH * x, ITile.HEIGHT * y);
  }

  @Override
  // moves every element in the game accordingly after each tick
  public void onTick() {
    for (Centipede c : this.cents) {
      c.move(this.width);
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
    return s;
  }
}