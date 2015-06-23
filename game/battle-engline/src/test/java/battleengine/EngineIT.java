package battleengine;

import battleengine.action.Actions;
import battleengine.action.elemental.BoostAttackAction;
import battleengine.action.elemental.HealAction;
import battleengine.action.log.BattleLog;
import battleengine.action.log.LogItem;
import battleengine.action.player.AttackAction;
import battleengine.action.player.DefendAction;
import battleengine.entities.BattleEntity;
import battleengine.entities.Element;
import battleengine.entities.elemental.Elemental;
import battleengine.entities.player.Attributes;
import battleengine.entities.player.Player;
import battleengine.entities.player.Players;
import battleengine.gateway.CoefficientGateway;
import org.junit.Before;
import org.junit.Test;

import static java.lang.Math.min;
import static org.junit.Assert.assertEquals;

/**
 * Created by RaV on 09.05.15.
 */
public class EngineIT {

    private static final String P1_NAME = "Player 1";
    private static final String P2_NAME = "Player 2";
    private static final String WATER_ELEMENTAL_NAME = "Water Elemental";
    private static final String FIRE_ELEMENTAL_NAME = "Fire Elemental";

    private static final int P1_HP = 100;
    private static final int P1_MANA = 50;
    private static final int P1_ATTACK = 20;
    private static final int P1_DEXTERITY = 10;
    private static final int P1_DEFENCE = 50;
    private static final int P2_INTELLIGENCE = 10;
    private static final int P1_SPEED = 20;

    private static final int P1_LUCK = 0;
    private static final int P2_HP = 100;
    private static final int P2_MANA = 50;
    private static final int P2_ATTACK = 10;
    private static final int P2_DEXTERITY = 0;
    private static final int P2_DEFENCE = 50;
    private static final int P1_INTELLIGENCE = 10;
    private static final int P2_SPEED = 10;

    private static final int P2_LUCK = 0;


    BattleEngine engine;
    Players players;

    @Before
    public void setUp() throws Exception {
        engine = new BattleEngine();
        players = new Players(
                new Player(P1_NAME, new Attributes(P1_HP, P1_MANA, P1_ATTACK, P1_DEXTERITY, P1_DEFENCE, P1_INTELLIGENCE, P1_SPEED, P1_LUCK)),
                new Player(P2_NAME, new Attributes(P2_HP, P2_MANA, P2_ATTACK, P2_DEXTERITY, P2_DEFENCE, P2_INTELLIGENCE, P2_SPEED, P2_LUCK)));
    }

    @Test
    public void testBasicAttackAction() throws Exception {
        Actions actions = new Actions(new AttackAction(players.get(P1_NAME), players.get(P2_NAME)));

        BattleLog log = engine.processTurn(actions).getBattleLog();

        assertEquals(87, players.get(P2_NAME).getCurrentHP());
        assertEquals(1,log.size());
        assertLog(log.getItem(0), 13, players.get(P1_NAME),players.get(P2_NAME), 0, true, 0);
    }

    @Test
    public void testCriticalHitAttack() throws Exception {
        players.get(P1_NAME).getAttributes().increaseLuck(1);
        Actions actions = new Actions(new AttackAction(players.get(P1_NAME), players.get(P2_NAME)));

        BattleLog log = engine.processTurn(actions).getBattleLog();

        assertEquals(81, players.get(P2_NAME).getCurrentHP());
        assertEquals(1,log.size());
        assertLog(log.getItem(0), 19, players.get(P1_NAME), players.get(P2_NAME), CoefficientGateway.getLogValue().ofCriticalStrike(), true, 0);
    }

    @Test
    public void testMissedAttack() throws Exception {
        Actions actions = new Actions(new AttackAction(players.get(P2_NAME), players.get(P1_NAME)));

        BattleLog log = engine.processTurn(actions).getBattleLog();

        assertEquals(P1_HP, players.get(P1_NAME).getCurrentHP());
        assertEquals(1,log.size());
        assertLog(log.getItem(0), 0, players.get(P2_NAME), players.get(P1_NAME), 0, false, 0);
    }

    @Test
    public void testBasicAttackWithDefend() throws Exception {
        Actions actions = new Actions();
        actions.add(new AttackAction(players.get(P1_NAME), players.get(P2_NAME)));
        actions.add(new DefendAction(players.get(P2_NAME)));

        BattleLog log = engine.processTurn(actions).getBattleLog();

        assertEquals(88, players.get(P2_NAME).getCurrentHP());
        assertEquals(P2_DEFENCE, players.get(P2_NAME).getAttributes().getDefence());
        assertEquals(2,log.size());
        assertLog(log.getItem(0), 0, players.get(P2_NAME), players.get(P2_NAME), 0, true, 0);
        assertLog(log.getItem(1), 12, players.get(P1_NAME), players.get(P2_NAME), 0, true, 0);
    }

    @Test
    public void testBasicActionsWithElemental() throws Exception {
        int missingHP = 10;
        Elemental waterElemental = new Elemental(WATER_ELEMENTAL_NAME, Element.WATER);
        players.get(P1_NAME).addElemental(waterElemental);
        players.get(P1_NAME).decreaseHP(missingHP);

        BattleLog log = engine.processTurn(new Actions(new HealAction(players.get(P1_NAME).getElemental(WATER_ELEMENTAL_NAME), players.get(P1_NAME)))).getBattleLog();

        int expectedHP = (int) min(P1_HP, P1_HP - missingHP + CoefficientGateway.getAbilityValue().ofHealingMultiplier() * P1_HP);
        assertEquals(expectedHP, players.get(P1_NAME).getCurrentHP());
        assertEquals(1,log.size());
        assertLog(log.getItem(0), 30, waterElemental, players.get(P1_NAME), 0, true, 0);
    }

    @Test
    public void testBoostAttackFor3Turns() throws Exception {
        Elemental fireElemental = new Elemental(FIRE_ELEMENTAL_NAME, Element.FIRE);
        players.get(P1_NAME).addElemental(fireElemental);

        EngineOutput engineOutput = engine.processTurn(new Actions(new BoostAttackAction(fireElemental, players.get(P1_NAME))));
        assertEquals(26,players.get(P1_NAME).getAttributes().getAttack());
        assertLog(engineOutput.getBattleLog().getItem(0), 6, fireElemental, players.get(P1_NAME), 0, true, 2);

        engineOutput = engine.processTurn(engineOutput.getActions());
        assertEquals(26, players.get(P1_NAME).getAttributes().getAttack());
        assertLog(engineOutput.getBattleLog().getItem(0), 6, fireElemental, players.get(P1_NAME), 0, true, 1);

        engineOutput = engine.processTurn(engineOutput.getActions());
        assertEquals(20, players.get(P1_NAME).getAttributes().getAttack());
        assertLog(engineOutput.getBattleLog().getItem(0), 6, fireElemental, players.get(P1_NAME), 0, true, 0);
    }

    private void assertLog(LogItem logItem, int value, BattleEntity owner, BattleEntity target, int code, boolean success, int duration) {
        assertEquals(value, logItem.getValue());
        assertEquals(owner, logItem.getOwner());
        assertEquals(target, logItem.getTarget());
        assertEquals(code, logItem.getInfoCode());
        assertEquals(success, logItem.isSuccess());
        assertEquals(duration, logItem.getDuration());
    }
}
