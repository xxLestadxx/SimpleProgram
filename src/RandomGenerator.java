import java.util.Random;

public class RandomGenerator {
    private int[] numbers;
    private float[] probabilities;
    private Random random;

    public RandomGenerator(int[] numbers, float[] probabilities) {
        //check for the two arrays to be matched at length
        if (numbers.length != probabilities.length) {
            throw new IllegalArgumentException("Arrays numbers and probabilities must have the same length");
        }

        this.numbers = numbers;
        this.probabilities = new float[probabilities.length];
        this.random = new Random();

        float sum = 0;
        for (float p : probabilities) {
            sum += p;
        }
        // convert probabilities to cumulative probabilities
        float cumulativeSum = 0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeSum += probabilities[i];
            // normalizing and transforms the array into a cumulative distribution
            // a.k.a if you have [0.1;0.2;0.7] -> [0.1;0.3;1], that is how afterwards
            // when the random 0.0 - 1.0 number is chosen it will be matched to the range and assigned respectively
            this.probabilities[i] = cumulativeSum / sum;
        }
    }

    public int nextNum() {
        float rand = random.nextFloat();

        // cycles through the cumulative probabilities and picks the number depending on where the rand is
        // in the probability range
        for (int i = 0; i < probabilities.length; i++) {
            if (rand < probabilities[i]) {
                return numbers[i];
            }
        }

        throw new RuntimeException("Should never reach here if probabilities are correct");
    }
}
