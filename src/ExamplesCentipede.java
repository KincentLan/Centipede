import javalib.worldimages.Posn;
import tester.*;                // The tester library

import java.util.ArrayList;

// tests and examples for the CGame and all of its related classes and fields
class ExamplesCentipede {
  Centipede cent_0;
  BodySeg bodySeg_0;
  BodySeg bodySeg_1;
  PebbleTile peb_0;
  PebbleTile peb_1;
  PebbleTile peb_2;
  PebbleTile peb_3;
  PebbleTile peb_4;
  PebbleTile peb_5;

  // initializes test conditions
  void initTestConditions() {
    bodySeg_0 = new BodySeg(new Posn(340, 61), new Posn(0, 2),
        false, true, false, 0);
    bodySeg_1 = new BodySeg(new Posn(340, 60), new Posn(0, 2),
        false, true, false, 0);
    cent_0 = new Centipede(10, 4);
    peb_0 = new PebbleTile(20, 20, 400);
    peb_1 = new PebbleTile(380, 20, 400);
    peb_2 = new PebbleTile(20, 60, 400);
    peb_3 = new PebbleTile(380, 60, 400);
    peb_4 = new PebbleTile(60, 60, 400);
    peb_5 = new PebbleTile(60, 20, 400);
  }

  // runs the game
  void testBigBang(Tester t) {
    int x = 10;
    int y = 15;
    GameMaster w = new GameMaster(x, y, 20);
    w.bigBang(x * ITile.WIDTH, y * ITile.HEIGHT, 1.0 / 28.0);
  }
}
