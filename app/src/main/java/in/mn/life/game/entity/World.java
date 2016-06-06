package in.mn.life.game.entity;


import java.util.List;

/**
 * Created by manuMohan on 01/06/2016.
 */

public interface World {
    int getAliveNeighboursCount(Cell cell);

    void setCell(Cell cell);

    List<Cell> getCells();
}
