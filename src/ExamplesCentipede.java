import javalib.worldimages.Posn;
import tester.*;                // The tester library

import java.util.ArrayList;

// tests and examples for the centipede game and all of its related classes and fields
class ExamplesCentipede {
  // examples for everything related to the Centipede game
  IsGrass isGrass = new IsGrass();
  IsDandelion isDandelion = new IsDandelion();
  IsPebble isPebble = new IsPebble();
  DanToPeb danToPeb = new DanToPeb();
  GrassToDan grassToDan = new GrassToDan();
  Util util = new Util();
  ITile tile_0;
  ITile tile_1;
  ITile tile_2;
  ITile tile_3;
  ITile tile_4;
  IWaterBalloon waterBalloon_0;
  IWaterBalloon waterBalloon_1;
  IWaterBalloon waterBalloon_2;
  IWaterBalloon waterBalloon_3;
  IDart dart_0;
  IDart dart_1;
  IDart dart_2;
  IDart dart_3;
  ArrayList<BodySeg> bseg_0;
  ArrayList<BodySeg> bseg_1;
  BodySeg bodySeg_0;
  BodySeg bodySeg_1;
  BodySeg bodySeg_2;
  BodySeg bodySeg_3;
  Centipede cent_0;
  Centipede cent_1;
  Centipede cent_2;
  Centipede cent_3;
  ObstacleList obl_0;
  ObstacleList obl_1;
  ObstacleList obl_2;
  ObstacleList obl_3;
  CGameState cgame_0;
  CGameState cgame_1;
  BGameState bgame_0;
  BGameState bgame_1;
  GameState gamestate_0;
  GameState gamestate_1;
  GameMaster gm_0;
  GameMaster gm_1;
  Gnome player;
  ArrayList<ITile> garden_0;
  ArrayList<ITile> garden_1;

  // initializes test conditions
  void initTestConditions() {
    tile_0 = new GrassTile(0, 0, 4);
    tile_1 = new PebbleTile(1, 0, 4);
    tile_2 = new DandelionTile(2, 1, 3, 4);
    tile_3 = new GrassTile(0, 1, 4);
    tile_4 = new PebbleTile(2, 3, 4);
    waterBalloon_0 = new WaterBalloon(0, 0, 10);
    waterBalloon_1 = new WaterBalloon(2, 1, 3);
    waterBalloon_2 = new WaterBalloon(2, 3, 4);
    waterBalloon_3 = new NoWaterBalloon();
    dart_0 = new Dart(0, 0, 10);
    dart_1 = new Dart(2, 1, 11);
    dart_2 = new Dart(5, 5, 10);
    dart_3 = new NoDart();

    bseg_0 = new ArrayList<>();
    bodySeg_0 = new BodySeg(new Posn(380, 60), new Posn(0, 6),
        false, true, false, 60, 0);
    bodySeg_1 = new BodySeg(new Posn(340, 60), new Posn(0, 2),
        false, true, false, 60, 0);
    bseg_0.add(bodySeg_0);
    bseg_0.add(bodySeg_1);

    bseg_1 = new ArrayList<>();
    bodySeg_2 = new BodySeg(new Posn(380, 20), new Posn(0, 2),
        false, true, false, 60, 0);
    bodySeg_3 = new BodySeg(new Posn(376, 20), new Posn(0, 2),
        false, true, false, 60, 0);
    bseg_1.add(bodySeg_2);
    bseg_1.add(bodySeg_3);

    cent_0 = new Centipede(10, 4);
    cent_1 = new Centipede(bseg_0, 8,
        10, new ArrayList<>(), new ArrayList<>());
    cent_2 = new Centipede(bseg_0, 8,
        10, new ArrayList<>(), new ArrayList<>());

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
