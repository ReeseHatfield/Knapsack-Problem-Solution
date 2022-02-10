import java.util.ArrayList;
import java.util.Random;
//Is this an ArrayList of type Boolean?
public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome> {
    private static Random rng = new Random();
    public Chromosome(){
        //no arg constructor can be empty
        //should be used in the crossover when a child is created
    }
    public Chromosome(ArrayList<Item> items){
        for(int i = 0; i < items.size(); i++){
            //items.get(i).setIncluded(rng.nextBoolean());
            this.add(new Item(items.get(i)));
        }
        for(Item s : this){
            s.setIncluded(rng.nextBoolean());
        }


        //Adds	a copy	of	each	of	the	items	passed	in	to	this	Chromosome.	Uses	a	random
        //number	to	decide	whether	each	item’s	included	field	is	set	to	true	or	false.
//        int tracker = rng.nextInt(items.size());
//        ArrayList<Integer> usedInts = new ArrayList<>();
//        int size = 0;
//        while(size < items.size()){
//            if(!usedInts.contains(tracker)){
//                usedInts.add(tracker);
//                items.get(tracker).setIncluded(rng.nextBoolean());
//                this.add(new Item(items.get(tracker)));//thanks Dr. Cheatham
//                size++;
//            }
//            tracker = rng.nextInt(items.size());
//        }
    }
    public Chromosome crossover(Chromosome other){
        Chromosome child = new Chromosome();
        int itemTracker = 0;
        while(child.size() < other.size()){
            if(rng.nextBoolean()){
                child.add(this.get(itemTracker));
            }
            else {
                child.add(other.get(itemTracker));
            }
            itemTracker++;
        }
        return child;
        //Creates	and	returns	a	new	child	chromosome	by	performing	the	crossover
        //operation	on	this	chromosome	and	the	other	one	that	is	passed	in	(i.e.	for	each	item,	use
        //a	random	number	to	decide	which	parent’s	item	should	be	copied	and	added	to	the	child).

    }
    public void mutate(){
        for(Item s : this){
            if(rng.nextBoolean()){
                s.setIncluded(!s.isIncluded());//flips the bool
            }
        }
        //Performs	the	mutation	operation	on	this	chromosome	(i.e.	for	each	item	in	this
        //chromosome,	use	a	random	number	to	decide	whether		to	slip	it&rsquo;s	included	field	from
        //true	to	false	or	vice	versa).
    }
    public int getFitness(){
        int totalWeight = 0;
        int totalValue = 0;
        for(Item s : this){
            if(s.isIncluded()){
                totalValue += s.getValue();
                totalWeight += s.getWeight();
            }

        }
        if(totalWeight < 10){
            return totalValue;
        }
        else {
            return 0;
        }
    }
    public int compareTo(Chromosome other){
        return Integer.compare(other.getFitness(), this.getFitness());
        //this should be a compressed version of the if, else if, and else return statements
    }
    public String toString(){
        StringBuilder returnString = new StringBuilder();
        for(Item s : this){
            if(s.isIncluded()){
                returnString.append("T ");
            }
            else {
                returnString.append("F ");
            }
        }
        returnString.append("The value is ").append(getFitness());
        return returnString.toString();
    }
}
