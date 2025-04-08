import java.util.*;

public class Vct {
    private String languageName;
    private Map<Character,Integer> charMap = new TreeMap<>();
    private Map<Character,Double> transformedMap = new TreeMap<>();


    private List<Double> vectorFromMap;


    public Map<Character, Integer> getCharMap() {
        return charMap;
    }

    public List<Double> getVectorFromMap() {
        return vectorFromMap;
    }

    public void setCharMap(Map<Character, Integer> charMap) {

        this.charMap = charMap;
        transformedMap = transofrmMap(charMap);
        vectorFromMap = convertMapToVector(transformedMap);
    }

    public String getLanguageName() {
        return languageName;
    }

    public Vct(String languageName) {
        this.languageName = languageName;
    }

    public Vct() {
    }


    @Override
    public String toString() {
        return "languageName='" + languageName + '\'' +
                ", charMap=" + transformedMap +
                '}'+"\n";
    }

    private static Map<Character, Double> transofrmMap  (Map<Character, Integer> inputMap) {
        int total=inputMap.values().stream().mapToInt(Integer::intValue).sum(); // zliczanie wszytskich znakow w mapie wejsciowej
        Map<Character,Double> result = new TreeMap<>();

        for(Map.Entry<Character, Integer> entry: inputMap.entrySet()) {
            double percent = (entry.getValue()/(double)total);
            result.put(entry.getKey(), percent);
        }

        return result;
}


    public Map<Character, Double> getTransformedMap() {
        return transformedMap;
    }

    private List<Double> convertMapToVector(Map<Character, Double> map) {
        List<Double> result = new ArrayList<>();

        for(Character znak : map.keySet()){
            result.add(map.get(znak));
        }
        return result;
    }

}
