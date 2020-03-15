import javalib.impworld.*;

// represents a centipede game that manages the current GameState, which could be the setup or the
// actual game
class GameMaster extends World {
  GameState w;

  // the constructor
  GameMaster(GameState w) {
    this.w = w;
  }

  // the default constructor - makes a new BGame state, only requiring the dimensions of the board
  // and the initial seed for the random object being used
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
  // changes the world accordingly in response to the key pressed by the user
  public void onKeyEvent(String s) {
    if (s.equals("s")) {
      this.w = this.w.toCGameState();
    }
    else {
      this.w.onKeyEvent(s);
    }
  }
}

// represents a game state in the centipede game which could be a BGameState (setup)
// or a CGameState (the actual game)
abstract class GameState extends World {
  @Override
  // gives the world scene of this GameState
  public abstract WorldScene makeScene();

  // in essence, it "starts" the game if this GameState is a BGameState, if this GameState is a
  // CGameState, then it just "continues" the game as normal
  public abstract CGameState toCGameState();
}


