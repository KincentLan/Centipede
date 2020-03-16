import javalib.worldimages.Posn;

//represents a generic function object
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

// represents a functional object that visits a tile and tells if it is a Dandelion
class IsDandelion implements ITileVisitor<Boolean>, IFunc<ITile, Boolean> {
  // applies this predicate on the given tile  to tell if it is a grass
  public Boolean apply(ITile tile) {
    return tile.accept(this);
  }

  // visits a grass tile and tells if it is a grass tile, which is always true
  public Boolean visitGrass(GrassTile tile) {
    return false;
  }

  // visits a dandelion tile and tells if it is a grass tile, which is always
  // false
  public Boolean visitDan(DandelionTile tile) {
    return true;
  }

  // visits a Pebble tile and tells if it is a grass tile, which is always false
  public Boolean visitPeb(PebbleTile tile) {
    return false;
  }
}

// represents a functional object that visits a tile and tells if it is a grass
class IsPebble implements ITileVisitor<Boolean>, IFunc<ITile, Boolean> {
  // applies this predicate on the given tile  to tell if it is a grass
  public Boolean apply(ITile tile) {
    return tile.accept(this);
  }

  // visits a grass tile and tells if it is a grass tile, which is always true
  public Boolean visitGrass(GrassTile tile) {
    return false;
  }

  // visits a dandelion tile and tells if it is a grass tile, which is always
  // false
  public Boolean visitDan(DandelionTile tile) {
    return false;
  }

  // visits a Pebble tile and tells if it is a grass tile, which is always false
  public Boolean visitPeb(PebbleTile tile) {
    return true;
  }
}