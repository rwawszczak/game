package battleengine;

import battleengine.action.Actions;
import battleengine.action.elemental.BoostAttackAction;
import battleengine.action.player.AttackAction;
import battleengine.action.player.DefendAction;
import battleengine.action.elemental.HealAction;
import battleengine.coefficient.CoefficientGateway;
import battleengine.player.Attributes;
import battleengine.player.Player;
import battleengine.player.Players;
import battleengine.player.elemental.Elemental;
import battleengine.player.elemental.ElementalType;
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

    @Test ///TODO: dodać log i jego sprawdzanie
    public void testBasicAttackAction() throws Exception {
        Actions actions = new Actions(new AttackAction(players.get(P1_NAME), players.get(P2_NAME)));

        engine.processTurn(actions);

        assertEquals(87, players.get(P2_NAME).getCurrentHP());
    }

    @Test
    public void testCriticalHitAttack() throws Exception {
        players.get(P1_NAME).getAttributes().increaseLuck(1);
        Actions actions = new Actions(new AttackAction(players.get(P1_NAME), players.get(P2_NAME)));

        engine.processTurn(actions);

        assertEquals(81, players.get(P2_NAME).getCurrentHP());
    }

    @Test
    public void testMissedAttack() throws Exception {
        Actions actions = new Actions(new AttackAction(players.get(P2_NAME), players.get(P1_NAME)));

        engine.processTurn(actions);

        assertEquals(P1_HP, players.get(P1_NAME).getCurrentHP());
    }

    @Test
    public void testBasicAttackWithDefend() throws Exception {
        Actions actions = new Actions();
        actions.add(new AttackAction(players.get(P1_NAME), players.get(P2_NAME)));
        actions.add(new DefendAction(players.get(P2_NAME)));

        engine.processTurn(actions);

        assertEquals(88, players.get(P2_NAME).getCurrentHP());
        assertEquals(P2_DEFENCE, players.get(P2_NAME).getAttributes().getDefence());
    }

    @Test
    public void testBasicActionsWithElemental() throws Exception {
        int missingHP = 10;
        Elemental waterElemental = new Elemental(WATER_ELEMENTAL_NAME, ElementalType.WATER);
        players.get(P1_NAME).addElementals(waterElemental);
        players.get(P1_NAME).decreaseHP(missingHP);

        engine.processTurn(new Actions(new HealAction(players.get(P1_NAME).getElemental(WATER_ELEMENTAL_NAME), players.get(P1_NAME))));

        int expectedHP = (int) min(P1_HP, P1_HP - missingHP + CoefficientGateway.getAbilityValue().ofHealingMultiplier() * P1_HP);
        assertEquals(expectedHP, players.get(P1_NAME).getCurrentHP());
    }

    @Test
    public void testBoostAttackFor3Turns() throws Exception {
        Elemental fireElemental = new Elemental(FIRE_ELEMENTAL_NAME, ElementalType.FIRE);
        players.get(P1_NAME).addElementals(fireElemental);

        Actions pushedActions = engine.processTurn(new Actions(new BoostAttackAction(players.get(P1_NAME))));
        assertEquals(26,players.get(P1_NAME).getAttributes().getAttack());
        pushedActions = engine.processTurn(pushedActions);
        assertEquals(26,players.get(P1_NAME).getAttributes().getAttack());
        engine.processTurn(pushedActions);
        assertEquals(20,players.get(P1_NAME).getAttributes().getAttack());
    }
}
