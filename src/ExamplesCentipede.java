import javalib.worldimages.Posn;
import tester.*;                // The tester library

import java.util.ArrayList;

// tests and examples for the CGame and all of its related classes and fields
class ExamplesCentipede {
  Centipede cent_0;

  // initializes test conditions
  void initTestConditions() {
    cent_0 = new Centipede(10);
  }

  // runs the game
  void testBigBang(Tester t) {
    int x = 10;
    int y = 15;
    GameMaster w = new GameMaster(x, y, 20);
    w.bigBang(x * ITile.WIDTH, y * ITile.HEIGHT, 1.0 / 28.0);
  }

  void testBodySegNextEncountered(Tester t) {
    BodySeg a = new BodySeg(new Posn(340, 61), new Posn(0, 2), false, true, false, 0);
    BodySeg b = new BodySeg(new Posn(340, 60), new Posn(0, 2), false, true, false, 0);
    ArrayList<Posn> arr = new ArrayList<>();
    arr.add(new Posn(300, 60));
    ObstacleList obl = new ObstacleList(0, arr);
    //return t.checkExpect(b.nextEncountered(obl, 2), true);
    t.checkExpect(a.nextEncountered(obl, 2), true);
  }
}
