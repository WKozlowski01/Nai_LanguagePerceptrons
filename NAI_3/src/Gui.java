import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Gui extends JFrame{
    private JPanel mainPanel;
    private JPanel inputPanel;
    private JPanel bottomPanel;
    private JButton checkButton;
    private JLabel resultLabel;
    private JTextArea inputField;
    private List<Perceptron> perceptronsList;

    public Gui(List<Perceptron> perceptrons) {
        this.perceptronsList = perceptrons;

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Panel wejściowy
        inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Dodanie JTextArea do JScrollPane
        inputField = new JTextArea(10, 30);
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(inputField);
        inputPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel dolny
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Label
        resultLabel = new JLabel("Wpisz tekst do analizy!");
        bottomPanel.add(resultLabel);

        // Przycisk
        checkButton = new JButton("Sprawdź");
        bottomPanel.add(checkButton);

        // Obsługa przycisku
        checkButton.addActionListener(e -> analyzeText());

        // Konfiguracja okna
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
    }

    // Metoda do analizy tekstu
    private void analyzeText() {
        String testInput = inputField.getText();  // Pobranie tekstu od użytkownika
        if (testInput.isEmpty()) {
            resultLabel.setText("Wpisz tekst do analizy!");
            return;
        }

        Vct testVector =Main.testTxtProcess(testInput);
        String bestMatch = "";
        double bestScore = 0;

        for (var perceptron : perceptronsList) {
            double score = perceptron.Compute(testVector.getVectorFromMap());
            System.out.println(perceptron.getName() + " -> " + score);
            if (score > bestScore) {
                bestScore = score;
                bestMatch = perceptron.getName();
            }
        }

        String result = "Najbardziej prawdopodobny język to " + bestMatch.replace("_Perceptron", "");
        resultLabel.setText(result);
    }




}


