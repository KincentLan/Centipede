import javalib.impworld.*;
import javalib.worldimages.*;

// represents a centipede game that manages the current GameState, which could be the setup or the
// actual game
class GameMaster extends World {
  GameState gameState;

  // the constructor
  GameMaster(GameState gameState) {
    this.gameState = gameState;
  }

  // the default constructor - makes a new BGame state, only requiring the dimensions of the board
  // and the initial seed for the random object being used
  GameMaster(int x, int y, int initialSeed) {
    this(new BGameState(x, y, initialSeed));
  }

  @Override
  // gives the world scene of the current GameState
  public WorldScene makeScene() {
    return this.gameState.makeScene();
  }

  @Override
  // EFFECT: modifies the current GameState by modifying its fields after each tick
  // moves the world along after each tick
  public void onTick() {
    if (this.gameState.endGame()) {
      this.endOfWorld("" + this.gameState.score());
    }
    this.gameState.onTick();
  }

  @Override
  // EFFECT: modifies the current GameState by modifying its fields after each key event, in the
  // event that the key "s" is pressed, if the GameState is BGameState, then it changes to the
  // CGameState with its current fields passed in, if the GameState is a CGameState,
  // it stays the same
  // changes the world accordingly in response to the key pressed by the user
  public void onKeyEvent(String s) {
    if (s.equals("s")) {
      this.gameState = this.gameState.toCGameState();
    } else {
      this.gameState.onKeyEvent(s);
    }
  }

  @Override
  // EFFECT: modifies the current GameState by modifying its fields after each key release
  // changes the world accordingly in response to the key released by the user
  public void onKeyReleased(String s) {
    this.gameState.onKeyReleased(s);
  }

  @Override
  // EFFECT: changes the current GameState by modifying its fields after each mouse click
  // changes the world accordingly in response to the position and mouse button click by the user
  public void onMouseClicked(Posn mouse, String bName) {
    this.gameState.onMouseClicked(mouse, bName);
  }

  @Override
  public WorldScene lastScene(String s) {
    return this.gameState.lastScene(s);
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

  public abstract boolean endGame();

  public abstract int score();
}


