import javalib.worldimages.*;
import java.util.ArrayList;
import java.util.Random;        // the random library in java
import javalib.impworld.*;      // the abstract World class and the big-bang library

class BGame extends World {
  int width;
  int height;
  ArrayList<ITile> garden;
  Gnome gnome;

  // the constructor
  BGame(int width, int height, ArrayList<ITile> garden, Gnome gnome) {
    this.width = width;
    this.height = height;
    this.garden = garden;
    this.gnome = gnome;
  }

  BGame(int width, int height) {
    this.width = width;
    this.height = height;
    this.garden = new Util().generateGrassBoard(width, height);
    this.gnome = new Gnome(ITile.WIDTH / 2, height * ITile.HEIGHT - ITile.HEIGHT / 2,
        ITile.WIDTH / 7);
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

    /*
     * onKeyEvent template: everything in the BGame template, plus Fields of
     * parameters: none Methods on parameters: none
     */
    if (key.equals("left")) {
      this.gnome.moveCell("left", this.width * ITile.WIDTH);
    }
    else if (key.equals("right")) {
      this.gnome.moveCell("right", this.width * ITile.WIDTH);
    }
//    else if (key.equals("d")) {
//      return new BGame(this.generateDandelions(this.width * (this.height - 1) / 20, this.garden),
//          this);
//    }
//    else if (key.equals("p")) {
//      return new BGame(this.generatePebbles(this.width * (this.height - 1) / 20, this.garden),
//          this);
//    }
//    else if (key.equals("r")) {
//      return new BGame(new Util().generateGrass(width, height, height), this);
//    }
//    else if (key.equals("s")) {
//      return new CGame(this.width, this.height, this.garden, this.gnome);
//    }
//    return this;
  }
}
