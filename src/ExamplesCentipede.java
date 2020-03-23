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
        false, true, false, 60, 0);
    bodySeg_1 = new BodySeg(new Posn(340, 60), new Posn(0, 2),
        false, true, false, 60, 0);
//    peb_0 = new PebbleTile(20, 20, 400);
//    peb_1 = new PebbleTile(380, 20, 400);
//    peb_2 = new PebbleTile(20, 60, 400);
//    peb_3 = new PebbleTile(380, 60, 400);
//    peb_4 = new PebbleTile(60, 60, 400);
//    peb_5 = new PebbleTile(60, 20, 400);
//    System.out.println(peb_0.hitBox());
//    System.out.println(peb_1.hitBox());
//    System.out.println(peb_2.hitBox());
//    System.out.println(peb_3.hitBox());
//    System.out.println(peb_4.hitBox());
//    System.out.println(peb_5.hitBox());
  }

  // runs the game
  void testBigBang(Tester t) {
    int x = 10;
    int y = 15;
    GameMaster w = new GameMaster(x, y, 20);
    w.bigBang(x * ITile.WIDTH, y * ITile.HEIGHT, 1.0 / 28.0);
  }

  void testBodySegNextEncountered(Tester t) {
    this.initTestConditions();
    ArrayList<Posn> arr = new ArrayList<>();
    arr.add(new Posn(300, 60));
    ObstacleList obl = new ObstacleList(0, arr);
  }
}
