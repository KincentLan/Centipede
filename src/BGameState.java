import javalib.worldimages.*;
import java.util.ArrayList;
import java.util.Random;        // the random library in java
import javalib.impworld.*;      // the abstract World class and the big-bang library

//represents a setting up phase for the centipede game
class BGameState extends GameState {
  int x;
  int y;
  ArrayList<ITile> garden;
  Gnome gnome;
  Random rand;

  // the constructor
  BGameState(int x, int y, ArrayList<ITile> garden, Gnome gnome, Random rand) {
    this.x = x;
    this.y = y;
    this.garden = garden;
    this.gnome = gnome;
    this.rand = rand;
  }

  // the default constructor
  BGameState(int x, int y, int initialSeed) {
    this(x, y, new Util().generateGrassBoard(x, y),
        new Gnome(ITile.WIDTH / 2, y * ITile.HEIGHT - ITile.HEIGHT / 2,
            ITile.WIDTH / 7), new Random(initialSeed));
  }
  
  // draws all the elements in the game
  public WorldScene makeScene() {
    WorldScene s = new WorldScene(this.x, this.y);
    for (ITile tile : this.garden) {
      tile.draw(s);
    }
    this.gnome.draw(s);
    return s;
  }

  // on left or right arrow keypress, moves the Gnome one tile to the left or
  // right,
  // on d or p keypress, fills 5% of available tiles with DandelionTiles or
  // PebbleTiles,
  // on r keypress, resets all tiles to GrassTiles, and
  // on s keypress, ends the BGame game
  public void onKeyEvent(String key) {
    if (key.equals("left")) {
      this.gnome.moveCell("left", this.x * ITile.WIDTH);
    }
    else if (key.equals("right")) {
      this.gnome.moveCell("right", this.x * ITile.WIDTH);
    }
    else if (key.equals("d")) {
      this.generate("LeftButton");
    }
    else if (key.equals("p")) {
      this.generate("RightButton");
    }
    else if (key.equals("r")) {
      this.garden = new Util().generateGrassBoard(this.x, this.y);
    }
  }
  
  // EFFECT: changes the garden by generating random dandelions or pebbles
  // changes the board to fill 5% of it with dandelions or pebbles
  void generate(String bName) {
    int botCol = (this.y - 1) * ITile.HEIGHT + ITile.HEIGHT / 2;
    int available = (this.garden.size() - this.x) / 20;
    for(int index = 0; index < available; index += 1) {
      int randInt = rand.nextInt(this.garden.size() - this.x);
      ITile randElement = this.garden.get(randInt);
      this.garden.set(randInt, randElement.replaceTile(bName, botCol));
    }
  }

  // EFFECT: changes this BGame's garden to change the tile clicked on to its effective new tile
  // on left mouse click, updates this BGame with a new DandelionTile at the given
  // posn;
  // on right mouse click, updates this BGame with a new PebbleTile at the given
  // posn
  public void onMouseClicked(Posn mouse, String bName) {
    int row = (mouse.x / ITile.HEIGHT) * ITile.WIDTH + ITile.WIDTH / 2;
    int col = (mouse.y / ITile.WIDTH) * ITile.HEIGHT + ITile.HEIGHT / 2;
    int botCol = (this.y - 1) * ITile.HEIGHT + ITile.HEIGHT / 2;
    Posn mouseCentered = new Posn(row, col);

    for (int index = 0; index < this.garden.size(); index += 1) {
      ITile tile = this.garden.get(index);
      if (tile.samePos(mouseCentered)) {
        this.garden.set(index, tile.replaceTile(bName, botCol));
      }
    }
  }

  // turns this setup (BGameState) to the actual game (CGameState)
  public CGameState toCGameState() {
    return new CGameState(this.x, this.y, this.garden, this.gnome);
  }
}

