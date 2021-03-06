package seedu.tp.flashcard;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static seedu.tp.utils.Constants.EMPTY_STRING;
import static seedu.tp.utils.Constants.LOG_FOLDER;

/**
 * Abstract flashcard class to represent basic properties of flashcard.
 */
public abstract class Flashcard implements Comparable<Flashcard> {
    protected static final String FILE_PATH = LOG_FOLDER + "flashcard.log";
    protected static final Logger LOGGER = Logger.getLogger(Flashcard.class.getName());

    protected String name;
    protected String summary;
    protected List<String> details;
    protected boolean isReviewed;
    protected PriorityLevel pl;

    protected Flashcard(String name, String summary, List<String> details) {
        this.name = name;
        this.summary = summary;
        this.details = details;
        this.isReviewed = false;
        this.pl = PriorityLevel.DEFAULT;
    }

    /**
     * Set up the flashcard logger. Call once at the start of the program.
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

    protected static String getDetailsString(List<String> details) {
        StringBuilder detailsStringBuilder = new StringBuilder();
        for (String detail : details) {
            detailsStringBuilder.append("* ").append(detail).append(System.lineSeparator());
        }
        return detailsStringBuilder.toString();
    }

    /**
     * Gets the name of the flashcard.
     *
     * @return the name of the flashcard
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the review status of the flashcard.
     *
     * @param isReviewed true if flashcard has been reviewed
     */
    public void setReviewStatus(boolean isReviewed) {
        this.isReviewed = isReviewed;
    }

    /**
     * Returns icon based on flashcard's review status.
     *
     * @return "Y" for Yes if reviewed, else "N" for No.
     */
    public String getReviewIcon() {
        return (isReviewed ? "/" : "X");
    }


    /**
     * Returns boolean value indicating whether or not the flashcards has been reviewed.
     *
     * @return true if flashcard is reviewed, else false
     */
    public boolean isReviewed() {
        return isReviewed;
    }

    /**
     * Sets the flashcard's priority level.
     *
     * @param pl priority level to be set
     */
    public void setPriorityLevel(PriorityLevel pl) {
        this.pl = pl;
    }

    /**
     * Returns the number of "*"s based on flashcard's priority level.
     *
     * @return "*"s to indicate priority level
     */
    public String getPriorityAsString() {
        switch (pl) {
        case LOW:
            return "*";
        case MEDIUM:
            return "**";
        case HIGH:
            return "***";
        default:
            return "Not indicated";
        }
    }

    /**
     * Gets the summary of the flashcard.
     *
     * @return the summary of the flashcard.
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Gets the details of the flashcard.
     *
     * @return the details of the flashcard
     */
    public List<String> getDetails() {
        return details;
    }

    /**
     * To be implemented by child classes.
     *
     * @return a shortened description of the flashcard
     */
    public abstract String getShortDescription();

    /**
     * Check if the current instance is equal to the object passed in.
     *
     * @param obj The object to be compared against the current instance
     * @return whether or not the two objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Flashcard)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        Flashcard otherFlashcard = (Flashcard) obj;
        return name.equals(otherFlashcard.getName()) && summary.equals(otherFlashcard.getSummary())
            && details.equals(otherFlashcard.getDetails());
    }

    @Override
    public abstract int compareTo(Flashcard flashcard);

    public enum PriorityLevel {
        LOW,
        MEDIUM,
        HIGH,
        DEFAULT
    }
}
