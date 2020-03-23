// represents a generic function object
interface IFunc<T, R> {
  // applies this function to the given argument
  R apply(T arg);
}

// represents a generic visitor for lists of type T, returning a value of type R
interface ITileVisitor<T> extends IFunc<ITile, T> {
  // applies this visitor to a given tile
  T apply(ITile tile);

  // visits the given grass tile and applies this visitor's function
  T visitGrass(GrassTile tile);

  // visits the given dandelion tile and applies this visitor's function
  T visitDan(DandelionTile tile);

  // visits the given pebble tile and applies this visitor's function
  T visitPeb(PebbleTile tile);
}

// represents a functional object that visits a tile and tells if it is a grass
class IsGrass implements ITileVisitor<Boolean>, IFunc<ITile, Boolean> {
  // applies this predicate on the given tile  to tell if it is a grass
  public Boolean apply(ITile tile) {
    return tile.accept(this);
  }

  // visits a grass tile and tells if it is a grass tile, which is always true
  public Boolean visitGrass(GrassTile tile) {
    return true;
  }

  // visits a dandelion tile and tells if it is a grass tile, which is always
  // false
  public Boolean visitDan(DandelionTile tile) {
    return false;
  }

  // visits a Pebble tile and tells if it is a grass tile, which is always false
  public Boolean visitPeb(PebbleTile tile) {
    return false;
  }
}

// represents a functional object that visits a tile and tells if it is a dandelion
class IsDandelion implements ITileVisitor<Boolean>, IFunc<ITile, Boolean> {
  // applies this predicate on the given tile to tell if it is a dandelion
  public Boolean apply(ITile tile) {
    return tile.accept(this);
  }

  // visits a grass tile and tells if it is a dandelion tile, which is always false
  public Boolean visitGrass(GrassTile tile) {
    return false;
  }

  // visits a dandelion tile and tells if it is a dandelion tile, which is always
  // true
  public Boolean visitDan(DandelionTile tile) {
    return true;
  }

  // visits a Pebble tile and tells if it is a dandelion tile, which is always false
  public Boolean visitPeb(PebbleTile tile) {
    return false;
  }
}

// represents a functional object that visits a tile and tells if it is a pebble
class IsPebble implements ITileVisitor<Boolean>, IFunc<ITile, Boolean> {
  // applies this predicate on the given tile to tell if it is a pebble
  public Boolean apply(ITile tile) {
    return tile.accept(this);
  }

  // visits a grass tile and tells if it is a pebble tile, which is always false
  public Boolean visitGrass(GrassTile tile) {
    return false;
  }

  // visits a dandelion tile and tells if it is a pebble tile, which is always
  // false
  public Boolean visitDan(DandelionTile tile) {
    return false;
  }

  // visits a Pebble tile and tells if it is a pebble tile, which is always true
  public Boolean visitPeb(PebbleTile tile) {
    return true;
  }
}

// converts this tile to a pebble if the tile is a dandelion
class DanToPeb implements ITileVisitor<ITile> {
  // applies this visitor to the given tile, meaning that it changes the dandelion tile
  // to a pebble tile
  public ITile apply(ITile tile) {
    return tile.accept(this);
  }

  // in effect, this function does nothing to a grass because it is not a
  // dandelion
  public ITile visitGrass(GrassTile tile) {
    return tile;
  }

  // changes the given DandelionTile to a PebbleTile
  public ITile visitDan(DandelionTile tile) {
    return new PebbleTile(tile.row, tile.col, tile.width);
  }

  // in effect, this function does nothing to a PebbleTile because it already is
  // one
  public ITile visitPeb(PebbleTile tile) {
    return tile;
  }
}

// converts this tile to a pebble if the tile is a dandelion
class GrassToDan implements ITileVisitor<ITile> {
  // applies this visitor to the given tile, meaning that it changes the dandelion tile
  // to a pebble tile
  public ITile apply(ITile tile) {
    return tile.accept(this);
  }

  // in effect, this function does nothing to a grass because it is not a
  // dandelion
  public ITile visitGrass(GrassTile tile) {
    return new DandelionTile(tile.row, tile.col, ITile.FULL_HP, tile.width);
  }

  // changes the given DandelionTile to a PebbleTile
  public ITile visitDan(DandelionTile tile) {
    return tile;
  }

  // in effect, this function does nothing to a PebbleTile because it already is
  // one
  public ITile visitPeb(PebbleTile tile) {
    return tile;
  }
}