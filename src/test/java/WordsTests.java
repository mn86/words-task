import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

/**
 * Tests for Words class
 * @author Mikhail Nazarov
 */
class WordsTests {

    private Words words;

    private final List<String> differentCatCasesList = Arrays.asList("cat", "Cat", "CAT");

    @BeforeEach
    void wordsInit() {
        words = new Words();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Test", " word", "COUNT"})
    void wordShouldBeAddedOnce(String word) {
        words.addWord(word);
        assertEquals(1L, words.getWordsMap().size(), "only one element should be added with addWord for " + word);
        assertTrue(words.getWordsMap().containsKey(word.trim()), "wrong word count with addWord for " + word);
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "test-2", "123TEST", "", "w o r d"})
    void onlyAlphabeticalCharsAllowed(String word) {
        words.addWord(word);
        assertEquals(0L, words.getWordsMap().size(), "word should not be added if contains anything but uppercase and lowercase characters from English alphabet.");
    }

    @Test
    void counterShouldWorkProperlyForMixedWords() {
        List<String> wordsSequence = Arrays.asList("cat", "dog", "cat", "bird", "cat", "cat", "dog");
        wordsSequence.forEach(words::addWord);
        assertEquals(4L, words.getWordCount("cat", true), "wrong 'cat' count");
        assertEquals(2L, words.getWordCount("dog", true), "wrong 'dog' count");
        assertEquals(1L, words.getWordCount("bird", true), "wrong 'bird' count");
    }

    @Test
    void caseSensitiveCounterShouldWorkProperly() {
        differentCatCasesList.forEach(words::addWord);
        for (String word: differentCatCasesList) {
            assertEquals(1L, words.getWordCount(word, true), "case Sensitive count should return 1 for " + words.getWordsMap().keySet());
        }
    }

    @Test
    void caseInsensitiveCounterShouldWorkProperly() {
        differentCatCasesList.forEach(words::addWord);
        for (String word: differentCatCasesList) {
            assertEquals(differentCatCasesList.size(), words.getWordCount(word, false), "case Insensitive count should return 3 for " + words.getWordsMap().keySet());
        }
    }

    @Test
    void counterShouldReturnZeroForAbsentWord() {
        String absentWord = "someabsentword";
        assertEquals(0L, words.getWordCount(absentWord, true), "should return 0 for absent word in case Sensitive mode");
        assertEquals(0L, words.getWordCount(absentWord, false), "should return 0 for absent in case Insensitive mode");
    }

    @ParameterizedTest
    @CsvSource({"Test;sentence;with;some;words;and;separators, 7", "word;word;test, 2", "test;;123;-; word;WORD, 3"})
    void sentenceCouldBeAdded(String sentence, int count) {
        words.addSentence(sentence);
        assertEquals(count, words.getWordsMap().size(), count + " words should be added by addSentence for " + sentence);
    }

}