package in.mn.life.game;

import in.mn.life.game.algorithm.Life;
import in.mn.life.game.entity.World;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by manuMohan on 01/06/2016.
 */

public class GameEngine<T extends World, U extends Life<T>> {
    private T mWorld;
    private U mLife;
    private Console<T> mConsole;
    private static final int GENERATION_AGE = 500;
    private boolean isLiving = false;
    private Subscription mSubscription;

    public GameEngine(U life, Console console) {
        mLife = life;
        mConsole = console;
    }

    public void start(T world) {
        mWorld = world;
        liveLife(mWorld);
    }

    public void stop() {
        unsubscribe();
    }

    public void pause() {
        unsubscribe();
    }

    public void resume() {
        liveLife(mWorld);
    }

    private void unsubscribe() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    private void liveLife(T world) {
        evolveLife(world, world1 -> {
            mWorld = world1;
            mConsole.updateLife(world1);
        });
    }

    private void evolveLife(T world, final LifeListener<T> lifeListener) {
        mSubscription = mLife.getNextGenerations(world, GENERATION_AGE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lifeListener::onNextGeneration
                );
    }

    private interface LifeListener<T> {
        void onNextGeneration(T world);
    }
}
