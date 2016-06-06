package in.mn.life.activity;

import android.os.Bundle;

import butterknife.OnClick;
import in.mn.life.fragment.SquareWorldFragment;
import in.mn.life.game.Console;
import in.mn.life.game.GameEngine;
import in.mn.life.game.algorithm.SimpleLife;
import in.mn.life.game.entity.Cell;
import in.mn.life.game.entity.SquareWorld;
import in.mn.life.game.entity.World;
import in.mn.world.R;

public class GameActivity extends BaseActivity implements Console<SquareWorld>,
        SquareWorldFragment.OnFragmentInteractionListener {
    private static final String ARG_IS_LIVING = "arg_is_living";
    private static final String ARG_WORLD = "arg_world";
    private GameEngine<SquareWorld, SimpleLife> mGameEngine;
    private SquareWorld mSquareWorld;
    private static final int WORLD_SIZE = 20;
    private boolean isLiving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ARG_IS_LIVING)) {
                isLiving = savedInstanceState.getBoolean(ARG_IS_LIVING);
            }
            if (savedInstanceState.containsKey(ARG_WORLD)) {
                mSquareWorld = savedInstanceState.getParcelable(ARG_WORLD);
            }
        }
        initGame();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGameEngine != null) {
            mGameEngine.stop();
        }
    }

    private void initGame() {
        mGameEngine = new GameEngine<>(new SimpleLife(), this);
        if (mSquareWorld == null) {
            mSquareWorld = new SquareWorld(WORLD_SIZE);
        }
        updateGameFragment(mSquareWorld);
        if (isLiving) {
            onGameStart();
        }
    }

    @OnClick(R.id.start)
    public void onGameStart() {
        isLiving = true;
        mGameEngine.start(mSquareWorld);
    }

    @OnClick(R.id.stop)
    public void onGameStop() {
        isLiving = false;
        mGameEngine.pause();
    }

    @Override
    public World getLife() {
        return null;
    }

    @Override
    public void updateLife(SquareWorld world) {
        mSquareWorld = world;
        updateGameFragment(world);
    }

    @Override
    public void announceGameEnd() {

    }

    @Override
    public void onCellSelected(Cell cell) {
        Cell newCell = new Cell(cell.getRow(), cell.getColumn());
        newCell.setState(cell.isAlive() ? Cell.State.DEAD : Cell.State.ALIVE);
        mSquareWorld.setCell(newCell);
        updateGameFragment(mSquareWorld);
    }

    @Override
    public int getWorldSize() {
        return WORLD_SIZE;
    }

    private void updateGameFragment(SquareWorld world) {
        SquareWorldFragment squareWorldFragment = (SquareWorldFragment) getSupportFragmentManager().findFragmentById(R.id.game_fragment);
        if (squareWorldFragment == null) {
            throw new IllegalStateException("Game fragment cannot be found");
        }
        squareWorldFragment.setSquareWorld(world);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ARG_IS_LIVING, isLiving);
        outState.putParcelable(ARG_WORLD, mSquareWorld);
    }
}
