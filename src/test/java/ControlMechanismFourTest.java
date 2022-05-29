import org.dhbw.swe.board.BoardFourMock;
import org.dhbw.swe.board.BoardInterface;
import org.dhbw.swe.board.ControlMechanismFour;
import org.dhbw.swe.board.GamePieceInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ControlMechanismFourTest {

    private BoardInterface board;
    private ControlMechanismFour controlMechanismFour;

    @BeforeEach
    public void beforeEachTest() {

        controlMechanismFour = new ControlMechanismFour();
        board = new BoardFourMock(controlMechanismFour);
        board.initBoard(4);

    }

    @Test
    public void checkWinTest(){

        Color winColor = controlMechanismFour.checkWin(board.getBoard());
        assertEquals(Color.BLUE, winColor);

    }

    @Test
    public void isAllowedToRediceTest1(){

        boolean isAllowedToRedice = controlMechanismFour.isAllowedToRedice(Color.RED, board.getBoard());

        assertTrue(isAllowedToRedice);

    }

    @Test
    public void calculateMoveTest1(){

        Optional<Integer> moveTo = controlMechanismFour.calculateMove(33, board.getBoard(), 5);

        assertEquals(65, moveTo.get());

    }

    @Test
    public void calculateMoveTest2(){

        Assertions.assertThrows(NullPointerException.class, () -> {

            Optional<Integer> moveTo = controlMechanismFour.calculateMove(34, board.getBoard(), 5);

        });

    }

    @Test
    public void isMovePossibleTest(){

        boolean isMovePossible = controlMechanismFour.isMovePossible(Color.YELLOW, board.getBoard(), 3);

        assertFalse(isMovePossible);

    }

    @Test
    public void isAllowedToRediceTest2(){

        boolean isAllowedToRedice = controlMechanismFour.isAllowedToRedice(Color.GREEN, board.getBoard());

        assertFalse(isAllowedToRedice);

    }

    @Test
    public void calculateMovesTest(){

        Map<GamePieceInterface, Integer> moves = controlMechanismFour.calculateMoves(Color.GREEN, board.getBoard(), 1);

        assertEquals(2, moves.size());
        assertTrue(moves.values().contains(34));
        assertTrue(moves.values().contains(44));

    }

}