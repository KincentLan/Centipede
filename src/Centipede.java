import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.impworld.*;      // the abstract World class and the big-bang library
// for imperative worlds
import java.awt.Color;          // general colors (as triples of red,green,blue values)
import java.util.ArrayList;     // the arraylist library from java
// and predefined colors (Red, Green, Yellow, Blue, Black, White)

interface ITile {
  int HEIGHT = 40;

  int WIDTH = 40;
}

// represents a centipede in the centipede game
class Centipede {
  ArrayList<Posn> body; // represents all the body segments of this centipede
  int speed;
  Posn velocity;
  boolean down;
  ArrayList<Posn> encountered; // represents a list of obstacles that this centipede has encountered

  // the constructor
  Centipede(ArrayList<Posn> body, int speed, Posn velocity, boolean down,
            ArrayList<Posn> encountered) {
    if (body.size() == 0) {
      throw new IllegalArgumentException("Centipede cannot have an empty body");
    }

    this.body = body;
    this.speed = speed;
    this.velocity = velocity;
    this.down = down;
    this.encountered = encountered;
  }

  // the default constructor - constructs the starting centipede in the centipede game
  Centipede() {
    ArrayList<Posn> body = new ArrayList<>();
    for (int index = 0; index < 10; index += 1) {
      body.add(new Posn((index - 9) * ITile.WIDTH + ITile.WIDTH / 2, ITile.HEIGHT / 2));
    }
    this.body = body;
    this.speed = ITile.WIDTH / 10;
    this.velocity = new Posn(this.speed, 0);
    this.down = true;
    this.encountered = new ArrayList<>();
  }

  // draws this centipede onto the given world scene
  WorldScene draw(WorldScene s) {
    WorldImage bodyPart = new CircleImage(ITile.WIDTH / 2, OutlineMode.SOLID, Color.BLUE);
    for (int index = 0; index < this.body.size(); index += 1) {
      Posn pos = this.body.get(index);
      if (index == this.body.size() - 1) {
        bodyPart = new CircleImage(ITile.WIDTH / 2, OutlineMode.SOLID, Color.CYAN);
      }
      s.placeImageXY(bodyPart, pos.x, pos.y);
    }
    return s;
  }

  // EFFECT: changes the all the elements in this centipede's list of body positions,
  // essentially moving it along in the world
  // moves the centipede along the board in the world
  void move(int width) {
  }
}

// represents the actual game world when the player can control the gnome
class CGame extends World {
  ArrayList<Centipede> cents; // represents all the centipedes in the current world
  int width;
  int height;

  // the constructor
  CGame(ArrayList<Centipede> cents, int width, int height) {
    if (width < 2 * ITile.WIDTH || height < 2 * ITile.HEIGHT) {
      throw new IllegalArgumentException("Invalid dimensions");
    }
    this.cents = cents;
    this.width = width;
    this.height = height;
  }

  // the default constructor, only requiring how big the board should be
  CGame(int x, int y) {
    this.cents = new ArrayList<>();
    this.cents.add(new Centipede());
    this.width = ITile.WIDTH * x;
    this.height = ITile.HEIGHT * y;
  }

  @Override
  public void onTick() {
    for (Centipede c : this.cents) {
      c.move(this.width);
    }
  }

  @Override
  public WorldScene makeScene() {
    WorldScene s = new WorldScene(this.width, this.height);
    for (Centipede c : this.cents) {
      c.draw(s);
    }
    return s;
  }
}