package in.mn.life.game.algorithm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import in.mn.life.game.entity.Cell;
import in.mn.life.game.entity.SquareWorld;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by manuMohan on 06/06/2016.
 */
public class SimpleLifeTest {
    private int mSize = 5;
    private SquareWorld mSquareWorld;
    private SimpleLife mSimpleLife;

    private int[] initCells = {
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 1, 1, 1, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 1, 0
    };
    private int[] firstGenCells = {
            0, 0, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 1, 1, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0
    };
    private int[] secondGenCells = {
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 1, 1, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 1, 0, 0
    };

    @Before
    public void setUp() throws Exception {
        mSquareWorld = new SquareWorld(mSize);
        mSimpleLife = new SimpleLife();
    }

    @After
    public void tearDown() throws Exception {
        mSquareWorld = null;
        mSimpleLife = null;
    }

    @Test
    public void getNextGenerationCell() throws Exception {
        int row = 1, column = 2;
        buildWorld(mSquareWorld, initCells, mSize);
        Cell cell = mSquareWorld.getCells().get(row * mSize + column);
        Cell nextGenCell = mSimpleLife.getNextGenerationCell(mSquareWorld, cell);

        assertNotNull(nextGenCell);
        assertTrue(nextGenCell.isAlive());

        cell = mSquareWorld.getCells().get(0);
        nextGenCell = mSimpleLife.getNextGenerationCell(mSquareWorld, cell);
        assertNotNull(nextGenCell);
        assertFalse(nextGenCell.isAlive());
    }

    @Test
    public void getNextGenerationWorld() throws Exception {
        buildWorld(mSquareWorld, initCells, mSize);
        SquareWorld expectedNextGenWorld = new SquareWorld(mSize);
        buildWorld(expectedNextGenWorld, firstGenCells, mSize);

        SquareWorld actualNextGenWorld = mSimpleLife.getNextGenerationWorld(mSquareWorld);
        assertEquals("Next generation world is wrong", expectedNextGenWorld, actualNextGenWorld);

    }

    //This test throws a compiler exception
//    @Test
//    public void getNextGenerations() throws Exception {
//        buildWorld(mSquareWorld, initCells, mSize);
//        SquareWorld expectedFirstGenWorld = new SquareWorld(mSize);
//        SquareWorld expectedSecondGenWorld = new SquareWorld(mSize);
//
//        final int[] count = {0};
//        CountDownLatch latch = new CountDownLatch(2);
//        Subscription subscription = mSimpleLife.getNextGenerations(mSquareWorld, 10).subscribe(
//                squareWorld -> {
//                    if (count[0] == 0) {
//                        assertEquals("First Generation is wrong", expectedFirstGenWorld, squareWorld);
//                    }
//                    if (count[0] == 1) {
//                        assertEquals("Second Generation is wrong", expectedSecondGenWorld, squareWorld);
//                    }
//                    latch.countDown();
//                    count[0]++;
//                }
//        );
//        latch.await();
//        subscription.unsubscribe();
//    }

    private void buildWorld(SquareWorld squareWorld, final int[] cells, final int size) {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                Cell cell = new Cell(i, j);
                cell.setState(cells[i * size + j] == 0 ? Cell.State.DEAD : Cell.State.ALIVE);
                squareWorld.setCell(cell);
            }
        }
    }

}