package seedu.tp.commands;

import seedu.tp.exceptions.HistoryFlashcardException;
import seedu.tp.flashcard.FlashcardList;
import seedu.tp.ui.Ui;

public class RandomCommand extends Command {
    Ui ui;
    FlashcardList flashcardList;

    public RandomCommand(FlashcardList flashcardList, Ui ui) {
        this.flashcardList = flashcardList;
        this.ui = ui;
    }

    @Override
    public void execute() throws HistoryFlashcardException {
        LOGGER.info("Randomizing flashcards for reviewing ...");
        flashcardList.reviewRandomFlashcards(ui);
        LOGGER.info("Randomized flashcards and finished a review");
    }
}
