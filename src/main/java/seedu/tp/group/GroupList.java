package seedu.tp.group;

import seedu.tp.exceptions.HistoryFlashcardException;
import seedu.tp.exceptions.InvalidFlashcardIndexException;
import seedu.tp.exceptions.UnrecognizedFlashcardGroupException;
import seedu.tp.flashcard.Flashcard;
import seedu.tp.flashcard.FlashcardList;
import seedu.tp.ui.Ui;

import java.util.ArrayList;
import java.util.List;

import static seedu.tp.utils.Constants.INDEX_FIELD;
import static seedu.tp.utils.Constants.NAME_FIELD;

/**
 * Lists of flashcard groups.
 */
public class GroupList {
    private List<FlashcardGroup> groups;

    /**
     * Constructs a list of groups.
     */
    public GroupList() {
        this.groups = new ArrayList<FlashcardGroup>();
    }

    /**
     * Adds a new group to the group list.
     *
     * @param group the new group to be added to the list
     */
    public void addFlashcardGroup(FlashcardGroup group) {
        groups.add(group);
    }

    /**
     * Adds a flashcard to an existing group.
     *
     * @param ui used to prompt the user
     * @throws InvalidFlashcardIndexException if the user gives an index not an integer of out of bound
     */
    public void addFlashcardToOneGroup(Ui ui, FlashcardList flashcardList) throws HistoryFlashcardException {
        try {
            String indexGiven = ui.promptUserForRequiredField(INDEX_FIELD);
            assert !indexGiven.isEmpty() : "Invalid index";

            int flashcardIndex = Integer.parseInt(indexGiven) - 1;
            String groupName = ui.promptUserForRequiredField(NAME_FIELD);
            Flashcard flashcard = null;
            FlashcardGroup group = null;
            for (FlashcardGroup g : groups) {
                if (g.getName().equals(groupName)) {
                    flashcard = flashcardList.getFlashcardAtIdx(flashcardIndex);
                    g.addFlashcardToTheGroup(flashcard);
                    group = g;
                    break;
                }
            }
            if (group != null) {
                ui.confirmFlashcardAdditionToGroup(group, flashcard);
            } else {
                throw new UnrecognizedFlashcardGroupException("There is no such group.");
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new InvalidFlashcardIndexException();
        }
    }

    public List<FlashcardGroup> getGroups() {
        return groups;
    }

    /**
     * Returns a FlashcardList of flashcards belonging to the group specified by groupName.
     *
     * @param groupIdentifier either the name or the index of the flashcard group
     * @return FlashcardList of flashcards belonging to the group
     */
    public FlashcardList getFlashcardsInGroup(String groupIdentifier) throws UnrecognizedFlashcardGroupException {
        FlashcardList flashcardsInGroup;
        for (FlashcardGroup g : groups) {
            if (g.getName().equals(groupIdentifier)) {
                flashcardsInGroup = g.getGroupCards();
                return flashcardsInGroup;
            }
        }

        try {
            int groupIndex = Integer.parseInt(groupIdentifier) - 1;
            FlashcardGroup g = groups.get(groupIndex);
            return g.getGroupCards();
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new UnrecognizedFlashcardGroupException("Invalid group!");
        }
    }

    /**
     * Gets the flashcard group at a specific index.
     *
     * @param index the index.
     * @return the flashcard group at the specified index
     */
    public FlashcardGroup getGroupAtIdx(int index) {
        return groups.get(index);
    }

    /**
     * Gets the total number of groups in the list.
     *
     * @return total number of groups in the list
     */
    public int getTotalGroupNum() {
        return groups.size();
    }

    /**
     * Check if the current instance is equal to the object passed in.
     *
     * @param obj The object to be compared against the current instance
     * @return whether or not the two objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        GroupList otherGroupList = (GroupList) obj;
        if (this.getTotalGroupNum() != otherGroupList.getTotalGroupNum()) {
            return false;
        }

        for (int idx = 0; idx < otherGroupList.getTotalGroupNum(); idx++) {
            if (!this.getGroupAtIdx(idx).equals(otherGroupList.getGroupAtIdx(idx))) {
                return false;
            }
        }
        return true;
    }
}
