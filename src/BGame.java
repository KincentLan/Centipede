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

  @Override
  public WorldScene makeScene() {
    return null;
  }
}
