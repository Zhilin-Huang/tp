package seedu.tp.ui;

import seedu.tp.commands.CommandFeedback;
import seedu.tp.exceptions.InvalidDateFormatException;
import seedu.tp.flashcard.EventFlashcard;
import seedu.tp.flashcard.Flashcard;
import seedu.tp.flashcard.PersonFlashcard;
import seedu.tp.group.FlashcardGroup;
import seedu.tp.parser.Parser;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static seedu.tp.utils.Constants.BULLET_POINT;
import static seedu.tp.utils.Constants.DETAIL_FIELD;
import static seedu.tp.utils.Constants.EMPTY_STRING;
import static seedu.tp.utils.Constants.LOG_FOLDER;

/**
 * Ui class.
 */
public class Ui {

    private static final String FILE_PATH = LOG_FOLDER + "ui.log";
    private static final Logger LOGGER = Logger.getLogger(Ui.class.getName());

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Set up the Ui logger. Call once at the start of the program.
     *
     * @throws IOException when logger set up failed
     */
    public static void setupLogger() throws IOException {
        LOGGER.setLevel(Level.ALL);
        LOGGER.setUseParentHandlers(false);
        FileHandler fileHandler = new FileHandler(FILE_PATH, true);
        fileHandler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(fileHandler);
    }

    /**
     * Sends welcome message to user.
     */
    public void sendWelcomeMessage() {
        System.out.println("Welcome to History Flashcard App!");
    }

    /**
     * Sends bye message to user.
     */
    public void sendByeMessage() {
        System.out.println("Thanks for using History Flashcard!");
    }

    /**
     * Prompts the user for a list of details for a flashcard.
     *
     * @return the list of details entered by user
     */
    public List<String> promptUserForDetails() {
        LOGGER.info("Prompting user for details...");
        List<String> details = new ArrayList<>();
        Optional<String> newDetailOptional = promptUserForOptionalField(DETAIL_FIELD);
        while (newDetailOptional.isPresent()) {
            details.add(newDetailOptional.get());
            newDetailOptional = promptUserForOptionalField(DETAIL_FIELD);
        }
        LOGGER.info("Returning details...");
        return details;
    }

    /**
     * Prompts the user for a piece of optional data used in the construction of a <code>Flashcard</code>.
     * The user can leave the line empty.
     *
     * @param fieldName string representing the name of the data to prompt for
     * @return the user's input
     */
    public Optional<String> promptUserForOptionalField(String fieldName) {
        assert !fieldName.isEmpty() : "Invalid empty field name!";
        LOGGER.info("Prompting user for optional field " + fieldName + "...");

        System.out.println("Please enter " + fieldName + " (optional):");
        String input = getNextLine().trim();

        LOGGER.info("Returning optional field " + fieldName + "...");
        return input.equals(EMPTY_STRING) ? Optional.empty() : Optional.of(input);
    }

    /**
     * Prompts the user for a piece of required data used in the construction of a <code>Flashcard</code>.
     *
     * @param fieldName string representing the name of the data to prompt for
     * @return the user's input
     */
    public String promptUserForRequiredField(String fieldName) {
        assert !fieldName.isEmpty() : "Invalid empty field name!";
        LOGGER.info("Prompting user for required field " + fieldName + "...");

        System.out.println("Please enter " + fieldName + ":");
        String input = getNextLine().trim();
        while (input.equals(EMPTY_STRING)) {
            System.out.println("That is a required field! Please enter again.");
            input = getNextLine().trim();
        }

        LOGGER.info("Returning required field " + fieldName + "...");
        return input;
    }

    /**
     * Prompts the user for a required date used in the construction of a <code>Flashcard</code>.
     *
     * @param fieldName string representing name of the date to prompt for
     * @return the parsed date
     */
    public LocalDate promptUserForRequiredLocalDate(String fieldName) {
        assert !fieldName.isEmpty() : "Invalid empty field name!";
        LOGGER.info("Prompting user for required local date field " + fieldName + "...");

        System.out.println("Please enter " + fieldName + ":");
        String input;
        LocalDate localDate = null;

        do {
            input = getNextLine().trim();
            try {
                localDate = Parser.parseDate(input);
            } catch (InvalidDateFormatException e) {
                System.out.println("That date format couldn't be parsed! Please enter again.");
            }
        } while (localDate == null);

        LOGGER.info("Returning required local date field " + fieldName + "...");
        return localDate;
    }

    /**
     * Gets the response from the user after the user reviewed a flashcard.
     *
     * @param flashcard the flashcard the user just reviewed
     * @return the response given by the user
     */
    public String promptUserResponseForReviewing(Flashcard flashcard) {
        System.out.println("Do you want to mark this flashcard as reviewed?");
        String input = getNextLine().trim().toLowerCase();
        System.out.println("");
        return input;
    }

    /**
     * Sends confirmation message for completion o random flashcard review.
     *
     * @param reviewedNumber        the number of flashcards reviewed during this self test
     * @param totalUnreviewedNumber the total number of unreviewed flashcards
     */
    public void confirmRandomFlashcardsReviewCompletion(int reviewedNumber, int totalUnreviewedNumber) {
        System.out.println("You have just gone through all the flashcard(s).");
        System.out.println("You have marked " + reviewedNumber + " flashcard(s) as reviewed this time.");
        System.out.println("You still have " + totalUnreviewedNumber + " unreviewed flashcard(s).");
        System.out.println("");
    }

    /**
     * Sends flashcard creation confirmation to user.
     *
     * @param flashcard the flashcard created
     */
    public void confirmFlashcardCreation(Flashcard flashcard) {
        assert flashcard != null : "Invalid null flashcard!";

        System.out.println("You've successfully created the flashcard below:");
        System.out.println(flashcard);
    }

    /**
     * Prints confirmation that flashcard has been marked as reviewed.
     *
     * @param flashcard the flashcard that was reviewed
     */
    public void confirmFlashcardReview(Flashcard flashcard) {
        assert flashcard != null : "Invalid null flashcard!";

        System.out.println("You have marked the following flashcard as Reviewed:");
        System.out.println(flashcard.getName());
    }

    /**
     * Displays flashcard details according to index specified.
     *
     * @param flashcard the flashcard to be displayed
     */
    public void showFlashcard(Flashcard flashcard) {
        assert flashcard != null : "Invalid null flashcard!";

        System.out.println("These are the flashcard details:");
        System.out.println(flashcard);
    }

    /**
     * Sends flashcard group creation confirmation to user.
     *
     * @param flashcardGroup the flashcard group created
     */
    public void confirmFlashcardGroupCreation(FlashcardGroup flashcardGroup) {
        assert flashcardGroup != null : "Invalid null flashcard group!";

        System.out.println("You've successfully created the group below:");
        System.out.println(flashcardGroup);
    }

    /**
     * Sends reset completion confirmation message to user.
     */
    public void confirmResetCompletion() {
        System.out.println("All the flashcards have been reset as unreviewed successfully.");
    }

    /**
     * Lists flashcards from a specified time period only, in sorted order.
     *
     * @param sortedFlashcards the sorted list of all flashcards
     * @param startDate        the date to start listing flashcards from (inclusive)
     * @param endDate          the date after which to stop listing flashcards from
     */
    private void listFlashcardsInPeriod(List<Flashcard> sortedFlashcards, LocalDate startDate, LocalDate endDate) {
        System.out.println("Listing flashcards from " + startDate + " to " + endDate + "...");
        boolean noFlashcards = true;
        for (Flashcard f : sortedFlashcards) {
            if (f instanceof EventFlashcard) {
                EventFlashcard eventFlashcard = (EventFlashcard) f;
                LocalDate eventStartDate = eventFlashcard.getStartDate();
                if (eventStartDate.compareTo(startDate) >= 0
                    && eventStartDate.compareTo(endDate) <= 0) {
                    System.out.println(BULLET_POINT + f.getShortDescription());
                    noFlashcards = false;
                }
            } else if (f instanceof PersonFlashcard) {
                PersonFlashcard personFlashcard = (PersonFlashcard) f;
                LocalDate personBirthDate = personFlashcard.getBirthDate();
                if (personBirthDate.compareTo(startDate) >= 0
                    && personBirthDate.compareTo(endDate) <= 0) {
                    System.out.println(BULLET_POINT + f.getShortDescription());
                    noFlashcards = false;
                }
            }
        }
        if (noFlashcards) {
            System.out.println("No flashcards found in this period.");
        }
    }

    /**
     * Prints confirmation that study plan has been updated.
     */
    public void confirmStudyPlanUpdate() {
        System.out.println("Your study plan has been updated.");
    }

    /**
     * Prints out exception to UI.
     *
     * @param exception the exception to be printed out
     */
    public void printException(Exception exception) {
        LOGGER.warning("Sending exception to user...");
        LOGGER.warning(exception.toString());
        System.out.println("An exception has occurred!");
        System.out.println(exception.getMessage());
    }

    /**
     * Sends response to unknown command entered by user.
     */
    public void sendUnknownCommandResponse() {
        LOGGER.info("Sending unknown command response to user...");
        System.out.println("Sorry, I don't know how to help with that yet.");
    }

    /**
     * Sends response to invalid flashcard index entered by user.
     */
    public void sendInvalidFlashcardIndexResponse() {
        LOGGER.info("Sending invalid flashcard index response to user...");
        System.out.println("Your input contains invalid flashcard index(es)");
    }

    public void sendLoggingSetupFailedMessage() {
        LOGGER.info("Sending logging set up failed response to user...");
        System.out.println("Logging setup failed! Logs will be printed to console instead of saved to file.");
    }

    public void sendInvalidInputFormatResponse() {
        LOGGER.info("Sending invalid input format response to user...");
        System.out.println("Please use the correct input format. Use \"help\" to view all commands.");
    }

    public void sendDuplicateFlashcardResponse() {
        LOGGER.info("Send duplicate flashcard response to user...");
        System.out.println("Duplicate flashcard detected. The flashcard has not been added.");
    }

    /**
     * Display the feedback of a command to the user.
     *
     * @param commandFeedback the commandFeedback to display.
     */
    public void showCommandFeedback(CommandFeedback commandFeedback) {
        if (!commandFeedback.isEmpty()) {
            System.out.println(commandFeedback);
        }
    }

    public void sendInvalidDateFormatResponse() {
        System.out.println("That date format couldn't be parsed!");
    }

    public void sendUiLineBreak() {
        LOGGER.info("Sending Ui line break to user...");
        System.out.println("--------------------------------------------");
    }

    /**
     * Gets the next user input line.
     *
     * @return next line
     */
    public String getNextLine() {
        return scanner.nextLine();
    }
}
