package seedu.tp.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.tp.exceptions.UnrecognizedFlashcardTypeException;
import seedu.tp.flashcard.EventFlashcard;
import seedu.tp.flashcard.FlashcardList;
import seedu.tp.flashcard.OtherFlashcard;
import seedu.tp.flashcard.PersonFlashcard;
import seedu.tp.ui.Ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tp.utils.ExampleInputConstants.BULLET_POINT;
import static seedu.tp.utils.ExampleInputConstants.DETAILS;
import static seedu.tp.utils.ExampleInputConstants.SUMMARY;

public class TimelineCommandTest {
    private final ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();
    private final PrintStream backupStdout = System.out;

    @BeforeEach
    public void captureStdout() {
        System.setOut(new PrintStream(capturedOut));
    }

    @AfterEach
    public void restoreStdout() {
        System.setOut(backupStdout);
    }

    @Test
    public void timelineCommand_execute_listsFlashcardsSuccessfully() throws UnrecognizedFlashcardTypeException {
        FlashcardList flashcardList = new FlashcardList();
        LocalDate firstDate = LocalDate.of(1834, 2, 1);
        LocalDate middleDate = LocalDate.of(1834, 7, 3);
        LocalDate lastDate = LocalDate.of(1921, 7, 3);

        flashcardList.addFlashcard(new EventFlashcard("Middle", middleDate, middleDate, SUMMARY, DETAILS));
        flashcardList.addFlashcard(new OtherFlashcard("Bottom", SUMMARY, DETAILS));
        flashcardList.addFlashcard(new EventFlashcard("Last", lastDate, lastDate, SUMMARY, DETAILS));
        flashcardList.addFlashcard(new PersonFlashcard("First", firstDate, firstDate, SUMMARY, DETAILS));

        StringBuilder expectedOutput = new StringBuilder();
        expectedOutput.append("Flashcards sorted by date:" + System.lineSeparator());
        expectedOutput.append(BULLET_POINT
                + flashcardList.getFlashcardAtIdx(3).getShortDescription() + System.lineSeparator());
        expectedOutput.append(BULLET_POINT
                + flashcardList.getFlashcardAtIdx(0).getShortDescription() + System.lineSeparator());
        expectedOutput.append(BULLET_POINT
                + flashcardList.getFlashcardAtIdx(2).getShortDescription() + System.lineSeparator());
        expectedOutput.append(BULLET_POINT
                + flashcardList.getFlashcardAtIdx(1).getShortDescription() + System.lineSeparator());

        TimelineCommand timelineCommand = new TimelineCommand(flashcardList, new Ui());
        timelineCommand.execute();
        assertEquals(expectedOutput.toString(), capturedOut.toString());
    }
}
