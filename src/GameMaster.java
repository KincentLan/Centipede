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
    this(new BGameState(x, y, initialSeed));
  }

  @Override
  // gives the world scene of the current GameState
  public WorldScene makeScene() {
    return this.w.makeScene();
  }

  @Override
  // EFFECT: modifies the current GameState by modifying its fields after each tick
  // moves the world along after each tick
  public void onTick() {
    this.w.onTick();
  }

  @Override
  // EFFECT: modifies the current GameState by modifying its fields after each key event, in the
  // event that the key "s" is pressed, if the GameState is BGameState, then it changes to the
  // CGameState with its current fields passed in, if the GameState is a CGameState,
  // it stays the same

  public void onKeyEvent(String s) {
    if (s.equals("s")) {
      this.w = this.w.toCGameState();
    }
    else {
      this.w.onKeyEvent(s);
    }
  }
}

abstract class GameState extends World {
  @Override
  public abstract WorldScene makeScene();

  public abstract CGameState toCGameState();
}


