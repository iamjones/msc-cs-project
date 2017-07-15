package domain.validation;

/**
 * Contains functions that are designed to make sure the arguments
 * passed into hadoop tasks are valid.
 */
public class TaskParameterValidator {

    /**
     * Checks if an input path has been supplied.
     * @param args String[]
     * @throws IllegalArgumentException if no input path is supplied
     */
    public void checkInputPath(String[] args) {

        try {
            String inputPath = args[0];
        } catch(ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("An input path is required.");
        }
    }

    /**
     * Checks if an output path has been supplied.
     * @param args String[]
     * @throws IllegalArgumentException if no output path is supplied
     */
    public void checkOutputPath(String[] args) {

        try {
            String inputPath = args[1];
        } catch(ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("An output path is required.");
        }
    }

    /**
     * Checks if an aspect words file path has been supplied.
     * @param args String[]
     * @throws IllegalArgumentException if no aspect words file has been supplied
     */
    public void checkAspectWordsPath(String[] args) {

        try {
            String inputPath = args[2];
        } catch(ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("A path to the aspect words file is required.");
        }
    }
}
