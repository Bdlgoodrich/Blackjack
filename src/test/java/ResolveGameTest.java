import Objects.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.Assert.assertTrue;

public class ResolveGameTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    Deck deck = new Deck();
    Player testPlayer = new Player(deck);
    Dealer testDealer = new Dealer(deck);
    Hand testPlayerHand = testPlayer.getHand();
    Hand testDealerHand = testDealer.getHand();
    Field score = Hand.class.getDeclaredField("currentScore");
    Field isBlackJack = Hand.class.getDeclaredField("isBlackjack");

    public ResolveGameTest() throws NoSuchFieldException {
    }

    @Before
    public void Setup() {
        System.setOut(new PrintStream(outputStream));
        score.setAccessible(true);
        isBlackJack.setAccessible(true);
    }

    @After
    public void TearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void P20D19ShouldWin() throws IllegalAccessException {
        score.set(testPlayerHand, 20);
        score.set(testDealerHand, 19);
        testPlayer.resolveHand(testDealer);
        String expectedOutput = "You won";
        assertTrue(outputStream.toString().contains(expectedOutput));
    }

    @Test
    public void P19D20ShouldLose() throws IllegalAccessException {
        score.set(testPlayerHand, 19);
        score.set(testDealerHand, 20);
        testPlayer.resolveHand(testDealer);
        String expectedOutput = "You lost";
        assertTrue(outputStream.toString().contains(expectedOutput));
    }

    @Test
    public void P20D20ShouldPush() throws IllegalAccessException {
        score.set(testPlayerHand, 20);
        score.set(testDealerHand, 20);
        testPlayer.resolveHand(testDealer);
        String expectedOutput = "You push";
        assertTrue(outputStream.toString().contains(expectedOutput));
    }

    @Test
    public void PlayerBlackjackShouldWin() throws IllegalAccessException {
        isBlackJack.set(testPlayerHand, true);
        testPlayer.resolveHand(testDealer);
        String expectedOutput1 = "You have Blackjack!";
        String expectedOutput2 = "You won";
        assertTrue(outputStream.toString().contains(expectedOutput1));
        assertTrue(outputStream.toString().contains(expectedOutput2));
    }

    @Test
    public void DealerBlackjackShouldLose() throws IllegalAccessException {
        InputStream originalSystemIn = System.in;
        isBlackJack.set(testDealerHand, true);
        ByteArrayInputStream in = new ByteArrayInputStream("\n".getBytes());
        System.setIn(in);
        testPlayer.resolveHand(testDealer);
        String expectedOutput1 = "Dealer has Blackjack";
        String expectedOutput2 = "You lost";
        assertTrue(outputStream.toString().contains(expectedOutput1));
        assertTrue(outputStream.toString().contains(expectedOutput2));
        System.setIn(originalSystemIn);
    }
}


   /*

    import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleInputTest {

    private InputStream originalSystemIn;

    @BeforeEach
    void setup() {
        originalSystemIn = System.in; // Backup original System.in
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn); // Restore original System.in
    }

    @Test
    void testReadFromConsole() {
        String simulatedInput = "Hello World\n";
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in); // Redirect System.in

        // Code under test that reads from System.in
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close(); // Close the scanner to release resources

        assertEquals("Hello World", input);
    }
}
     */