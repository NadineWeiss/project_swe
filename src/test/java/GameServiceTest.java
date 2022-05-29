import jdk.jfr.Unsigned;
import org.dhbw.swe.board.BoardFourMock;
import org.dhbw.swe.game.Context;
import org.dhbw.swe.game.GameParameterMock;
import org.dhbw.swe.game.GameService;
import org.dhbw.swe.game.ObserverContext;
import org.dhbw.swe.visualization.GameFrameMock;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class GameServiceTest {

    private GameService gameService;
    private GameFrameMock gameVisualization;
    private GameParameterMock gameParameter;

    @BeforeEach
    public void beforeEachTest() {

        BoardFourMock board = new BoardFourMock();
        board.initBoard(4);

        gameVisualization = new GameFrameMock();
        gameParameter = new GameParameterMock(board);
        gameService = new GameService(gameVisualization, gameParameter);

    }

    @Test
    public void calculateMovesTest1(){

        Assertions.assertThrows(NullPointerException.class, () -> {

            gameParameter.setTurn(Color.GREEN);
            gameVisualization.notifyObservers(new ObserverContext(Context.CALCULATE, Optional.of(32)));

        });

    }

    @Ignore
    @Test
    public void calculateMovesTest2(){

        gameParameter.setTurn(Color.GREEN);
        gameVisualization.notifyObservers(new ObserverContext(Context.DICE));
        int dicedValue = gameVisualization.getDiceValue();
        gameVisualization.notifyObservers(new ObserverContext(Context.CALCULATE, Optional.of(43)));

        assertEquals(43 + dicedValue, gameVisualization.getMarkedAdditionalField());

    }

    @Test
    public void diceTest(){

        gameParameter.setTurn(Color.GREEN);
        gameVisualization.notifyObservers(new ObserverContext(Context.DICE));
        int dicedValue = gameVisualization.getDiceValue();

        assertTrue(1 <= dicedValue && dicedValue <= 6);

    }


}
