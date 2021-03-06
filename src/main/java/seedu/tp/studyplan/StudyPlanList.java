package seedu.tp.studyplan;

import seedu.tp.exceptions.InvalidFlashcardIndexException;
import seedu.tp.flashcard.FlashcardList;
import seedu.tp.ui.Ui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static seedu.tp.utils.Constants.DATE_FIELD;
import static seedu.tp.utils.Constants.EMPTY_SPACE;
import static seedu.tp.utils.Constants.INDEXES_FIELD;
import static seedu.tp.utils.Constants.LOG_FOLDER;

public class StudyPlanList {

    protected static final Logger LOGGER = Logger.getLogger(StudyPlanList.class.getName());
    private static final String FILE_PATH = LOG_FOLDER + "study_plan.log";

    private Map<LocalDate, List<Integer>> studyPlanList;

    /**
     * Constructor for StudyPlan.
     */
    public StudyPlanList() {
        this.studyPlanList = new TreeMap<>();
    }

    /**
     * Constructor for StudyPlanList.
     *
     * @param studyPlanList the list of daily study plans to be added
     */
    public StudyPlanList(List<Map.Entry<LocalDate, List<Integer>>> studyPlanList) {
        this();

        assert studyPlanList != null : "Invalid null study plan list!";

        for (Map.Entry<LocalDate, List<Integer>> entry : studyPlanList) {
            this.studyPlanList.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Set up the Study Plan logger. Call once at the start of the program.
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
     * Updates the study plan.
     *
     * @param ui            the Ui class to be used for interaction with user
     * @param flashcardList the flashcard list used for checking index boundary
     * @throws InvalidFlashcardIndexException exception thrown when user input invalid indexes
     */
    public void updateStudyPlan(Ui ui, FlashcardList flashcardList) throws InvalidFlashcardIndexException {
        assert ui != null : "Invalid null Ui!";
        assert flashcardList != null : "Invalid null FlashcardList!";

        LocalDate date = ui.promptUserForRequiredLocalDate(DATE_FIELD);
        String[] indexesStr = ui.promptUserForRequiredField(INDEXES_FIELD).split(EMPTY_SPACE);
        List<Integer> indexes = new ArrayList<>();
        try {
            for (String dateStr : indexesStr) {
                int index = Integer.parseInt(dateStr) - 1;
                if (index < 0 | index >= flashcardList.getTotalFlashcardNum()) {
                    throw new IndexOutOfBoundsException();
                }
                indexes.add(index);
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new InvalidFlashcardIndexException();
        }
        studyPlanList.put(date, indexes);
    }

    public List<Map.Entry<LocalDate, List<Integer>>> getStudyPlanList() {
        return new ArrayList<>(studyPlanList.entrySet());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StudyPlanList)) {
            return false;
        }
        if (this == obj) {
            return true;
        }

        StudyPlanList otherStudyPlan = (StudyPlanList) obj;
        List<Map.Entry<LocalDate, List<Integer>>> otherStudyPlanList = otherStudyPlan.getStudyPlanList();
        List<Map.Entry<LocalDate, List<Integer>>> studyPlanList = this.getStudyPlanList();
        if (otherStudyPlanList.size() != studyPlanList.size()) {
            return false;
        }
        for (int i = 0; i < studyPlanList.size(); i++) {
            Map.Entry<LocalDate, List<Integer>> entry = studyPlanList.get(i);
            Map.Entry<LocalDate, List<Integer>> otherEntry = otherStudyPlanList.get(i);

            if (!entry.getKey().equals(otherEntry.getKey())) {
                return false;
            }
            if (!entry.getValue().equals(otherEntry.getValue())) {
                return false;
            }
        }
        return true;
    }
}
