package in.mn.life.game.algorithm;

import in.mn.life.game.entity.Cell;
import in.mn.life.game.entity.SquareWorld;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by manuMohan on 01/06/2016.
 */

public class SimpleLife implements Life<SquareWorld> {
    private SquareWorld currentWorld;

    @Override
    public Observable<SquareWorld> getNextGenerations(final SquareWorld world, int age) {
        currentWorld = world;
        return Observable.create(new Observable.OnSubscribe<SquareWorld>() {
            @Override
            public void call(Subscriber<? super SquareWorld> subscriber) {
                if (subscriber != null && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(world);
                }
                while (true) {
                    SquareWorld nextGenerationWorld = getNextGenerationWorld(currentWorld);
                    try {
                        Thread.sleep(age);
                    } catch (InterruptedException e) {
                        //sleep interrupted. stop emitting values
                        break;
                    }
                    if (subscriber != null) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(nextGenerationWorld);
                        } else {
                            break;
                        }
                    }
                    currentWorld = nextGenerationWorld;
                }
            }
        });
    }

    SquareWorld getNextGenerationWorld(final SquareWorld world) {
        SquareWorld nextGenerationWorld = new SquareWorld(world.getSize());
        for (Cell cell : world.getCells()) {
            nextGenerationWorld.setCell(getNextGenerationCell(world, cell));
        }
        return nextGenerationWorld;
    }

    Cell getNextGenerationCell(final SquareWorld world, final Cell cell) {
        int neighbourCount = world.getAliveNeighboursCount(cell);
        Cell nextGenCell = new Cell(cell.getRow(), cell.getColumn());
        if (cell.isAlive()) {
            if (neighbourCount < 2) { //alive cell with less than 2 neighbours dies
                nextGenCell.setState(Cell.State.DEAD);
            } else if (neighbourCount == 2 || neighbourCount == 3) {//alive cell with 2 or 3 neighbours survives
                nextGenCell.setState(Cell.State.ALIVE);
            } else {//alive cell with more than 3 neighbours dies
                nextGenCell.setState(Cell.State.DEAD);
            }
        } else {
            if (neighbourCount == 3) {//dead cell with exactly 3 neighbours lives
                nextGenCell.setState(Cell.State.ALIVE);
            }
        }
        return nextGenCell;
    }
}
