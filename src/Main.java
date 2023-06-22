import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /**
         * I have decided to make the program as a little cli application instead of writing JUnit assertion tests
         * There are two arguments to be used when executing the program - test or run.
         * run - the user can play with how the user's chosen probabilities are going to play out on a hypothetical random numbers
         * test - 3 use cases to play out
         */

        // checking whether there are args passed to the program
        if (args.length == 0) {
            System.out.println(
                    "Choose a command: \n run - to add up to 10 numbers and their probabilities " +
                            "\n test - to print out predefined tests");
        } else if (args.length == 1) {
            if (args[0].equals("test")) {

                int[] numbers = {1, 2, 3, 4, 5};
                float[] probabilities = {0.1F, 0.25F, 0.3F, 0.25F, 0.1F};
                structureData(numbers, probabilities);
                System.out.println("-------------------------");

                int[] numbers2 = {1, 2, 3, 4, 5,6,7,8,9,10};
                float[] probabilities2 = {0.1F, 0.05F, 0.35F, 0.06F, 0.04F,0.1F, 0.01F, 0.02F, 0.03F, 0.24F};
                structureData(numbers2, probabilities2);
                System.out.println("-------------------------");

                int[] numbers3 = {1, 2, 3};
                float[] probabilities3 = {0.1F, 0.25F, 0.65F};
                structureData(numbers3, probabilities3);


            } else if (args[0].equals("run")) {

                //when the user chooses to play out with random numbers and probabilities, 3 functions are being called
                // first is the one to get the numbers
                int[] numbers = getNumbers();

                // second is to get the probabilities, with the numbers given, so that the length of both arrays to match
                float[] probabilities = getProbabilities(numbers);

                // run the simulation and output structured text
                structureData(numbers, probabilities);

            } else {
                System.out.println("Please enter either run or test as cli parameter.");
            }
        } else
            System.out.println("Please enter either run or test as cli parameter.");
    }

    /**
     * getNumbers is a function to take the user's random integers that want to later assign probabilities to it
     *
     * @return an array with integers
     */
    public static int[] getNumbers() {
        //using Scanner to take inputs from the user
        Scanner in = new Scanner(System.in);
        boolean integers = true;
        int[] numbers = new int[0];

        //using while loop to go over the commands whenever submits an invalid input
        while (integers) {
            System.out.println("Enter the desired random integers separated by space.");
            String line = in.nextLine();
            line = line.trim();
            // if the user submits an empty
            if (line.isEmpty() || line.equals(" ")) {
                System.err.println("Please enter an input before submitting.");
                continue;
            }
            //splits the line and puts the chunks into an array
            String[] inputNumber = line.split("\\s+");
            numbers = new int[inputNumber.length];

            //goes through the loop to see if the input is valid, if not start a new while cycle
            for (int i = 0; i < inputNumber.length; i++) {
                try {
                    int num = Integer.parseInt(inputNumber[i]);
                    numbers[i] = num;
                    integers = false;
                } catch (Exception e) {
                    System.out.println("You entered string " + line);
                    System.err.println("The input is not integers. Please input only integers.");
                    integers = true;
                }
            }
        }
        return numbers;
    }

    /**
     * getProbabilities is a function to get the probabilities the user wants to assign to each of the numbers
     * they initially submit. It takes a parameter of the numbers' array so that it can track if both arrays have
     * the same length.
     *
     * @param numbers
     * @return an array with the assigned probabilities
     */
    public static float[] getProbabilities(int[] numbers) {
        float[] probabilities = new float[0];
        boolean probabilityBoolean = true;

        //using Scanner to take inputs from the user
        Scanner in = new Scanner(System.in);
        //using while loop to go over the commands whenever submits an invalid input
        while (probabilityBoolean) {

            float sumOfProbabilities = 0;
            System.out.println("Enter a probability for each of the integers, separated by space.");

            String line = in.nextLine();
            line = line.trim();

            if (line.isEmpty() || line.equals(" ")) {
                System.err.println("Please enter an input before submitting.");
                continue;
            }

            String[] probabilityInput = line.split("\\s+");
            if (probabilityInput.length != numbers.length) {
                System.err.println("The input number of probabilities is not equal to those of the number of integers." +
                        " Please add the correct number of " + numbers.length + " probabilities.");
                continue;
            }
            probabilities = new float[probabilityInput.length];
            for (int i = 0; i < probabilityInput.length; i++) {
                try {
                    float num = Float.parseFloat(probabilityInput[i]);
                    probabilities[i] = num;
                    sumOfProbabilities += num;
                    probabilityBoolean = false;
                } catch (Exception e) {
                    System.out.println("You entered string " + line);
                    System.err.println("The input is not float numbers. Please input only float numbers.");
                    probabilityBoolean = true;
                }
            }
            //if someone decides to use the extremes of the float type, they might get something close to 1.00000 but not exactly
            if (sumOfProbabilities > 1.01 || sumOfProbabilities < 0.99) {
                System.err.println("The sum of probabilities do not add up to 1.0! Please enter correct probabilities for the " + numbers.length + " numbers.");
                probabilityBoolean = true;
            }
        }
        return probabilities;

    }

    /**
     * structureData - is a function that takes the numbers and their assigned probabilities, goes through the
     * random generation and then structures and outputs a human-readable output.
     *
     * @param numbers
     * @param probabilities
     */
    public static void structureData(int[] numbers, float[] probabilities) {
        RandomGenerator generator = new RandomGenerator(numbers, probabilities);
        int[] counter = new int[numbers.length];
        //for loop to generate 100 results
        for (int i = 0; i < 100; i++) {
            int randNum = generator.nextNum();
            for (int k = 0; k < numbers.length; k++) {
                //check to count how many times a number has been picked
                if (randNum == numbers[k]) {
                    counter[k]++;
                }
            }
        }
        //structured output for the user to see the results of a 100 sample.
        for (int k = 0; k < numbers.length; k++) {
            System.out.println("The number " + numbers[k] + " with probability " + probabilities[k] + " had been encountered " +
                    counter[k] + " many times during the 100 sample.");
        }
    }
}

