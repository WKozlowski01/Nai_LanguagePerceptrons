import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {


        List<Vct> treningVectors = getFilesToLanguagesReady("Jezyki");
        List<Vct> testVectors = getFilesToLanguagesReady("JezykiTest");

//        for (var x : treningVectors) {
//            System.out.println(x.getLanguageName() + x.getVectorFromMap());
//        }

        Collections.shuffle(treningVectors);

        List<Perceptron> perceptronsList = new ArrayList<>();


        for (Vct language : treningVectors) { //tworzenie perceptronow

            Random rand = new Random();
            List<Double> weightsRandom = Stream.generate(rand::nextDouble).limit(treningVectors.get(0).getVectorFromMap().size()).collect(Collectors.toList());

            String languageName = language.getLanguageName().split("_")[0];
            boolean exists = perceptronsList.stream().anyMatch(p -> p.getName().equals(languageName + "_" + "Perceptron"));
            if (!exists) {
                perceptronsList.add(new Perceptron(weightsRandom,languageName + "_" + "Perceptron"));
            }
        }

        for (var perceptron : perceptronsList) {  //uczenie perceptronu
            double totalError;
            do {
                totalError = 0;
                for (var vct : treningVectors) {
                    String vctLanguageName = vct.getLanguageName().split("_")[0];
                    int decision = (vctLanguageName + "_Perceptron").equals(perceptron.getName()) ? 1 : -1;
                    double error = perceptron.Leran(vct.getVectorFromMap(), decision);
                    totalError = totalError + error;
                }
//                System.out.println(totalError);
            } while (totalError > 0.1);
        }

        //test perceptronu

        List<Integer> testResults = new ArrayList<>();
        for(var testVector : testVectors) {
            String bestMatch = "";
            double bestScore = 0;
            for (var perceptron : perceptronsList) {
                double score = perceptron.Compute(testVector.getVectorFromMap());
//                System.out.println(perceptron.getName() + "->" + score);
                if (score > bestScore) {
                    bestScore = score;
                    bestMatch = perceptron.getName();
                }
            }
            if(bestMatch.replace("_Perceptron", "").equals(testVector.getLanguageName().split("_")[0])) {
                testResults.add(1);
            }else{
                testResults.add(0);
            }
        }

        double countOnes = testResults.stream().filter(n -> n == 1).count();
        double percentage =  countOnes / testResults.size() * 100;
        System.out.println(testResults);
        System.out.println("skuteczność perceptronu wynosi " + percentage+"%");


       SwingUtilities.invokeLater(()-> {
           new Gui(perceptronsList);
       });


    }

    public static List<Language> loadLanguages(String baseDirectory) {
        List<Language> languages = new ArrayList<>();
        File baseFolder = new File(baseDirectory);

        for (File countryFolder : baseFolder.listFiles()) {
            if (countryFolder.isDirectory()) {
                String languageName = countryFolder.getName();
                List<String> texts = new ArrayList<>();

                for (File textFile : countryFolder.listFiles()) {
                    if (textFile.isFile() && textFile.getName().endsWith(".txt")) {
                        try {
                            String content = Files.readString(textFile.toPath()).trim();
                                texts.add(content);
                        } catch (IOException e) {
                            System.err.println("Błąd wczytywania pliku: " + textFile.getAbsolutePath());
                        }
                    }
                }
                    languages.add(new Language(languageName, texts));
            }
        }
        return languages;
    }


    public static List<Vct> getFilesToLanguagesReady(String baseDirectory) {


        List<Language> languages = loadLanguages(baseDirectory);
        List<Vct> languageVectorList = new ArrayList<>();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        for (Language lang : languages) {
            int textIndex = 1;
            for (String txt : lang.getText()) {

                Map<Character, Integer> letterCount = new TreeMap<>();
                for (char c : alphabet.toCharArray()) {
                    letterCount.put(c, 0);
                }
                txt = txt.toLowerCase();

                String newTxt = "";
                for (char znak : txt.toCharArray()) {
                    if ((int) znak >= 97 && (int) znak <= 122) {
                        newTxt = newTxt += znak;
                    }
                }
                for (Character letter : newTxt.toCharArray()) {
                    letterCount.put(letter, letterCount.getOrDefault(letter, 0) + 1);
                }
                Vct vct = new Vct(lang.getLanguageName() + "_text" + textIndex);
                vct.setCharMap(letterCount);
                languageVectorList.add(vct);
                textIndex++;
            }

        }
        return languageVectorList;
    }

    public static Vct testTxtProcess(String txt){


        String alphabet = "abcdefghijklmnopqrstuvwxyz";

                Map<Character, Integer> letterCount = new TreeMap<>();
                for (char c : alphabet.toCharArray()) {
                    letterCount.put(c, 0);
                }
                txt = txt.toLowerCase();

                String newTxt = "";
                for (char znak : txt.toCharArray()) {
                    if ((int) znak >= 97 && (int) znak <= 122) {
                        newTxt = newTxt += znak;
                    }
                }
                for (Character letter : newTxt.toCharArray()) {
                    letterCount.put(letter, letterCount.getOrDefault(letter, 0) + 1);
                }
                Vct vct = new Vct();
                vct.setCharMap(letterCount);

        return vct;

    }


}

