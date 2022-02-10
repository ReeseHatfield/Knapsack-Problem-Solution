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
        //Creates and returns an ArrayList of populationSize Chromosome	objects	that
        //each	contain	the	items, with their included field randomly set to true or false.
    }

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Item> items = readData("more_items.txt");//parses the data into items
        //System.out.println(items);
        int populationSize = 20000;
        ArrayList<Chromosome> population = (initializePopulation(items, populationSize));

        for(int i = 0; i < 500; i ++){//runs 20 times
            ArrayList<Chromosome> nextGeneration = new ArrayList<>();
            nextGeneration.addAll(population);

            Collections.shuffle(population);//randomizes population for pairing off
            for(int j = 0; j <population.size(); j += 2){
                nextGeneration.add(population.get(j).crossover(population.get(j+1)));
            }


            //mutate 10% of nextGeneration, rounding down
            int numberToMutate = (int) (nextGeneration.size() * 0.10);
            int amountMutated = 0;
            while(amountMutated < numberToMutate){
                nextGeneration.get((int)(Math.random() * nextGeneration.size())).mutate();
                amountMutated++;
            }
           // population.addAll(initializePopulation(items,populationSize-10));
            Collections.sort(nextGeneration);//this should call the comparable
            population.clear();

            for(int j = 0; j < populationSize; j++){
                population.add(nextGeneration.get(j));
                //takes the best of the generation and their children, and only adds the best
                // of them to the next generation. I do this with the population size, so the amount of
                //individuals stays constant
            }
            Collections.sort(population);
            nextGeneration.clear();



            System.out.println(population);
        }
        Collections.sort(population);
        System.out.println("The best solution is " + population.get(0));


    }
    //1. item class, chromosome constructor, read data and initialize population
}
