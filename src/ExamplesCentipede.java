import javalib.impworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.Posn;
import javalib.worldimages.StarImage;
import javalib.worldimages.WorldImage;
import tester.*;                // The tester library

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

  /* DOCUMENTATION for known/potential bugs:
     The move() for BodySeg has a slight bug where it will try to move towards a Dandelion
     when moving down sometimes depending on speed. It will re-orient itself to the correct position
     but this leads to janky movement to the eyes. If there was more time, the move() would have
     been fixed by checking if there was a dandelion and adding the excess speed to the y
     position, while mutating all its related fields.

     Speeds may also affect how aheadDandelion() works in the BodySeg class. It currently checks
     if there is a dandelion ahead of this tile, but with higher speeds, it may lead to bugs
     where it may overshoot the current tile, making it not able to detect dandelions correctly.

     If this is the case, then the centipede would break, and all of its segments would not
     follow the head correctly. In certain cases where the body segment somehow reaches
     the top or bottom row before the head, it will also crash. This is because the program
     expects the head to reach those rows first to generate a new ObstacleList to be
     used by all other body segments in the same iteration as that ObstacleList. If the head
     does not reach it first, the ObstacleList with the same iteration would never exist
     and the program would crash.
 */

// tests and examples for the centipede game and all of its related classes and fields
class ExamplesCentipede {
  // examples for everything related to the Centipede game
  IsGrass isGrass = new IsGrass();
  IsDandelion isDandelion = new IsDandelion();
  IsPebble isPebble = new IsPebble();
  DanToPeb danToPeb = new DanToPeb();
  GrassToDan grassToDan = new GrassToDan();
  ArrayList<Integer> onetwothree;
  ArrayList<Integer> none;
  ArrayList<Integer> one;
  ArrayList<Integer> twothree;
  Util util = new Util();
  ITile tile_0;
  ITile tile_1;
  ITile tile_2;
  ITile tile_3;
  ITile tile_4;
  ITile tile_5;
  ITile tile_6;
  ITile tile_7;
  ITile tile_8;
  ITile tile_9;
  ITile tile_10;
  ITile tile_11;
  ITile tile_12;
  ITile tile_13;
  ITile tile_14;
  ITile tile_15;
  ITile tile_16;
  ArrayList<ITile> garden_0;
  ArrayList<ITile> garden_1;
  ArrayList<ITile> garden_2;
  ArrayList<ITile> garden_3;
  ArrayList<ITile> garden_4;
  ArrayList<ITile> garden_5;
  IWaterBalloon waterBalloon_0;
  IWaterBalloon waterBalloon_1;
  IWaterBalloon waterBalloon_2;
  IWaterBalloon waterBalloon_3;
  IWaterBalloon waterBalloon_4;
  IWaterBalloon waterBalloon_5;
  IWaterBalloon waterBalloon_6;
  IWaterBalloon waterBalloon_7;
  WaterBalloon waterBalloon_8;
  IDart dart_0;
  IDart dart_1;
  IDart dart_2;
  IDart dart_3;
  IDart dart_4;
  IDart dart_5;
  BodySeg bodySeg_0;
  BodySeg bodySeg_1;
  BodySeg bodySeg_2;
  BodySeg bodySeg_3;
  BodySeg bodySeg_4;
  BodySeg bodySeg_5;
  BodySeg bodySeg_6;
  BodySeg bodySeg_7;
  BodySeg bodySeg_8;
  BodySeg bodySeg_9;
  BodySeg bodySeg_10;
  BodySeg bodySeg_11;
  ArrayList<BodySeg> bseg_0;
  ArrayList<BodySeg> bseg_1;
  ArrayList<BodySeg> bseg_2;
  Centipede cent_0;
  Centipede cent_1;
  Centipede cent_2;
  Centipede cent_3;
  Centipede cent_4;
  ArrayList<Centipede> cent_arr_0;
  ArrayList<Centipede> cent_arr_01;
  ArrayList<Centipede> cent_arr_23;
  ArrayList<Centipede> cent_arr_34;
  ArrayList<Centipede> cent_arr_4;
  ObstacleList obl_0;
  ObstacleList obl_1;
  ObstacleList obl_2;
  Gnome player;
  Gnome player_1;
  Gnome player_2;
  Gnome player_3;
  Gnome player_4;
  CGameState cgame_0;
  CGameState cgame_1;
  BGameState bgame_0;
  BGameState bgame_1;
  GameState gamestate_0;
  GameState gamestate_1;
  GameMaster gm_0;
  GameMaster gm_1;
  WorldScene bg;
  WorldScene bg1;

  // initializes test conditions
  void initTestConditions() {
    onetwothree = new ArrayList<>();
    onetwothree.add(1);
    onetwothree.add(2);
    onetwothree.add(3);
    none = new ArrayList<>();
    one = new ArrayList<>();
    one.add(1);
    twothree = new ArrayList<>();
    twothree.add(2);
    twothree.add(3);
    tile_0 = new GrassTile(20, 60, 400);
    tile_1 = new PebbleTile(20, 20, 400);
    tile_2 = new DandelionTile(60, 60, 3, 400);
    tile_3 = new GrassTile(100, 100, 400);
    tile_4 = new PebbleTile(140, 180, 400);
    tile_5 = new GrassTile(20, 20, 40);
    tile_6 = new GrassTile(20, 20, 80);
    tile_7 = new GrassTile(20, 60, 80);
    tile_8 = new GrassTile(60, 20, 80);
    tile_9 = new GrassTile(60, 60, 80);
    tile_10 = new DandelionTile(20, 60, 2, 80);
    tile_11 = new DandelionTile(60, 60, 1, 80);
    tile_12 = new DandelionTile(60, 20, 3, 80);
    tile_13 = new PebbleTile(60, 20, 400);
    tile_14 = new PebbleTile(380, 20, 400);
    tile_15 = new GrassTile(20, 20, 400);
    tile_16 = new PebbleTile(420, 20, 440);
    garden_0 = new ArrayList<>();
    garden_0.add(tile_0);
    garden_0.add(tile_1);
    garden_0.add(tile_2);
    garden_0.add(tile_3);
    garden_1 = new ArrayList<>();
    garden_1.add(tile_5);
    garden_2 = new ArrayList<>();
    garden_2.add(tile_6);
    garden_2.add(tile_7);
    garden_2.add(tile_8);
    garden_2.add(tile_9);
    garden_3 = util.generateGrassBoard(3, 3);
    garden_4 = util.generateGrassBoard(5, 5);
    garden_5 = new ArrayList<>();
    garden_5.add(new DandelionTile(20, 20, 3, 120));
    garden_5.add(new DandelionTile(20, 60, 3, 120));
    garden_5.add(new DandelionTile(20, 100, 3, 120));
    garden_5.add(new DandelionTile(60, 20, 3, 120));
    garden_5.add(new PebbleTile(60, 60, 120));
    garden_5.add(new DandelionTile(60, 100, 3, 120));
    garden_5.add(new DandelionTile(100, 20, 3, 120));
    garden_5.add(new DandelionTile(100, 60, 3, 120));
    garden_5.add(new DandelionTile(100, 100, 3, 120));
    waterBalloon_0 = new WaterBalloon(0, 0, 10);
    waterBalloon_1 = new WaterBalloon(2, -1, 3);
    waterBalloon_2 = new WaterBalloon(2, 3, 4);
    waterBalloon_3 = new NoWaterBalloon();
    waterBalloon_4 = new WaterBalloon(20, 20, 10);
    waterBalloon_5 = new WaterBalloon(20, 32, 10);
    waterBalloon_6 = new WaterBalloon(20, 20, 10);
    waterBalloon_7 = new WaterBalloon(60, 60, 10);
    waterBalloon_8 = new WaterBalloon(60, 60, 10);
    dart_0 = new Dart(0, 0, 10);
    dart_1 = new Dart(2, -1, 11);
    dart_2 = new Dart(5, 5, 10);
    dart_3 = new NoDart();
    dart_4 = new Dart(20, 20, 10);
    dart_5 = new Dart(20, 32, 10);

    bseg_0 = new ArrayList<>();
    bodySeg_0 = new BodySeg(new Posn(20, 20), new Posn(0, 6),
        false, true, true, 60, 0);
    bodySeg_1 = new BodySeg(new Posn(60, 20), new Posn(-6, 0),
        false, true, false, 60, 0);
    bseg_0.add(bodySeg_0);
    bseg_0.add(bodySeg_1);

    bseg_1 = new ArrayList<>();
    bodySeg_2 = new BodySeg(new Posn(60, 22), new Posn(0, 2),
        false, true, false, 60, 0);
    bodySeg_3 = new BodySeg(new Posn(22, 20), new Posn(2, 0),
        false, true, true, 60, 0);
    bseg_1.add(bodySeg_2);
    bseg_1.add(bodySeg_3);

    bodySeg_4 = new BodySeg(new Posn(100, 20), new Posn(0, 6),
        false, true, true, 60, 0);
    bodySeg_5 = new BodySeg(new Posn(20, 60), new Posn(-6, 0),
        false, true, false, 60, 0);
    bodySeg_6 = new BodySeg(new Posn(60, 60), new Posn(0, 6),
        false, true, true, 60, 0);
    bodySeg_7 = new BodySeg(new Posn(98, 60), new Posn(-6, 0),
        false, true, false, 60, 0);
    bodySeg_8 = new BodySeg(new Posn(20, 100), new Posn(0, 6),
        false, true, true, 60, 0);
    bodySeg_9 = new BodySeg(new Posn(60, 100), new Posn(-6, 0),
        false, true, false, 60, 0);
    bodySeg_10 = new BodySeg(new Posn(100, 100), new Posn(-6, 0),
        false, true, false, 60, 0);

    bseg_2 = new ArrayList<>();
    bseg_2.add(bodySeg_0);
    bseg_2.add(bodySeg_1);
    bseg_2.add(bodySeg_4);
    bseg_2.add(bodySeg_5);
    bseg_2.add(bodySeg_6);
    bseg_2.add(bodySeg_7);
    bseg_2.add(bodySeg_8);
    bseg_2.add(bodySeg_9);
    bseg_2.add(bodySeg_10);

    bodySeg_11 = new BodySeg(new Posn(220, 100), new Posn(-6, 0),
        false, true, false, 60, 0);

    cent_0 = new Centipede(10, 4);
    cent_1 = new Centipede(bseg_0, 8,
        10, new ArrayList<>(), new ArrayList<>());
    cent_2 = new Centipede(bseg_1, 8,
        10, new ArrayList<>(), new ArrayList<>());
    cent_3 = new Centipede(util.singletonList(bodySeg_11), 8,
        10, new ArrayList<>(), new ArrayList<>());
    cent_4 = new Centipede(bseg_2, 8,
        10, new ArrayList<>(), new ArrayList<>());
    cent_arr_0 = new ArrayList<>();
    cent_arr_0.add(cent_0);
    cent_arr_01 = new ArrayList<>();
    cent_arr_01.add(cent_0);
    cent_arr_01.add(cent_1);
    cent_arr_23 = new ArrayList<>();
    cent_arr_23.add(cent_2);
    cent_arr_23.add(cent_3);
    cent_arr_34 = new ArrayList<>();
    cent_arr_34.add(cent_3);
    cent_arr_34.add(cent_4);
    cent_arr_4 = new ArrayList<>();
    cent_arr_4.add(cent_4);

    ArrayList<Posn> posns_0 = new ArrayList<>();
    posns_0.add(new Posn(280, 60));
    obl_0 = new ObstacleList(0, posns_0);

    ArrayList<Posn> posns_1 = new ArrayList<>();
    posns_1.add(new Posn(20, 20));
    obl_1 = new ObstacleList(1, posns_1);
    obl_2 = new ObstacleList(2, new ArrayList<>());

    player = new Gnome(20, 15 * ITile.HEIGHT - ITile.HEIGHT / 2, ITile.WIDTH / 7);
    player_1 = new Gnome(25, 15 * ITile.HEIGHT - ITile.HEIGHT / 2, ITile.WIDTH / 7);
    player_2 = new Gnome(45, 15 * ITile.HEIGHT - ITile.HEIGHT / 2, ITile.WIDTH / 7);
    player_3 = new Gnome(100, 20, 8);
    player_4 = new Gnome(100, 60, 8);

    cgame_0 = new CGameState(3, 3, garden_1, player);
    cgame_1 = new CGameState(5, 5, garden_2, player);

    bgame_0 = new BGameState(3, 3, 20);
    bgame_1 = new BGameState(3, 3, garden_1, player, new Random(20));

    gamestate_0 = cgame_0;
    gamestate_1 = bgame_0;

    gm_0 = new GameMaster(gamestate_0);
    gm_1 = new GameMaster(gamestate_1);
    bg = new WorldScene(10, 15);
    bg1 = new WorldScene(10, 15);
  }

  // test the method draw in Gnome class
  void testGnomeDraw(Tester t) {
    this.initTestConditions();
    WorldImage gnome =
        new StarImage(ITile.WIDTH / 2 - 1, 8,
            2, OutlineMode.SOLID, Color.ORANGE);
    WorldImage gnomeBallon =
        new CircleImage(ITile.WIDTH / 2 - 1, OutlineMode.SOLID, Color.BLUE);
    t.checkExpect(bg, bg1);
    bg1.placeImageXY(gnome, 20, 580);
    player.draw(bg, 2);
    t.checkExpect(bg, bg1);
    player.draw(bg, 4);
    bg1.placeImageXY(gnomeBallon, 20, 580);
    t.checkExpect(bg, bg1);
  }

  Gnome player_ = new Gnome(0, 580, 5);
  // test the method generateDart
  boolean testGnomeGenerateDart(Tester t) {
    this.initTestConditions();
    return t.checkExpect(player.generateDart(), new Dart(20, 580, 20))
        && t.checkExpect(player_.generateDart(), new Dart(20, 580, 20))
        && t.checkExpect(player_2.generateDart(), new Dart(60, 580, 20));
  }

  // test the method generateWaterBallon in Gnome class
  boolean testGnomeGenerateWaterBallon(Tester t) {
    this.initTestConditions();
    return t.checkExpect(player.generateWaterBallon(), new WaterBalloon(20, 580, 20))
        && t.checkExpect(player_.generateWaterBallon(), new WaterBalloon(20, 580, 20))
        && t.checkExpect(player_2.generateWaterBallon(), new WaterBalloon(60, 580, 20));
  }

  // test the method inRange in Gnome class
  boolean testGnomeInRange(Tester t) {
    this.initTestConditions();
    return t.checkExpect(player.inRange(new Posn(20, 580)), true)
        && t.checkExpect(player.inRange(new Posn(0, 560)), true)
        && t.checkExpect(player.inRange(new Posn(20, 550)), false)
        && t.checkExpect(player_.inRange(new Posn(30, 590)), false)
        && t.checkExpect(player_2.inRange(new Posn(50, 50)), false);
  }

  // test the method move in Gnome class
  void testGnomeMove(Tester t) {
    this.initTestConditions();
    t.checkExpect(player.x, 20);
    t.checkExpect(player.y, 580);
    player.move("left", 400, 600, new ArrayList<ITile>());
    t.checkExpect(player.x, 20);
    player.move("right", 400, 600, new ArrayList<ITile>());
    t.checkExpect(player.x, 25);
    player.move("down", 400, 600, garden_1);
    t.checkExpect(player.y, 580);
    player.move("up", 400, 600, garden_2);
    t.checkExpect(player.y, 575);
  }

  // test the method moveCell in Gnome class
  void testGnomeMoveCell(Tester t) {
    this.initTestConditions();
    t.checkExpect(player.x, 20);
    t.checkExpect(player.y, 580);
    player.moveCell("left", 400);
    t.checkExpect(player.x, 20);
    t.checkExpect(player.y, 580);
    player.moveCell("right", 400);
    t.checkExpect(player.x, 60);
    t.checkExpect(player.y, 580);
    player.moveCell("left", 300);
    t.checkExpect(player.x, 20);
    t.checkExpect(player.y, 580);
    player.moveCell("right", 40);
    t.checkExpect(player.x, 20);
    t.checkExpect(player.y, 580);
  }

  // function object tests

  // tests IsGrass, IsPebble, IsDandelion apply(ITile)
  // NOTE: all the other methods in these ITileVisitor's are tested implicitly
  boolean testIsXApply(Tester t) {
    this.initTestConditions();
    IsGrass isGrass = new IsGrass();
    IsPebble isPebble = new IsPebble();
    IsDandelion isDandelion = new IsDandelion();

    return t.checkExpect(isGrass.apply(tile_0), true)
        && t.checkExpect(isPebble.apply(tile_0), false)
        && t.checkExpect(isDandelion.apply(tile_0), false)
        && t.checkExpect(isGrass.apply(tile_1), false)
        && t.checkExpect(isPebble.apply(tile_1), true)
        && t.checkExpect(isDandelion.apply(tile_1), false)
        && t.checkExpect(isGrass.apply(tile_2), false)
        && t.checkExpect(isPebble.apply(tile_2), false)
        && t.checkExpect(isDandelion.apply(tile_2), true);

  }

  // tests GrassToDan apply(ITile)
  // NOTE: all the other methods in this ITileVisitor's are tested implicitly
  boolean testGrassToDanApply(Tester t) {
    this.initTestConditions();
    GrassToDan grassToDan = new GrassToDan();
    return t.checkExpect(grassToDan.apply(tile_0), new DandelionTile(20, 60, 3, 400))
        && t.checkExpect(grassToDan.apply(tile_1), tile_1)
        && t.checkExpect(grassToDan.apply(tile_2), tile_2);
  }

  // tests DanToPeb apply(ITile)
  // NOTE: all the other methods in these ITileVisitor's are tested implicitly
  boolean testDanToPebApply(Tester t) {
    this.initTestConditions();
    DanToPeb danToPeb = new DanToPeb();
    return t.checkExpect(danToPeb.apply(tile_0), tile_0)
        && t.checkExpect(danToPeb.apply(tile_1), tile_1)
        && t.checkExpect(danToPeb.apply(tile_2), new PebbleTile(60, 60, 400));
  }

  // tests for the util class

  // tests util generateCentBody(int, int)
  void testUtilGenerateCentBody(Tester t) {
    ArrayList<BodySeg> body = new ArrayList<>();
    body.add(new BodySeg(new Posn(-60, 20),
        new Posn(4, 0), false, true, true, 60, 0));
    body.add(new BodySeg(new Posn(-20, 20),
        new Posn(4, 0), false, true, true, 60, 0));
    BodySeg head = new BodySeg(new Posn(20, 20),
        new Posn(4, 0), true, true, true, 60, 0);
    body.add(head);
    t.checkExpect(util.generateCentBody(3, 4), body);
    body.remove(0);
    t.checkExpect(util.generateCentBody(2, 4), body);
    body.remove(0);
    t.checkExpect(util.generateCentBody(1, 4), body);
  }

  // tests util getElementsBetween(ArrayList<T>, int, int)
  void testUtilGetElementsBetween(Tester t) {
    this.initTestConditions();
    t.checkExpect(util.getElementsBetween(onetwothree, 0, 1), one);
    t.checkExpect(util.getElementsBetween(onetwothree, 0, 0), none);
    t.checkExpect(util.getElementsBetween(onetwothree, 1, 3), twothree);
    t.checkExpect(util.getElementsBetween(onetwothree, 3, 2), none);
  }

  // tests util singletonList(T)
  boolean testUtilSingletonList(Tester t) {
    this.initTestConditions();
    return t.checkExpect(util.singletonList(1), one);
  }

  // tests util generateGrassBoard(int, int)
  // NOTE: this checks generateGrassRow(ArrayList<ITile>, int, int, int) implicitly
  boolean testUtilGenerateGrassBoard(Tester t) {
    this.initTestConditions();
    return t.checkExpect(util.generateGrassBoard(1, 1), garden_1)
        && t.checkExpect(util.generateGrassBoard(2, 2), garden_2)
        && t.checkExpect(util.generateGrassBoard(0, 0), new ArrayList<>());
  }

  // tests util copy(ArrayList<T>)
  void testUtilCopy(Tester t) {
    this.initTestConditions();
    ArrayList<Integer> one = new ArrayList<>();
    one.add(1);
    t.checkExpect(util.copy(this.one), one);
    t.checkExpect(one == this.one, false);
  }

  // tests util append(ArrayList<T>, ArrayList<T>)
  void testUtilAppend(Tester t) {
    this.initTestConditions();
    util.append(one, twothree);
    t.checkExpect(one, onetwothree);
    util.append(none, one);
    t.checkExpect(none, onetwothree);

    this.initTestConditions();
    ArrayList<Integer> one = new ArrayList<>();
    one.add(1);
    util.append(this.one, none);
    t.checkExpect(this.one, one);
  }

  // tests util inRange(Posn, Posn)
  boolean utilInRange(Tester t) {
    return t.checkExpect(util.inRange(new Posn(0, 0), new Posn(20, 20)), true)
        && t.checkExpect(util.inRange(new Posn(100, 100), new Posn(20, 20)), false)
        && t.checkExpect(util.inRange(new Posn(60, 60), new Posn(60, 60)), true)
        && t.checkExpect(util.inRange(new Posn(101, 100), new Posn(60, 60)), false);
  }

  // tests util sproutDandelions(Posn, ArrayList<ITile>)
  void testUtilSproutDandelions(Tester t) {
    this.initTestConditions();
    ArrayList<ITile> changedGarden = new ArrayList<>();
    changedGarden.add(new DandelionTile(20, 20, 3, 40));
    util.sproutDandelion(new Posn(20, 20), garden_1);
    t.checkExpect(garden_1, changedGarden);

    changedGarden = new ArrayList<>();
    changedGarden.add(tile_6);
    changedGarden.add(new DandelionTile(20, 60, 3, 80));
    changedGarden.add(tile_8);
    changedGarden.add(tile_9);

    util.sproutDandelion(new Posn(20, 60), garden_2);
    t.checkExpect(garden_2, changedGarden);
  }

  // tests util sproutDanInPosns(ArrayList<Posn>, ArrayList<ITile>)
  void testUtilSproutDanInPosns(Tester t) {
    this.initTestConditions();
    ArrayList<ITile> changedGarden = new ArrayList<>();
    changedGarden.add(new DandelionTile(20, 20, 3, 40));
    ArrayList<Posn> hitbox = new ArrayList<>();
    hitbox.add(new Posn(20, 20));
    util.sproutDanInPosns(hitbox, garden_1);
    t.checkExpect(garden_1, changedGarden);

    changedGarden = new ArrayList<>();
    changedGarden.add(tile_6);
    changedGarden.add(new DandelionTile(20, 60, 3, 80));
    changedGarden.add(new DandelionTile(60, 20, 3, 80));
    changedGarden.add(tile_9);

    hitbox = new ArrayList<>();
    hitbox.add(new Posn(20, 60));
    hitbox.add(new Posn(60, 20));

    util.sproutDanInPosns(hitbox, garden_2);
    t.checkExpect(garden_2, changedGarden);
  }

  // tests for ITile interface

  // draw(WorldScene) can be seen in the big bang world

  // tests ITile samePos(Posn)
  boolean testITileSamePos(Tester t) {
    this.initTestConditions();
    return t.checkExpect(tile_1.samePos(new Posn(20, 20)), true)
        && t.checkExpect(tile_1.samePos(new Posn(20, 21)), false);
  }

  // tests ITile replaceTile(String, int)
  boolean testITileReplaceTile(Tester t) {
    this.initTestConditions();
    return t.checkExpect(tile_0.replaceTile("LeftButton", 380),
        new DandelionTile(20, 60, 3, 400))
        && t.checkExpect(tile_0.replaceTile("RightButton", 380),
        new PebbleTile(20, 60, 400))
        && t.checkExpect(tile_1.replaceTile("LeftButton", 380),
        new GrassTile(20, 20, 400))
        && t.checkExpect(tile_2.replaceTile("LeftButton", 380),
        new GrassTile(60, 60, 400))
        && t.checkExpect(tile_2.replaceTile("RightButton", 380), tile_2)
        && t.checkExpect(tile_1.replaceTile("LeftButton", 20), tile_1);
  }

  // accept(ITileVisitor) is tested implicitly by other ITileVistor

  // tests ITile lowerHP()
  void testITileLowerHP(Tester t) {
    this.initTestConditions();
    ITile pebbleTile = new PebbleTile(20, 20, 400);
    tile_1.lowerHP();
    t.checkExpect(tile_1, pebbleTile);

    ITile dandelionTile = new DandelionTile(60, 60, 2, 400);
    tile_2.lowerHP();
    t.checkExpect(tile_2, dandelionTile);

    ITile grassTile = new GrassTile(100, 100, 400);
    tile_3.lowerHP();
    t.checkExpect(tile_3, grassTile);
  }

  // tests ITile fullHP()
  void testITileFullHP(Tester t) {
    this.initTestConditions();
    ITile pebbleTile = new PebbleTile(20, 20, 400);
    tile_1.fullHP();
    t.checkExpect(tile_1, pebbleTile);

    ITile dan_0 = new DandelionTile(20, 60, 3, 80);
    ITile dan_1 = new DandelionTile(60, 60, 3, 80);
    ITile dan_2 = new DandelionTile(60, 20, 3, 80);
    tile_10.fullHP();
    tile_11.fullHP();
    tile_12.fullHP();
    t.checkExpect(tile_10, dan_0);
    t.checkExpect(tile_11, dan_1);
    t.checkExpect(tile_12, dan_2);

    ITile grassTile = new GrassTile(100, 100, 400);
    tile_3.fullHP();
    t.checkExpect(tile_3, grassTile);
  }

  // tests ITile noHP()
  boolean testITileNoHP(Tester t) {
    this.initTestConditions();
    ITile dan_0 = new DandelionTile(20, 20, 0, 3);
    return t.checkExpect(tile_10.noHP(), false)
        && t.checkExpect(dan_0.noHP(), true)
        && t.checkExpect(tile_1.noHP(), false);
  }

  // tests ITile hitBox()
  boolean testITileHitBox(Tester t) {
    this.initTestConditions();
    ArrayList<Posn> hitBoxOne = new ArrayList<>();
    ArrayList<Posn> hitBoxTwo = new ArrayList<>();
    ArrayList<Posn> hitBoxThree = new ArrayList<>();
    ArrayList<Posn> hitBoxFour = new ArrayList<>();
    hitBoxOne.add(new Posn(20, 20));
    hitBoxOne.add(new Posn(60, 20));
    hitBoxOne.add(new Posn(20, 60));

    hitBoxTwo.add(new Posn(140, 180));
    hitBoxTwo.add(new Posn(100, 180));
    hitBoxTwo.add(new Posn(180, 180));
    hitBoxTwo.add(new Posn(140, 140));
    hitBoxTwo.add(new Posn(140, 220));

    hitBoxThree.add(new Posn(60, 20));
    hitBoxThree.add(new Posn(20, 20));
    hitBoxThree.add(new Posn(100, 20));
    hitBoxThree.add(new Posn(60, 60));

    hitBoxFour.add(new Posn(380, 20));
    hitBoxFour.add(new Posn(340, 20));
    hitBoxFour.add(new Posn(380, 60));

    return t.checkExpect(tile_2.hitBox(), util.singletonList(new Posn(60, 60)))
        && t.checkExpect(tile_1.hitBox(), hitBoxOne)
        && t.checkExpect(tile_4.hitBox(), hitBoxTwo)
        && t.checkExpect(tile_13.hitBox(), hitBoxThree)
        && t.checkExpect(tile_14.hitBox(), hitBoxFour);
  }

  // tests ITile inRange(Posn)
  void testITileInRange(Tester t) {
    this.initTestConditions();
    Posn first = new Posn(21, 21);
    Posn second = new Posn(380, 225);
    Posn third = new Posn(62, 60);
    Posn fourth = new Posn(342, 20);
    Posn fifth = new Posn(20, 20);

    t.checkExpect(tile_1.inRange(first), true);
    t.checkExpect(tile_4.inRange(second), false);
    t.checkExpect(tile_13.inRange(third), true);
    t.checkExpect(tile_14.inRange(fourth), true);
    t.checkExpect(tile_1.inRange(fifth), true);
  }

  // tests for IProjectile

  // draw(WorldScene) can be seen in the big bang world

  // tests IProjectile move()
  void testIProjectileMove(Tester t) {
    this.initTestConditions();

    IWaterBalloon waterBalloon_0 = new WaterBalloon(0, -10, 10);
    this.waterBalloon_0.move();
    t.checkExpect(this.waterBalloon_0, waterBalloon_0);

    IWaterBalloon waterBalloon_2 = new WaterBalloon(2, -1, 4);
    this.waterBalloon_2.move();
    t.checkExpect(this.waterBalloon_2, waterBalloon_2);

    IWaterBalloon noBalloon = new NoWaterBalloon();
    waterBalloon_3.move();
    t.checkExpect(waterBalloon_3, noBalloon);

    IDart dart_0 = new Dart(0, -10, 10);
    this.dart_0.move();
    t.checkExpect(this.dart_0, dart_0);

    IDart dart_1 = new Dart(2, -12, 11);
    this.dart_1.move();
    t.checkExpect(this.dart_1, dart_1);

    IDart noDart = new NoDart();
    dart_3.move();
    t.checkExpect(dart_3, noDart);
  }

  // tests IProjectile offScreen()
  boolean testIProjectileOffScreen(Tester t) {
    this.initTestConditions();
    return t.checkExpect(waterBalloon_0.offScreen(), true)
        && t.checkExpect(waterBalloon_1.offScreen(), true)
        && t.checkExpect(waterBalloon_2.offScreen(), false)
        && t.checkExpect(waterBalloon_3.offScreen(), true)
        && t.checkExpect(dart_0.offScreen(), true)
        && t.checkExpect(dart_1.offScreen(), true)
        && t.checkExpect(dart_2.offScreen(), false)
        && t.checkExpect(dart_3.offScreen(), true);
  }

  // tests IProjectile hitBodySeg(BodySeg)
  boolean testIProjectileHitBodySeg(Tester t) {
    this.initTestConditions();
    return t.checkExpect(waterBalloon_1.hitBodySeg(bodySeg_0), false)
        && t.checkExpect(waterBalloon_4.hitBodySeg(bodySeg_0), true)
        && t.checkExpect(waterBalloon_5.hitBodySeg(bodySeg_0), true)
        && t.checkExpect(waterBalloon_3.hitBodySeg(bodySeg_1), false)
        && t.checkExpect(dart_1.hitBodySeg(bodySeg_0), false)
        && t.checkExpect(dart_4.hitBodySeg(bodySeg_0), true)
        && t.checkExpect(dart_5.hitBodySeg(bodySeg_0), true)
        && t.checkExpect(dart_3.hitBodySeg(bodySeg_1), false);
  }

  // tests IProjectile hitTile(ITile)
  boolean testIProjectHitTile(Tester t) {
    this.initTestConditions();
    return t.checkExpect(waterBalloon_1.hitTile(tile_0), false)
        && t.checkExpect(waterBalloon_4.hitTile(tile_15), true)
        && t.checkExpect(waterBalloon_5.hitTile(tile_15), true)
        && t.checkExpect(waterBalloon_3.hitTile(tile_2), false)
        && t.checkExpect(dart_1.hitTile(tile_15), false)
        && t.checkExpect(dart_4.hitTile(tile_15), true)
        && t.checkExpect(dart_5.hitTile(tile_15), true)
        && t.checkExpect(dart_3.hitTile(tile_5), false);
  }

  // tests for IDart

  // tests IDart missed()
  boolean testIDartMissed(Tester t) {
    this.initTestConditions();
    return t.checkExpect(dart_0.missed(), true)
        && t.checkExpect(dart_1.missed(), true)
        && t.checkExpect(dart_2.missed(), false)
        && t.checkExpect(dart_3.missed(), false);
  }

  // tests for IWaterBalloon

  // test WaterBalloon tileInHitBox(ITile)
  // NOTE: this implicitly tests hitBox() in WaterBalloon
  void testWaterBalloonTileInHitbox(Tester t) {
    for (ITile tile : garden_5) {
      t.checkExpect(waterBalloon_8.tileInHitBox(tile), true);
    }
    t.checkExpect(waterBalloon_8.tileInHitBox(tile_16), false);
    t.checkExpect(waterBalloon_8.tileInHitBox(tile_14), false);
  }

  // test WaterBalloon bodySegInHitbox(BodySeg)
  boolean testIWaterBalloonBodySegInHitbox(Tester t) {
    this.initTestConditions();
    return t.checkExpect(waterBalloon_7.bodySegInHitbox(bodySeg_0), true)
        && t.checkExpect(waterBalloon_7.bodySegInHitbox(bodySeg_1), true)
        && t.checkExpect(waterBalloon_7.bodySegInHitbox(bodySeg_4), true)
        && t.checkExpect(waterBalloon_7.bodySegInHitbox(bodySeg_5), true)
        && t.checkExpect(waterBalloon_7.bodySegInHitbox(bodySeg_6), true)
        && t.checkExpect(waterBalloon_7.bodySegInHitbox(bodySeg_7), true)
        && t.checkExpect(waterBalloon_7.bodySegInHitbox(bodySeg_8), true)
        && t.checkExpect(waterBalloon_7.bodySegInHitbox(bodySeg_9), true)
        && t.checkExpect(waterBalloon_7.bodySegInHitbox(bodySeg_10), true)
        && t.checkExpect(waterBalloon_7.bodySegInHitbox(bodySeg_11), false)
        && t.checkExpect(waterBalloon_3.bodySegInHitbox(bodySeg_0), false);
  }

  // test IWaterBalloon explode(ArrayList<Centipede>, ArrayList<ITile>)
  void testIWaterBalloonExplode(Tester t) {
    this.initTestConditions();
    IWaterBalloon noBalloon = new NoWaterBalloon();

    waterBalloon_3.explode(cent_arr_0, garden_1);
    t.checkExpect(waterBalloon_3, noBalloon);

    waterBalloon_7.explode(cent_arr_4, garden_3);

    ArrayList<ITile> newGarden = util.generateGrassBoard(3, 3);
    for (int index = 0; index < newGarden.size(); index += 1) {
      newGarden.set(index, new GrassToDan().apply(newGarden.get(index)));
    }

    t.checkExpect(cent_arr_4, new ArrayList<>());
    t.checkExpect(garden_3, newGarden);

    this.initTestConditions();
    waterBalloon_7.explode(cent_arr_34, garden_3);
    t.checkExpect(cent_arr_34, util.singletonList(cent_3));

    this.initTestConditions();

    for (int index = 0; index < garden_3.size(); index += 1) {
      garden_3.set(index, new GrassToDan().apply(newGarden.get(index)));
      garden_3.get(index).lowerHP();
    }
    waterBalloon_7.explode(cent_arr_4, garden_3);
    t.checkExpect(cent_arr_4, new ArrayList<>());
    t.checkExpect(garden_3, garden_5);
  }

  // tests for Centipede

  // draw() can be seen in the big bang world

  // already checked splashHit(IWaterBalloon), getIndicesHit(IWaterBalloon), posnsHit(IWaterBalloon)
  // in explode() of IWaterBalloon, and waterBalloonHit(IWaterBalloon) from IProjectile

  // tests Centipede dartHit(IDart)
  boolean testCentipedeDartHit(Tester t) {
    this.initTestConditions();
    return t.checkExpect(cent_0.dartHit(dart_4), true)
        && t.checkExpect(cent_4.dartHit(dart_5), true)
        && t.checkExpect(cent_3.dartHit(dart_4), false)
        && t.checkExpect(cent_2.dartHit(dart_3), false);
  }

  // tests Centipede waterBalloonHit(IWaterBalloon)
  boolean testCentipedeWaterBalloonHit(Tester t) {
    this.initTestConditions();
    return t.checkExpect(cent_0.waterBalloonHit(waterBalloon_4), true)
        && t.checkExpect(cent_4.waterBalloonHit(waterBalloon_5), true)
        && t.checkExpect(cent_3.waterBalloonHit(waterBalloon_4), false)
        && t.checkExpect(cent_2.waterBalloonHit(waterBalloon_3), false);
  }

  // tests Centipede hitPlayer(Gnome)
  boolean testCentipedeHitPlayer(Tester t) {
    this.initTestConditions();
    return t.checkExpect(cent_4.hitPlayer(player_3), true)
        && t.checkExpect(cent_3.hitPlayer(player_4), false)
        && t.checkExpect(cent_4.hitPlayer(player_4), true);
  }

  // tests Centipede onPebble(ArrayList<ITile>)
  void testCentipedeOnPebble(Tester t) {
    this.initTestConditions();
    t.checkExpect(cent_4.onPebble(garden_5), true);
    ArrayList<ITile> pebs = new ArrayList<>();
    pebs.add(garden_5.get(4));
    t.checkExpect(cent_4.pebsAlreadyOn, pebs);

    this.initTestConditions();
    t.checkExpect(cent_3.onPebble(garden_5), false);
    t.checkExpect(cent_3.pebsAlreadyOn, new ArrayList<>());

    this.initTestConditions();
    t.checkExpect(cent_0.onPebble(garden_5), false);
    t.checkExpect(cent_0.pebsAlreadyOn, new ArrayList<>());
  }

  // tests Centipede inPebsAlreadyOn(ITile)
  void testCentipedeInPebsAlreadyOn(Tester t) {
    this.initTestConditions();
    // onPebble is supposed to add it to the ArrayList
    cent_4.onPebble(garden_5);
    t.checkExpect(cent_4.inPebsAlreadyOn(garden_5.get(4)), true);
    ITile pebble = new PebbleTile(60, 60, 120);
    t.checkExpect(cent_4.inPebsAlreadyOn(pebble), false);
    // there is no way to remove a pebble currently, so it will always be the same
    // reference, thus the == comparison
    this.initTestConditions();
    cent_0.onPebble(garden_5);
    t.checkExpect(cent_0.inPebsAlreadyOn(garden_5.get(4)), false);
    t.checkExpect(cent_4.inPebsAlreadyOn(garden_5.get(4)), false);
  }

  // Centipede onPebble already tested anyInRange(ITile tile)

  // tests for BodySeg

  // draw() can be seen in the big bang world

  // already checked tilePosn() in explode() of IWaterBalloon and posnInRange(Posn pos)
  // and bodySegInRange() of IProjectile

//  // runs the game - the setup first, then the game by pressing "s"
//  void testBigBang(Tester t) {
//    int x = 10;
//    int y = 15;
//    GameMaster w = new GameMaster(x, y, 20);
//    w.bigBang(x * ITile.WIDTH, y * ITile.HEIGHT, 1.0 / 28.0);
//  }
}
