import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Perceptron {

    private List<Double> weights;

    private double learnConst = 0.1;
    private String name;
    private List<Double> inputs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Perceptron(List<Double> weights, String name) {
        this.weights = weights;
        this.name = name;
        normalizeWeights();
    }

    public double Compute(List<Double> input) {
        List<Double> result = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            double tmp = input.get(i) * weights.get(i);
            result.add(tmp);
        }
        double WtX = 0;

        for (int i = 0; i < result.size(); i++) {
            WtX += result.get(i);
        }
        double y = (2 / (1 + Math.exp(-1 * WtX))) - 1;
        return y;

    }

    public double Leran(List<Double> inputs, int decision) {

        double y = Compute(inputs);
        double error = (1.0 / 2.0) * Math.pow((decision - y), 2);

        IntStream.range(0, weights.size()).forEach(i -> weights.set(i, weights.get(i) + learnConst * 0.5 * (decision - y) * (1 - Math.pow(y, 2)) * inputs.get(i)));

        return error;
    }

    private void normalizeWeights() {
        double norm = Math.sqrt(weights.stream().mapToDouble(w -> w * w).sum());
        IntStream.range(0, weights.size()).forEach(i ->
                weights.set(i, weights.get(i) / norm)
        );
    }
}
