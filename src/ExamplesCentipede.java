import javalib.worldimages.*;
import tester.*;                // The tester library
import java.util.ArrayList;
import javalib.impworld.*;      // the abstract World class and the big-bang library

// tests and examples for the CGame and all of its related classes and fields
class ExamplesCentipede {
  Centipede cent_0 = new Centipede();

  void testBigBang(Tester t) {
    World w = new CGame(10, 15);
    w.bigBang(10 * ITile.WIDTH, 15 * ITile.HEIGHT, 1.0 / 28.0);
  }
}
