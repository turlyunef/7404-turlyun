package model.game;

import model.game.field.Field;
import model.game.field.GameField;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static junit.framework.TestCase.assertEquals;

public class ModelTest {
    private static final Logger log = LoggerFactory.getLogger(ModelTest.class);

    @Test
    public void testBombsGeneration1() {
            testBombsGeneration(10, 9, 9);
    }

    @Test
    public void testBombsGeneration2() {

        Random random = new Random();
        for (int i = 0; i < 5000; i++) {
            int randomBombsCount = random.nextInt(50);
            int randomRowsCount = random.nextInt(20);
            int randomColumnsCount = random.nextInt(20);
            if (randomBombsCount > randomRowsCount * randomColumnsCount) {
                log.debug("randomBombsCount > randomRowsCount * randomColumnsCount");
                continue;
            }
            log.debug(String.format("Test bombs generation with random parameters. " +
                            "randomBombsCount = %s, randomRowsCount = %s, randomColumnsCount = %s ",
                    randomBombsCount, randomRowsCount, randomColumnsCount));

            testBombsGeneration(randomBombsCount, randomRowsCount, randomColumnsCount);
        }
    }

    private void testBombsGeneration(int bombsCount, int rowsCount, int columnsCount) {
        GameProperties gameProperties = new GameProperties();
        gameProperties.setProperties(bombsCount, rowsCount, columnsCount, "Test mode");
        Field field = new GameField(gameProperties);
        int bombsCounter = 0;
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                if (field.getCell(i, j).isBomb()) {
                    bombsCounter++;
                }
            }
        }
        assertEquals(bombsCount, bombsCounter);
    }
}