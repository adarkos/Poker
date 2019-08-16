public class Card {
    private int number;
    private char suit;

    public void Card(){}

    public void Card(int number, char suit){
        this.number = number;
        this.suit = suit;
    }

    public int getNumber(){
        return this.number;
    }
    
    public char getSuit(){
        return this.suit;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public void setSuit(char suit){
        this.suit = suit;
    }
}
