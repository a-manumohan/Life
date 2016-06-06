package in.mn.life.game;

import in.mn.life.game.entity.World;

/**
 * Created by manuMohan on 01/06/2016.
 */

public interface Console<T> {
    World getLife();

    void updateLife(T world);

    void announceGameEnd();
}
