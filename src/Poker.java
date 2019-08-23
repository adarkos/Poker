import java.util.Scanner;

public class Poker{
    //static 
    //static Card[] hand = new Card[5];
    
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        while(input.hasNextLine()){
            String line = input.nextLine();
            String[] parts = validateSep(line);
            if(parts == null){
                invalid(line);
                continue;
            } 
            Card[] hand = convert(parts);
            if(hand == null){
                invalid(line);
                continue;
            }
            Card[] newHand = duplicates(hand);
            if(newHand == null || newHand.length == 0){
                invalid(line);
                continue;
            }
            order(newHand);
        } 
    }

    public static void invalid(String line){
        System.out.println("Invalid: " + line);
    }

    public static int convertNumber(char letter){
        letter = Character.toUpperCase(letter);
        if(letter == 'K'){
            return 13;
        }
        if(letter == 'Q'){
            return 12;
        }
        if(letter == 'J'){
            return 11;
        }
        if(letter == 'A'){
            return 1;
        }
        return 0;
    }

    public static String[] validateSep(String line){
        char[] separator = {' ', '-', '/'};
        char sep = ' ';
        int sepFound = 0;
        for(int i = 0; i < line.length(); i++){
            if(line.charAt(i) == ' ' && sepFound == 0){
                sep = ' ';
            } else if(line.charAt(i) == '-' && sepFound == 0){
                sep = '-';
            } else if(line.charAt(i) == '/' && sepFound == 0){
                sep = '/';
            }
            for(int j=0; j<separator.length; j++){
                if(line.charAt(i) == separator[j] && line.charAt(i) != sep){
                    return null;
                }
            }
        }
        String[] parts = line.split(String.valueOf(sep));
        if(parts.length != 5){
            return null;
        }
        return parts;
    }

    public static Card[] convert(String[] parts){
        Card[] newHand = new Card[5]; 
        for(int i=0; i<5; i++){
            int finalNum = 0;
            char suit = parts[i].charAt(parts[i].length()-1);
            suit = Character.toUpperCase(suit);
            if(!String.valueOf(suit).matches("C|D|H|S")){
                return null;
            }
            if(parts[i].length() == 2){
                char number = parts[i].charAt(0);
                if(Character.isLetter(number)){
                    int num = convertNumber(number);
                    if(num == 0){
                        return null;
                    }
                    finalNum = num;
                } else {
                    int num = Character.getNumericValue(number);
                    if(num > 13 || num < 1){
                        return null;
                    }
                    finalNum = num;
                }
                
            } else if(parts[i].length() == 3){
                char num1 = parts[i].charAt(0);
                char num2 = parts[i].charAt(1);
                String num = "" + num1 + num2;
                if(num.matches("[0-9]+")){
                    int numSet = Integer.parseInt(num);
                    if(numSet >13 || numSet < 1){
                        return null;
                    }
                    finalNum = numSet;
                } else {
                    return null;
                }
            }
            newHand[i] = new Card();
            newHand[i].setSuit(suit);
            if(finalNum == 1){
                newHand[i].setNumber(14);
            } else {
                newHand[i].setNumber(finalNum);
            }
        }
        return newHand;
    }
    
    
    public static Card[] duplicates(Card[] newHand){
        try {
            for(int i=0; i<5; i++){
                for(int j=i + 1; j<5; j++){
                    if(newHand[i].getSuit() == newHand[j].getSuit() && newHand[i].getNumber() == newHand[j].getNumber()){
                        return null;
                    }
                }
            }
        } catch(NullPointerException e){
            return null;
        }
        return newHand;
    }

    public static void order(Card[] newHand){      
        int j, k;
        int n = newHand.length;        
        Card key;
       
        //Note: This code for insertion sort came from my implementation in cosc242
        for(j = 1; j < n; ++j){
            key = newHand[j];
            for(k = j-1; k>=0 && newHand[k].getNumber() > key.getNumber(); k--){
                newHand[k+1] = newHand[k];
            }
            newHand[k+1] = key;
        }
        
        for(j = 1; j < n; ++j){
            key = newHand[j];
            for(k = j-1; k>=0 && newHand[k].getNumber()== key.getNumber()&& newHand[k].getSuit() > key.getSuit(); k--){
                newHand[k+1] = newHand[k];
            }
            newHand[k+1] = key;
        }
        
        for (Card card : newHand) {
            output(card);
        }
        System.out.println();
    }
    
    public static void output(Card card){
        char num = 0;
        if(card.getNumber() == 14){
            num = 'A';
        } 
        else if(card.getNumber() == 13){
            num = 'K';
        } 
        else if(card.getNumber() == 12){
            num = 'Q';
        } 
        else if(card.getNumber() == 11){
            num = 'J';
        } else {
            System.out.print(card.getNumber());
        }
        if(num != 0) System.out.print(num);
        
        System.out.print(card.getSuit() + " ");
    }
}

