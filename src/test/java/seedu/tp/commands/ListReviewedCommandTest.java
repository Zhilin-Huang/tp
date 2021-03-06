package seedu.tp.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.tp.flashcard.EventFlashcard;
import seedu.tp.flashcard.Flashcard;
import seedu.tp.flashcard.FlashcardList;
import seedu.tp.flashcard.OtherFlashcard;
import seedu.tp.flashcard.PersonFlashcard;
import seedu.tp.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListReviewedCommandTest {

    public static final EventFlashcard EVENT_FLASHCARD = new EventFlashcard(
        "Event 1",
        LocalDate.of(1843, 7, 31),
        LocalDate.of(1892, 12, 25),
        "This is an event summary",
        Arrays.asList("Detail 1", "Detail 2")
    );
    public static final PersonFlashcard PERSON_FLASHCARD = new PersonFlashcard(
        "Person 1",
        LocalDate.of(1843, 7, 31),
        LocalDate.of(1892, 12, 25),
        "This is a person's summary",
        Arrays.asList("Detail 1", "Detail 2")
    );
    public static final OtherFlashcard OTHER_FLASHCARD = new OtherFlashcard(
        "Title 1",
        "This is a summary",
        Arrays.asList("Detail 1", "Detail 2")
    );
    private final ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();
    private final PrintStream backupStdout = System.out;
    private FlashcardList emptyFlashcardList;
    private FlashcardList fullFlashcardList;

    /**
     * Set up variables before each test.
     */
    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(capturedOut));
        emptyFlashcardList = new FlashcardList();
        EVENT_FLASHCARD.setReviewStatus(true);
        OTHER_FLASHCARD.setReviewStatus(true);
        List<Flashcard> flashcards = Arrays.asList(EVENT_FLASHCARD, PERSON_FLASHCARD, OTHER_FLASHCARD);
        fullFlashcardList = new FlashcardList(flashcards);
    }

    /**
     * Restores standard output and variables.
     */
    @AfterEach
    public void restoreStdoutAndVariables() {
        System.setOut(backupStdout);
        EVENT_FLASHCARD.setReviewStatus(false);
        OTHER_FLASHCARD.setReviewStatus(false);
    }

    @Test
    public void listReviewedCommand_execute_listsFlashcardsSuccessfully() {
        StringBuilder expectedOutput = new StringBuilder();
        expectedOutput.append("Here's the list of flashcards you are looking for:" + System.lineSeparator());
        expectedOutput.append("1: Event 1 | Reviewed: / | Not indicated | ID: 1" + System.lineSeparator());
        expectedOutput.append("2: Title 1 | Reviewed: / | Not indicated | ID: 3" + System.lineSeparator());

        ListReviewedCommand listReviewedCommand = new ListReviewedCommand(fullFlashcardList, new Ui());
        listReviewedCommand.execute();
        assertEquals(expectedOutput.toString(), capturedOut.toString());
    }

    @Test
    public void listReviewedCommand_executeEmptyList_listsFlashcardsSuccessfully() {
        String expectedOutput = "You have no flashcard matching your query!" + System.lineSeparator();

        ListReviewedCommand listReviewedCommand = new ListReviewedCommand(emptyFlashcardList, new Ui());
        listReviewedCommand.execute();
        assertEquals(expectedOutput, capturedOut.toString());
    }
}
