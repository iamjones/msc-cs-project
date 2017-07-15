package domain.validation;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TaskParameterValidatorTest {

    private TaskParameterValidator taskParameterValidator;

    @Rule
    public ExpectedException exceptions = ExpectedException.none();

    @Before
    public void setup() {
        this.taskParameterValidator = new TaskParameterValidator();
    }

    @Test
    public void it_should_throw_an_illegal_argument_exception_if_no_input_path() {

        exceptions.expect(IllegalArgumentException.class);
        exceptions.expectMessage("An input path is required.");

        this.taskParameterValidator.checkInputPath(new String[]{});
    }

    @Test
    public void it_should_not_throw_an_illegal_argument_exception_if_an_input_path_exists() {

        this.taskParameterValidator.checkInputPath(new String[]{
            "input/file/path"
        });
    }

    @Test
    public void it_should_throw_an_illegal_argument_exception_if_no_output_path() {

        exceptions.expect(IllegalArgumentException.class);
        exceptions.expectMessage("An output path is required.");

        this.taskParameterValidator.checkOutputPath(new String[]{
            "input/file/path/"
        });
    }

    @Test
    public void it_should_not_throw_an_illegal_argument_exception_if_an_output_path_exists() {

        this.taskParameterValidator.checkOutputPath(new String[]{
            "input/file/path/",
            "output/file/path/"
        });
    }

    @Test
    public void it_should_throw_an_illegal_argument_exception_if_no_aspect_words_path() {

        exceptions.expect(IllegalArgumentException.class);
        exceptions.expectMessage("A path to the aspect words file is required.");

        this.taskParameterValidator.checkAspectWordsPath(new String[]{
            "input/file/path",
            "output/file/path"
        });
    }

    @Test
    public void it_should_not_throw_an_illegal_argument_exception_if_an_aspect_words_path_exists() {

        this.taskParameterValidator.checkAspectWordsPath(new String[]{
            "input/file/path",
            "output/file/path",
            "aspect/words/file/path"
        });
    }
}
