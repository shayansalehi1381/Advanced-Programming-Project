package Controller;

import Factory.WaveConfiguration;
import Factory.WaveEnemyFactory;
import Model.SquareEnemy;
import Model.TrigorathEnemy;
import View.GameFrame;
import View.GamePanel;

import java.util.Map;

public class Wave {

    public static int GameDifficulty = 1;
    private static final Map<Integer, WaveConfiguration> difficultyConfigurations = Map.of(
            1, new WaveConfiguration(1, 2),
            2, new WaveConfiguration(3, 2),
            3, new WaveConfiguration(4, 2)
    );

    private WaveEnemyFactory enemyFactory;

    public Wave(GameFrame frame) {

        int waveNumber = 1; // Replace with actual logic to get wave number

        if (difficultyConfigurations.containsKey(GameDifficulty)) {
            WaveConfiguration configuration = difficultyConfigurations.get(GameDifficulty);
            configureEnemies(configuration, frame);
        }
    }

    private void configureEnemies(WaveConfiguration configuration, GameFrame frame) {
        enemyFactory = new WaveEnemyFactory(frame);

        if (GamePanel.Wave == 1) {
            enemyFactory.createTrigorathEnemies(configuration.getTriangles());
            enemyFactory.createSquareEnemies(configuration.getSquares());
        } else if (GamePanel.Wave == 2) {
            enemyFactory.createTrigorathEnemies(configuration.getTriangles() + 1);
            enemyFactory.createSquareEnemies(configuration.getSquares() + 2);
        } else if (GamePanel.Wave == 3) {
            enemyFactory.createTrigorathEnemies(configuration.getTriangles() + 2);
            enemyFactory.createSquareEnemies(configuration.getSquares() + 3);
        }
    }
}

