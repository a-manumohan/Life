package in.mn.life.game.algorithm;

import in.mn.life.game.entity.World;
import rx.Observable;

/**
 * Created by manuMohan on 01/06/2016.
 */

public interface Life<T extends World> {
    /**
     *
     * @param world world to generate next generations
     * @param age of a single generation
     * @return Observable which emits generations
     */
    Observable<T> getNextGenerations(T world, int age);
}
