package BST;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.util.Stack;

public class BSTAnimationController {
    @FXML
    private TextField tfKey;

    // Add your BST and BTView instances here
    private BST<Integer> tree = new BST<Integer>(); /// Create a tree
    private BTView view = new BTView(tree); // Create a View
    private final Stack<Action> undoStack = new Stack<>();
    private final Stack<Action> redoStack = new Stack<>();


    private void pushAction(int key, boolean isInsert) {
        undoStack.push(new Action(key, isInsert));
        // Clear the redo stack when a new action is performed
        // This is because we can't redo an action after a new action is performed
        redoStack.clear();
    }

    private Action popUndoAction() {
        if (!undoStack.isEmpty()) {
            return undoStack.pop();
        }
        return null;
    }

    private void pushRedoAction(Action action) {
        redoStack.push(action);
    }

    private Action popRedoAction() {
        if (!redoStack.isEmpty()) {
            return redoStack.pop();
        }
        return null;
    }

    @FXML
    private BorderPane borderPane = new BorderPane(); // Add a field for the BorderPane

    // This method will be called after the FXML file is loaded
    public void initialize() {
        borderPane.setCenter(view);
    }

    // Constructor
    public BSTAnimationController() {
        // Initialize your BST and BTView instances here
        tree = new BST<Integer>();
        view = new BTView(tree);
    }

    @FXML
    private void handleInsert(ActionEvent event) {
        // Implement the insert logic
        int key = Integer.parseInt(tfKey.getText());
        if (tree.search(key)) { // key is in the tree already
            view.displayTree();
            view.setStatus(key + " is already in the tree");
        } else {
            tree.insert(key); // Insert a new key
            view.displayTree();
            view.setStatus(key + " is inserted in the tree");
        }
        // After user clicks the Insert button, the text field should be cleared
        tfKey.setText("");
        // After inserting, push the action to the history stack
        pushAction(key, true);
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        // Implement the delete logic
        int key = Integer.parseInt(tfKey.getText());
        if (!tree.search(key)) { // key is not in the tree
            view.displayTree();
            view.setStatus(key + " is not in the tree");
        } else {
            tree.delete(key); // Delete a key
            view.displayTree();
            view.setStatus(key + " is deleted from the tree");
        }
        // After user clicks the Delete button, the text field should be cleared
        tfKey.setText("");
        pushAction(key, false);
    }

    // Change the node with current value to a new value
    // User input the current value and new value in the text fields respectively and click the Update button to change
    // the node value in the tree view accordingly.
    public void handleUpdate(ActionEvent event) {
        String combinedText = tfKey.getText();
        String[] splitText = combinedText.split(",");

        if (splitText.length != 2) {
            view.setStatus("Please enter two values separated by a comma.  Please use <currentValue>, <newValue>");
            return;
        }
        String currentText = splitText[0].trim();
        String newText = splitText[1].trim();

        int currentKey = Integer.parseInt(currentText);
        int newKey = Integer.parseInt(newText);
        if (!tree.search(currentKey)) {
            view.displayTree();
            view.setStatus(currentKey + " is not in the tree");
        } else if (tree.search(newKey)) {
            view.displayTree();
            view.setStatus(newKey + " is already in the tree");
        } else {
            tree.delete(currentKey);
            tree.insert(newKey);
            view.displayTree();
            view.setStatus(currentKey + " is updated to " + newKey);
        }
        // After user clicks the Update button, the text field should be cleared
        tfKey.setText("");
    }

    public void handleSearch(ActionEvent event) {
        // Search for a node in the tree. Highlight the node if found.
        int key = Integer.parseInt(tfKey.getText());
        if (tree.search(key)) { // key is in the tree already
            view.displayTree();
            view.setStatus(key + " is found in the tree");
        } else {
            view.displayTree();
            view.setStatus(key + " is not found in the tree");
        }
        tfKey.setText("");
    }

    @FXML
    private void handleUndo(ActionEvent event) {
        Action lastAction = popUndoAction();
        if (lastAction != null) {
            int key = lastAction.getKey();
            if (lastAction.isInsert()) {
                tree.delete(key);
                view.displayTree();
                view.setStatus(key + " insertion undone");
            } else {
                tree.insert(key);
                view.displayTree();
                view.setStatus(key + " deletion undone");
            }
            // Move the action to the redo stack
            pushRedoAction(lastAction);
        }
    }

    @FXML
    private void handleRedo(ActionEvent event) {
        Action redoAction = popRedoAction();
        if (redoAction != null) {
            int key = redoAction.getKey();
            if (redoAction.isInsert()) {
                tree.insert(key);
                view.displayTree();
                view.setStatus(key + " insertion redone");
            } else {
                tree.delete(key);
                view.displayTree();
                view.setStatus(key + " deletion redone");
            }
            // Move the action back to the undo stack
            pushUndoAction(redoAction);
        }
    }

    private void pushUndoAction(Action action) {
        undoStack.push(action);
    }

    public void handleTraverseBFS(ActionEvent event) {

    }
}
