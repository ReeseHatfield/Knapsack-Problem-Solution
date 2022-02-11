import java.util.ArrayList;
import java.util.Random;
public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome> {
    private final static Random rng = new Random();
    public Chromosome(){
        //no argument constructor is used in the crossover when a child is created
    }
    public Chromosome(ArrayList<Item> items){
        for (Item item : items) {
            Item newItem = new Item(item);
            newItem.setIncluded(rng.nextBoolean());
            this.add(newItem);
        }
        //Chromosome is made with every Item passed in, randomly setting included to true or false
    }
    public Chromosome crossover(Chromosome other){
        Chromosome child = new Chromosome();
        int itemTracker = 0;
        while(child.size() < other.size()){
            if(rng.nextBoolean()){
                child.add(new Item(this.get(itemTracker)));
            }
            else {
                child.add(new Item(other.get(itemTracker)));
            }
            itemTracker++;
        }
        return child;
    }
    public void mutate(){
        for(Item s : this){
            if(rng.nextInt(10) == 0){
                s.setIncluded(!s.isIncluded());
            }
            //for everything item in this chromosome, it has a 10% chance to flip the
            // bool to whatever it is not currently set to
        }
    }
    public int getFitness(){
        int totalValue = 0;
        double totalWeight = 0;
        //I originally had this as an Int and spent at least an hour trying to find the bug >:(
        for(Item s : this){
            if(s.isIncluded()){
                totalValue += s.getValue();
                totalWeight += s.getWeight();
            }
        }
        if(totalWeight > 10){
            return 0;
        }
        return totalValue;
        //sums the price and weight values for all included items and returns value if weight is less than 10
    }
    public int compareTo(Chromosome other){
        return Integer.compare(other.getFitness(), this.getFitness());
        //this should be a compressed version of the if, else if, and else return statements
        //IDE recommends this instead
    }
    public String toString(){
        StringBuilder returnString = new StringBuilder();
        for(Item s : this){
            if(s.isIncluded()){
                returnString.append(s).append(", ");
            }
        }
        returnString.append("The value is ").append(getFitness());
        return returnString.toString();
        //returns a string with all included items in the Chromosome, as well as the value
    }
}
