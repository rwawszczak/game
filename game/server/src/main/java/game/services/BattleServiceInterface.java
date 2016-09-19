package game.services;

import game.model.Battle;

public interface BattleServiceInterface {
    Battle createNewBattle();

    Battle getBattle(long id);

    void deleteBattle(long id);
}
