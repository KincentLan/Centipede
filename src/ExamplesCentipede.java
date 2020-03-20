import tester.*;                // The tester library

// tests and examples for the CGame and all of its related classes and fields
class ExamplesCentipede {
  Centipede cent_0;

  // initializes test conditions
  void initTestConditions() {
    cent_0 = new Centipede(10);
  }

  // runs the game
  void testBigBang(Tester t) {
    int x = 3;
    int y = 3;
    GameMaster w = new GameMaster(x, y, 20);
    w.bigBang(x * ITile.WIDTH, y * ITile.HEIGHT, 1.0 / 28.0);
  }
}
