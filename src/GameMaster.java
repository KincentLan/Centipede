import javalib.impworld.*;

// represents a centipede game with the setup and game state
class GameMaster extends World {
  GameState w;

  // the constructor
  GameMaster(GameState w) {
    this.w = w;
  }

  // the default constructor - makes a new BGame state
  GameMaster(int x, int y, int initialSeed) {
    this.w = new BGameState(x, y, initialSeed);
  }

  @Override
  public WorldScene makeScene() {
    return this.w.makeScene();
  }

  @Override
  public void onTick() {
    this.w.onTick();
  }

  @Override
  public void onKeyEvent(String s) {
    if (s.equals("s")) {
      this.w = this.w.toCGame();
    }
    else {
      this.w.onKeyEvent(s);
    }
  }
}

abstract class GameState extends World {
  @Override
  public abstract WorldScene makeScene();

  public abstract CGameState toCGame();
}


