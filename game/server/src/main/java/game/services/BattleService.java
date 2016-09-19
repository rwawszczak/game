package game.services;

import game.model.Battle;

import java.util.HashMap;
import java.util.Map;

public class BattleService implements BattleServiceInterface {
    private long nextBattleId = 0L;
    private Map<Long, Battle> battleMap = new HashMap<Long, Battle>();

    @Override
    public Battle createNewBattle(){
        Battle battle = new Battle(++nextBattleId);
        battleMap.put(battle.getId(), battle);
        return battle;
    }

    @Override
    public Battle getBattle(long id){
        return battleMap.get(id);
    }

    @Override
    public void deleteBattle(long id){
        battleMap.remove(id);
    }
}
