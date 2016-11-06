package game.server;

import game.model.domain.Battle;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class BattleInspector extends Thread {
    private static long MAX_PENDING_TIME = 20000;
    private static long INSPECT_TIME_INTERVAL = 30000;
    private boolean running = true;

    @Override
    public void run() {
        while (running) {
            try {
                System.out.println("INSPECTOR: Current battle count: " + ServerData.getBattles().size());
                List<Battle> battles = new ArrayList<>(ServerData.getBattles().values());
                for (int i = 0; i < battles.size(); i++) {
                    checkPending(battles.get(i));
                    checkDeclined(battles.get(i));
                }

                sleep();
            } catch (ConcurrentModificationException e){
                System.out.println("INSPECTOR: Concurrent modification exception :(");
                e.printStackTrace();
            }
        }
    }

    private void checkDeclined(Battle battle) {
        long count = battle.statusCount(Battle.Status.DECLINED);
        if (count > 0) {
            System.out.println("INSPECTOR: " + count + " players have declined battle nr " + battle.getId() + ", battle will be removed.");
            ServerData.removeBattle(battle);
        }
    }


    private void checkPending(Battle battle) {
        if (battle.statusCount(Battle.Status.PENDING) > 0 && exceededPendingTime(battle)) {
            System.out.println("INSPECTOR: Battle nr " + battle.getId() + " has pending users for too long, battle will be removed.");
            ServerData.removeBattle(battle);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(INSPECT_TIME_INTERVAL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopGracefully() {
        running = true;
    }

    private boolean exceededPendingTime(Battle battle) {
        return System.currentTimeMillis() - battle.getTimeCreated() > MAX_PENDING_TIME;
    }
}
