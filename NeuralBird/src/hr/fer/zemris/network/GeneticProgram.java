package hr.fer.zemris.network;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.game.model.GameModelAI;
import hr.fer.zemris.game.model.GameModelAITrainable;
import hr.fer.zemris.network.transfer_functions.ITransferFunction;
import hr.fer.zemris.network.transfer_functions.SigmoidTransferFunction;

/**
 * Class where genetic algorithm is implemented.
 * Class is used for training an neural network.
 *
 * @author Damir Kopljar
 *
 */
public class GeneticProgram {

    /** Max number of generations. */
    public static final int MAX_GENERATIONS = 50;
    /** Size of population. */
    public static final int POPULATION_SIZE = 50;

    public static final int SCORE_LIMIT = 600;
    public static final int TOURNAMENT_SIZE = 10;
    public static final int MAX_NUM_OF_PIPES = 50;

    public static final double CROSSOVER_ALPHA=0.5;
    public static final double MUTATION_RATE=0.01;

    /** Number of neuronsPerLayer */
    private static final int[] neuronsPerLayer = { 9, 50, 1 };
    private static Random rand = new Random();

    public NeuralNetwork train() {

        int numberOfGenerations = 0;

        TournamentSelection selectionO = new TournamentSelection();

        BLXAlphaCrossover crossoverO = new BLXAlphaCrossover(CROSSOVER_ALPHA);
        SimpleMutation mutationO = new SimpleMutation(MUTATION_RATE);

        // Napravi inicijalnu populaciju
        Solution[] population = createInitialPopulation(POPULATION_SIZE);

        while (numberOfGenerations < MAX_GENERATIONS) {

            Solution[] newPopulation = new Solution[POPULATION_SIZE];


            // Napravi novu populaciju
            for (int i = 0; i < POPULATION_SIZE; i++) {
                NeuralNetwork parent1 = selectionO.select(population, TOURNAMENT_SIZE);
                NeuralNetwork parent2 = selectionO.select(population, TOURNAMENT_SIZE);
                Solution child = new Solution(crossoverO.doCrossover(parent1, parent2));

                mutationO.mutate(child.network);
                child.fitness = calculateFitness(child.network);
                newPopulation[i] = child;

            }

            population = newPopulation;
            numberOfGenerations++;
            double currentFit = evaluatePopulation(population);


            System.out.println(currentFit + "   " + numberOfGenerations);

            //mutationO.setSigma(currentFit < 3 ? 0.03 : 0.01);

        }
        NeuralNetwork bestOne = getBestNet(population);

        System.out.println(calculateFitness(bestOne));
        // Engine testGame = new Engine(DIMENSION, DIMENSION, (new Random()).nextInt());


        return bestOne;
    }

    /**
     * Metoda kreira inicijalnu populaciju
     *
     * @param popSize velicina populacije
     * @return polje neuronskih mreza (populacija)
     */
    static Solution[] createInitialPopulation(int popSize) {

        Solution[] result = new Solution[popSize];

        ITransferFunction[] transferFunction = new ITransferFunction[neuronsPerLayer.length - 1];
        Arrays.fill(transferFunction, new SigmoidTransferFunction());

        for (int i = 0; i < popSize; i++) {
            Solution sol = new Solution(new NeuralNetwork(neuronsPerLayer, transferFunction, new GameModelAITrainable()));
            double[] weights = new double[sol.network.getWeightsCount()];
            for (int j = 0; j < weights.length; j++) {
				weights[j]=rand.nextDouble();
				if(rand.nextBoolean()){
					weights[j]*=-1;
				}
			}
            sol.network.setWeights(weights);
            sol.fitness = calculateFitness(sol.network);
            result[i] = sol;
        }
        return result;
    }

    /**
     * Metoda vraca najmanju gresku (1/brojFrame-ova) u populaciji
     *
     * @param population populacija
     * @return najmanja greska u populaciji
     */
    static double evaluatePopulation(Solution[] population) {

        double maxValue = population[0].fitness;
        for (Solution solution : population) {
            if (solution.fitness > maxValue) {
                maxValue = solution.fitness;
            }
        }
        return maxValue;
    }

    /**
     * Metoda vraca neuronsku mrezu koja je dosla najdalje u igrici
     *
     * @param population populacija
     * @return najbolja neuronska mreza
     */
    static NeuralNetwork getBestNet(Solution[] population) {

        double maxValue = population[0].fitness;
        NeuralNetwork bestNet = population[0].network;
        for (Solution solution : population) {
            if (solution.fitness > maxValue) {
                bestNet = solution.network;
                maxValue = solution.fitness;
            }
        }
        return bestNet;
    }



    /**
     * Metoda za racunanje greske. Greska se racuna tako da se ptica pokrene u NUMBER_OF_GAMES igara. Zatim se izracuna
     * se prosjecan broj frame-ova u NUMBER_OF_GAMES igara koji je ptica presla, a greska se racuna prema formuli
     * 1/prosjecanBrojFrame-ova
     *
     * @param net neuronska mreza koju testiramo (ptica)
     * @return 1/prosjecanBrojFrame-ova
     */
    public static final int NUMBER_OF_GAMES = 5;

    static double calculateFitness(NeuralNetwork net) {

        double counter = 0;
        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            GameModelAI model = new GameModelAITrainable();
            model.addEnvironmentListener(net);
            int tmpScore = 0;

            while (true) {

                if (!model.update(1) || model.getScore() > SCORE_LIMIT || model.getNumberOfPassedPipes() > MAX_NUM_OF_PIPES) {
                    break;
                }

                tmpScore = model.getScore();

            }
            counter += tmpScore * 1.0;

        }
        counter /= NUMBER_OF_GAMES;
        return counter;

    }

}
