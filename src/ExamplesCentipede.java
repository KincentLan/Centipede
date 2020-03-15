import tester.*;                // The tester library


// tests and examples for the CGame and all of its related classes and fields
class ExamplesCentipede {
  Centipede cent_0 = new Centipede();

  void testBigBang(Tester t) {
    GameMaster w = new GameMaster(10, 15, 20);
    w.bigBang(10 * ITile.WIDTH, 15 * ITile.HEIGHT, 1.0 / 28.0);
  }
}
