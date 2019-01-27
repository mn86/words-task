import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Words class implements logic to store words that contain only English alphabetical characters
 * and calculate words in case sensitive and insensitive modes.
 * @author Mikhail Nazarov
 */
public class Words {

    private static final String WORDS_SPLITTER = ";";
    private final Map<String, Long> wordsMap;

    /**
     * Words no args constructor to instantiate Map
     */
    public Words() {
        this.wordsMap = new LinkedHashMap<>();
    }

    /**
     * Adds word to class, only English alphabetical characters allowed (both upper and lower case).
     * Trims word for unnecessary spaces before adding.
     * @param word - word to add
     */
    public void addWord(String word) {
        word = word.trim();
        if (word.length() > 0 && word.chars().allMatch(Character::isLetter)) {
            wordsMap.merge(word, 1L, Long::sum);
        }
    }

    /**
     * Returns entire words map
     * @return wordsMap
     */
    public Map<String, Long> getWordsMap() {
        return wordsMap;
    }

    /**
     * Counts words in case sensitive/insensitive modes.
     * @param word - word to count
     * @param caseSensitive - true for caseSensitive count, false for caseInsensitive count
     * @return long
     */
    public long getWordCount(String word, boolean caseSensitive) {
        if (caseSensitive) {
            return wordsMap.getOrDefault(word, 0L);
        }
        final String wordLowerCase = word.toLowerCase();
        return wordsMap.entrySet()
                .stream()
                .filter(w -> w.getKey().toLowerCase().equals(wordLowerCase))
                .mapToLong(Map.Entry::getValue)
                .sum();
    }

    /**
     * Add several words (sentence) divided by WORDS_SPLITTER
     * @param sentence - string of words divided with WORDS_SPLITTER (';' by default)
     */
    public void addSentence(String sentence) {
        Stream<String> words = Stream.of(sentence.split(WORDS_SPLITTER));
        words.forEach(this::addWord);
    }

}
