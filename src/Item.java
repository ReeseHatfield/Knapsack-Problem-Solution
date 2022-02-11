public class Item {
    private final String name;
    private final double weight;
    private final int value;
    private boolean included;

    public Item(String name, double weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.included = false;
    }
    public Item(Item other){
        this.name = other.name;
        this.weight = other.weight;
        this.value = other.value;
        this.included = other.included;
        //this constructor sets the item to the exact stats of the other, making a copy
    }

    public double getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public boolean isIncluded() {
        return included;
    }

    public void setIncluded(boolean included) {
        this.included = included;
    }

    @Override
    public String toString() {
        return name + " " + "(" + weight + " lbs. $" + value + ")";
        //toString returns a string with all information about the item
    }
}
