//package model.game;
//
//import model.game.field.inside.InsideValueGameModel;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Random;
//
//import static junit.framework.TestCase.assertEquals;
//
//public class InsideValueGameMainModelTest {
//    private static Logger log = LoggerFactory.getLogger(InsideValueGameMainModelTest.class);
//
//    @Test
//    public void testBombsGeneration1() {
//        try {
//            GameProperties gameProperties = new GameProperties();
//            testBombsGeneration(gameProperties);
//        } catch (TableGenerationException e) {
//            log.warn("Test bombs generation conflict, " + e.getMessage());
//        }
//    }
//
//    @Test
//    public void testBombsGeneration2() {
//
//        Random random = new Random();
//        try {
//            for (int i = 0; i < 5000; i++) {
//                int randomBombsCount = random.nextInt(50);
//                int randomRowsCount = random.nextInt(20);
//                int randomColumnsCount = random.nextInt(20);
//                if (randomBombsCount > randomRowsCount * randomColumnsCount) {
//                    log.debug("randomBombsCount > randomRowsCount * randomColumnsCount");
//                    continue;
//                }
//                log.debug(String.format("Test bombs generation with random parameters. " +
//                                "randomBombsCount = %s, randomRowsCount = %s, randomColumnsCount = %s ",
//                        randomBombsCount, randomRowsCount, randomColumnsCount));
//
//                GameProperties gameProperties = new GameProperties(randomBombsCount, randomRowsCount, randomColumnsCount);
//                testBombsGeneration(randomBombsCount, randomRowsCount, randomColumnsCount);
//            }
//        } catch (TableGenerationException e) {
//            log.warn("Test bombs generation conflict, " + e.getMessage());
//        }
//    }
//
//    private void testBombsGeneration(GameProperties gameProperties) throws TableGenerationException {
//        InsideValueGameModel table = new InsideValueGameModel(gameProperties);
//        int bombsCounter = 0;
//        for (int i = 0; i < rowsCount; i++) {
//            for (int j = 0; j < columnsCount; j++) {
//                if (table.cellIsBomb(i, j)) {
//                    bombsCounter++;
//                }
//            }
//        }
//        assertEquals(bombsCount, bombsCounter);
//    }
//}