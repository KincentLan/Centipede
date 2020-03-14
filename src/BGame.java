import javalib.worldimages.*;
import java.util.ArrayList;
import java.util.Random;        // the random library in java
import javalib.impworld.*;      // the abstract World class and the big-bang library

class BGame extends World {
  int width;
  int height;
  ArrayList<ITile> garden;
  Gnome gnome;
  Random rand;

  // the constructor
  BGame(int width, int height, ArrayList<ITile> garden, Gnome gnome, Random rand) {
    this.width = width;
    this.height = height;
    this.garden = garden;
    this.gnome = gnome;
    this.rand = rand;
  }

  BGame(int width, int height, int initialSeed) {
    this.width = width;
    this.height = height;
    this.garden = new Util().generateGrassBoard(width, height);
    this.gnome = new Gnome(ITile.WIDTH / 2, height * ITile.HEIGHT - ITile.HEIGHT / 2,
        ITile.WIDTH / 7);
    this.rand = new Random(initialSeed);
  }
  
  // draws all the elements in the game
  public WorldScene makeScene() {
    WorldScene s = new WorldScene(this.width, this.height);
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
      this.gnome.moveCell("left", this.width * ITile.WIDTH);
    }
    else if (key.equals("right")) {
      this.gnome.moveCell("right", this.width * ITile.WIDTH);
    }
    else if (key.equals("d")) {
      this.generate("left");
    }
    else if (key.equals("p")) {
      this.generate("right");
    }
//    else if (key.equals("r")) {
//      return new BGame(new Util().generateGrass(width, height, height), this);
//    }
//    else if (key.equals("s")) {
//      return new CGame(this.width, this.height, this.garden, this.gnome);
//    }
//    return this;
  }
  
  // EFFECT:
  void generate(String bName) {
    // 
    int availiable = (this.garden.size() - this.width) / 20;
    for(int index = 0; index < availiable; index += 1) {
      int randInt = rand.nextInt(availiable);
      ITile randElement = this.garden.get(randInt);
      this.garden.set(randInt, randElement.replaceTile(bName, this.height));
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
    int botCol = (this.height - 1) * ITile.HEIGHT + ITile.HEIGHT / 2;
    Posn mouseCentered = new Posn(row, col);

    for (int index = 0; index < this.garden.size(); index += 1) {
      ITile tile = this.garden.get(index);
      if (tile.samePos(mouseCentered)) {
        this.garden.set(index, tile.replaceTile(bName, botCol));
      }
    }
  }
}

