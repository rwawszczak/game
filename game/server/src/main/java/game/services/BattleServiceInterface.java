package game.services;

import game.model.domain.Battle;

public interface BattleServiceInterface {
    Battle createNewBattle();

    Battle getBattle(long id);

    void deleteBattle(long id);
}
