package Factory;

import Model.SquareEnemy;
import Model.TrigorathEnemy;
import View.GameFrame;

public class WaveEnemyFactory {
    private GameFrame gameFrame;

    public WaveEnemyFactory(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public void createTrigorathEnemies(int count) {
        for (int i = 0; i < count; i++) {
            TrigorathEnemy trigorathEnemy = new TrigorathEnemy(gameFrame);
        }
    }

    public void createSquareEnemies(int count) {
        for (int i = 0; i < count; i++) {
            SquareEnemy squareEnemy = new SquareEnemy(gameFrame);
        }
    }
}
