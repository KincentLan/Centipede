import javalib.impworld.*;
import javalib.worldimages.Posn;

import java.util.ArrayList;

// represents a centipede game with the setup and game state
class CentipedeGame extends World {
  World w;

  // the constructor
  CentipedeGame(World w) {
    this.w = w;
  }

  // the default constructor - makes a new BGame state
  CentipedeGame(int x, int y, int initialSeed) {
    this.w = new BGame(x, y, initialSeed);
  }

  void changeToGame(int width, int height, ArrayList<ITile> garden, Gnome gnome) {
    System.out.println("changeToGame");
    System.out.println(this.w);
    System.out.println(this.w instanceof BGame);
    System.out.println(this.w instanceof CGame);
    this.w = new CGame(new Util().singletonList(new Centipede()), garden, gnome,
        width * ITile.WIDTH, height * ITile.HEIGHT);
    System.out.println(this.w instanceof BGame);
    System.out.println(this.w instanceof CGame);
  }

  @Override
  public WorldScene makeScene() {
    System.out.println("makeScene");
    System.out.println(this.w instanceof BGame);
    System.out.println(this.w instanceof CGame);
    return this.w.makeScene();
  }

  @Override
  public void onKeyEvent(String s) {
    System.out.println("onKeyEvent");
    System.out.println(this.w);
    System.out.println(this.w instanceof BGame);
    System.out.println(this.w instanceof CGame);
    this.w.onKeyEvent(s);
  }

  @Override
  public void onTick() {
    System.out.println("onTick");
    System.out.println(this.w);
    System.out.println(this.w instanceof BGame);
    System.out.println(this.w instanceof CGame);
    this.w.onTick();
  }

  @Override
  public void onMouseClicked(Posn mouse, String buttonName) {
    this.w.onMouseClicked(mouse, buttonName);
  }
}
