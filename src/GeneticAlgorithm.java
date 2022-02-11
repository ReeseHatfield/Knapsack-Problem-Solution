import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GeneticAlgorithm {
    public static ArrayList<Item> readData(String filename) throws FileNotFoundException{
        ArrayList<Item> items = new ArrayList<>();//this should be returned
        Scanner reader = new Scanner(new File(filename));
        while(reader.hasNextLine()){
            String currentLine = reader.nextLine();
            String[] brokenString = currentLine.split(", ");
            items.add(new Item(brokenString[0], Double.parseDouble(brokenString[1]), Integer.parseInt(brokenString[2])));
        }
        //to read in data, I take in each new line in the file as a string, split it into 3 strings, parse the last two, and add
        //a new item into the arrayList that is returned
        return items;
    }
    public static ArrayList<Chromosome> initializePopulation(ArrayList<Item> items, int populationSize){
        ArrayList<Chromosome> population = new ArrayList<>();
        for(int i = 0; i < populationSize; i ++){
            population.add(new Chromosome(items));
        }
        return population;
        //Creates and returns an ArrayList of populationSize me.reese.genetic.Chromosome	objects	that
        //each	contain	the	items, with their included field randomly set to true or false.
    }

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Item> items = readData("more_items.txt");//parses the data into items
        //System.out.println(items);
        int populationSize = 1000;
        int generations = 5000;
        //these integers can be tweaked to yield better results
        //I've found that if the population size is too low, no matter how many generations, you may not get a good solution, so
        //the higher the population size, the more consistent the results, but the higher the generations, the better your results will be
        ArrayList<Chromosome> population = (initializePopulation(items, populationSize));

        for(int i = 0; i < generations; i ++){
            //this main loop will run how ever many times the generations' int is set to
            ArrayList<Chromosome> nextGeneration = new ArrayList<>(population);

            for(int j = 1; j <population.size(); j += 2){
                nextGeneration.add(population.get(j).crossover(population.get(j-1)));
                //adds the current population to the next generation
            }

            int amountMutated = 0;
            while(amountMutated++ < (int) (nextGeneration.size() * 0.10)){
                nextGeneration.get((int)(Math.random() * nextGeneration.size())).mutate();
                //exposes 10% (rounding down) of the population to the mutation method,
            }

            Collections.sort(nextGeneration);
            population.clear();
            //sorts the nextGeneration by calling comparable and empties the population

            for(int j = 0; j < items.size()/2.0; j++){
                population.add(nextGeneration.get(j));
                //takes the best of the generation and their children, and only adds the best
                // of them to the next generation
            }

            System.out.println(population);
            Collections.shuffle(population);//randomizes population for pairing off
        }
        Collections.sort(population);
        System.out.println("The fittest solution is " + population.get(0));
        System.out.println("This solution was found with a population size of " + populationSize + " and " + generations + " generations");
    }
}
