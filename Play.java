import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

class Play {

    static String[] pickedCards = new String[52];
    static String[] cards = {"1H", "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "DH", "JH", "QH", "KH", "1S", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "DS", "JS", "QS", "KS", "1C", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "DC", "JC", "QC", "KC", "1D", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "DD", "JD", "QD", "KD"};
    static int[] score = new int[2];
    static Scanner entree = new Scanner(System.in);
    static String maskedCard;

    public static void main(String[] args) {

        

        System.out.println("Bonjour, bienvenue au VAW (Vegas Always Wins).");
        System.out.println("Vous prenez place à la table de Black Jack. Des joueurs vous lancent des regards menaçants.");
        System.out.println("Le jeu commence. Jean-Claude, votre dealer, vous donne votre première carte.");
        
        String card = cardsDealing(0);
        System.out.println("Vous avez tiré " + completeCard(card) + ".");

        card = cardsDealing(0);
        System.out.println("Vous avez tiré " + completeCard(card) + ".");

        System.out.println("Votre score est de " + score[0] + " points !");
        
        card = cardsDealing(1);
        System.out.println("Jean-Claude a tiré " + completeCard(card) + ".");
        System.out.println("Son score est de " + score[1] + " points !");
        maskedCard = cardsDealing(1);
        System.out.println("Jean-Claude a tiré une carte qui restera face cachée.");
        boolean keepGoing = false;

        if (score[0] == 21) {
            keepGoing = dealerPlay();
        }

        while(keepGoing == false) {
            System.out.println("Voulez vous tirer une autre carte ? (O/N)");
            String answer = entree.next().toLowerCase();
            if(answer.equals("o")) {
                
                card = cardsDealing(0);
                System.out.println("Vous avez tiré " + completeCard(card) + ".");
                System.out.println("Votre score est de " + score[0] + " points !");
                
                if(score[0]==21)
                {
                   keepGoing = dealerPlay();
                }
                else if(score[0]>21) {
                    System.out.println("Vous avez perdu !");
                    keepGoing = true;
                }
            }
            else if(answer.equals("n")) {
              keepGoing = dealerPlay();
            }
            else
            {
                System.out.println("Je crois que vous n'avez pas bien compris");
            }
        }

    }


    public static String cardsDealing(int player) {
        
        
        Random random = new Random();
        int randomIndex;
        boolean check = false;
        String selectedCard = "";
        
        while (check == false) {
            randomIndex = random.nextInt(52);
            selectedCard = cards[randomIndex];
            check = cardCheck(selectedCard, randomIndex);
        }
        
        score(selectedCard, player);
        return selectedCard;
    }


    public static boolean cardCheck(String card, int randomIndex) {
        
        
        if (card.equals(pickedCards[randomIndex])) {
            return false;
        }
        else {
            pickedCards[randomIndex] = card;
            return true;
        }

    }

    public static void score(String card, int player) {
        switch(card.charAt(0)){
            case '1':
                ace(player);
                break;
            case '2':
                score[player] += 2;
                break;
            case '3':
                score[player] += 3;
                break;
            case '4':
                score[player] += 4;
                break;
            case '5':
                score[player] += 5;
                break;
            case '6':
                score[player] += 6;
                break;
            case '7':
                score[player] += 7;
                break;
            case '8':
                score[player] += 8;
                break;
            case '9':
                score[player] += 9;
                break;
            case 'D':
                score[player] += 10;
                break;
            case 'J':
                score[player] += 10;
                break;
            case 'Q':
                score[player] += 10;
                break;
            case 'K':
                score[player] += 10;
                break;
        }
    }

    public static void ace(int player) {

        if (player == 1) {
            if (score[1] <= 10) {
                score[1] += 11;
            }
            else {
                score[1] += 1;
            }
        }
        else {
            System.out.println("Vous avez tiré un as, souhaitez-vous compter 1 ou 11 points ?");
            String choix = entree.next();
            while(!choix.equals("1") && !choix.equals("11")) {
                System.out.println("N'essayez pas de tricher !");
                choix = entree.next();
            }
            score[0] += Integer.valueOf(choix);
        }
    }

    public static boolean dealerPlay() {
        String card = "";
        System.out.println("Jean Claude a retourné sa deuxième carte, " + completeCard(maskedCard) + ", son score est de " + score[1] + " points");
        while(score[1]<17)
        {
            System.out.println("\nJean-Claude réfléchit...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            card = cardsDealing(1);
            System.out.println("Jean Claude a tiré une autre carte, " + completeCard(card) + ", son nouveau score est de " + score[1] + " points");
        }
        if(score[1]>21) {
            System.out.println("Jean Claude a perdu, donc vous avez gagné veinard !!!");
            return true;
        }
        else{
            if(score[1] > score[0]) {
                System.out.println("Vous avez perdu");
                return true;
            }
            else if(score[0] == score[1]) {
                System.out.println("Vous avez tous les deux perdus");
                return true;
            }
            else {
                System.out.println("Vous avez gagné, Jean Claude est dégouté");
                return true;
            }

        }
    }

    public static String completeCard(String card) {

        String cardNumber = "";
        String cardColor = "";

        switch (card.charAt(0)) {
            case '1':
                cardNumber = "l'As";
                break;
            case 'D':
                cardNumber = "le 10";
                break;
            case 'J':
                cardNumber = "le Valet";
                break;
            case 'Q':
                cardNumber = "la Reine";
                break;
            case 'K':
                cardNumber = "le Roi";
                break;
            default :
                cardNumber = "le " + String.valueOf(card.charAt(0));
        }

        switch (card.charAt(1)) {
            case 'H':
                cardColor = " de coeur";
                break;
            case 'D':
                cardColor = " de carreau";
                break;
            case 'C':
                cardColor = " de trèfle";
                break;
            case 'S':
                cardColor = " de pique";
                break;
        }

        String completeCard = cardNumber + cardColor;

        return completeCard;
    }
}