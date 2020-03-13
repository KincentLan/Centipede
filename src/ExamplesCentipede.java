import javalib.worldimages.*;
import tester.*;                // The tester library
import java.util.ArrayList;
import javalib.impworld.*;      // the abstract World class and the big-bang library

// tests and examples for the CGame and all of its related classes and fields
class ExamplesCentipede {
  Centipede cent_0 = new Centipede();

  boolean testCentipedeConstructor(Tester t) {
    ArrayList<Posn> body = new ArrayList<>();
    for (int index = 0; index < 10; index += 1) {
      body.add(new Posn((index - 9) * ITile.WIDTH + ITile.WIDTH / 2 , ITile.HEIGHT / 2));
    }
    Centipede cent_exp = new Centipede(body, 4,
        new Posn(4, 0), true, new ArrayList<>());
    return t.checkExpect(cent_0, cent_exp);
  }

  void testBigBang(Tester t) {
    World w = new CGame(10, 15);
    w.bigBang(10 * ITile.WIDTH, 15 * ITile.HEIGHT, 1.0 / 28.0);
  }
}
