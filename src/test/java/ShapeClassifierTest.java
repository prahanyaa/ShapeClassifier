import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class ShapeClassifierTest {

    @Test
    @DisplayName("Correct guess for Line")
    void testCorrectGuessForLine() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Line,Small,Yes,5");
        assertEquals("Yes", result, "Correct guess for Line should return 'Yes'");
    }

    @Test
    @DisplayName("Incorrect shape guess for Line")
    void testIncorrectShapeGuessForLine() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Circle,Small,Yes,5");
        assertTrue(result.contains("No"), "Incorrect shape guess for Line should return 'No'");
    }

    @Test
    @DisplayName("Shape suggestion for Line")
    void testShapeSuggestionForLine() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Rectangle,Small,Yes,5");
        assertTrue(result.contains("Suggestion=Line"), "Should suggest 'Line' for single parameter");
    }

    @Test
    @DisplayName("Correct guess for Circle")
    void testCorrectGuessForCircle() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Circle,Large,Yes,10");
        assertEquals("Yes", result, "Correct guess for Circle should return 'Yes'");
    }

    @Test
    @DisplayName("Incorrect size guess for Circle")
    void testIncorrectSizeGuessForCircle() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Circle,Small,Yes,10");
        assertTrue(result.contains("Wrong Size"), "Incorrect size guess should indicate 'Wrong Size'");
    }

    @Test
    @DisplayName("Incorrect even/odd guess for Circle")
    void testIncorrectEvenOddGuessForCircle() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Circle,Large,No,10");
        assertTrue(result.contains("Wrong Even/Odd"), "Incorrect even/odd guess should indicate 'Wrong Even/Odd'");
    }

    @Test
    @DisplayName("Correct guess for Equilateral Triangle")
    void testCorrectGuessForEquilateralTriangle() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Equilateral,Small,Yes,3,3,3");
        assertEquals("Yes", result, "Correct guess for Equilateral Triangle should return 'Yes'");
    }

    @Test
    @DisplayName("Shape suggestion for Equilateral Triangle")
    void testSuggestionForEquilateralTriangle() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Isosceles,Small,Yes,3,3,3");
        assertTrue(result.contains("Suggestion=Equilateral"), "Should suggest 'Equilateral' for equal sides triangle");
    }

    @Test
    @DisplayName("Correct guess for Rectangle")
    void testCorrectGuessForRectangle() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Rectangle,Large,Yes,4,6,4,6");
        assertEquals("Yes", result, "Correct guess for Rectangle should return 'Yes'");
    }

    @Test
    @DisplayName("Shape suggestion for Square")
    void testSuggestionForSquare() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Rectangle,Small,Yes,4,4,4,4");
        assertTrue(result.contains("Suggestion=Square"), "Should suggest 'Square' for all sides equal");
    }

    @Test
    @DisplayName("Bad guesses exceed limit")
    void testBadGuessesExceedLimit() {
        ShapeClassifier classifier = new ShapeClassifier();
        classifier.evaluateGuess("Rectangle,Small,Yes,3,3,3"); // 1st bad guess
        classifier.evaluateGuess("Circle,Large,No,4,4");       // 2nd bad guess
        Exception exception = assertThrows(RuntimeException.class, () -> {
            classifier.evaluateGuess("Line,Large,Yes,5");       // 3rd bad guess
        });
        assertTrue(exception.getMessage().contains("Bad guess limit exceeded"),
                "Should throw an error after 3 bad guesses");
    }

    @Test
    @DisplayName("Correct guess for Ellipse")
    void testCorrectGuessForEllipse() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Ellipse,Large,Yes,5,7");
        assertEquals("Yes", result, "Correct guess for Ellipse should return 'Yes'");
    }

    @Test
    @DisplayName("Shape suggestion for Ellipse")
    void testSuggestionForEllipse() {
        ShapeClassifier classifier = new ShapeClassifier();
        String result = classifier.evaluateGuess("Circle,Small,Yes,5,7");
        assertTrue(result.contains("Suggestion=Ellipse"), "Should suggest 'Ellipse' for unequal two-parameter shape");
    }
}
