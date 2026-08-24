package practica.compi2.util;

public final class PigLatinUtil {

    public static final String LECTURA = "%OINK_OINK";
    public static final String ESCRITURA = "%OINK";

    private static final String VOWELS = "aeiouAEIOU";

    public PigLatinUtil(){}

    public String translateWord(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        int i = 0;
        while (i < word.length() && VOWELS.indexOf(word.charAt(i)) < 0) {
            i++;
        }
        if (i == 0) {
            return word + "way";
        }
        if (i == word.length()) {
            return word + "ay";
        }
        return word.substring(i) + word.substring(0, i) + "ay";
    }

}
