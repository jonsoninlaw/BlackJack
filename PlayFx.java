import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.text.DecimalFormat;

public class PlayFx extends Application {

    static String[] pickedCards = new String[52];
    static int nbCards = 0;
    static String[] cards = {"1H", "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "DH", "JH", "QH", "KH", "1S", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "DS", "JS", "QS", "KS", "1C", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "DC", "JC", "QC", "KC", "1D", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "DD", "JD", "QD", "KD"};
    static int[] score = new int[2];
    static Scanner entree = new Scanner(System.in);
    static String maskedCard;
    static boolean keepGoing = false;
    static String card;

    static TextArea mainField = new TextArea();
    static Label playerHand = new Label();
    static TextArea playerField = new TextArea();
    static Label dealerHand = new Label();
    static TextArea dealerField = new TextArea();
    static TextArea message = new TextArea();
    static Button oui = new Button();
    static Button non = new Button();
    static Button one = new Button();
    static Button eleven = new Button();
    static Button restart = new Button();
    static Button exit = new Button();

    public static void main(String[] args) {

        launch(args);
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
        
        
        if (nbCards >= 52) {
            mainField.appendText("\nIl n'y a plus de cartes ! Le jeu a été remélangé.");
            Arrays.fill(pickedCards, null);
            nbCards = 0;
            return true;
        }
        else if (card.equals(pickedCards[randomIndex])) {
            return false;
        }
        else if (((card.equals("1C")) || card.equals("1H") || card.equals("1S") || card.equals("1D")) && nbCards <= 3) {
            return false;
        }
        else {
            pickedCards[randomIndex] = card;
            nbCards ++;
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
            message.setText("Vous avez tiré un as, souhaitez-vous compter 1 ou 11 points ?");
            oui.setVisible(false);
            non.setVisible(false);
            one.setVisible(true);
            eleven.setVisible(true);
        }
    }


    public static void oneEleven(int oneEleven) {

        score[0] += oneEleven;

        if (score[0] == 21) {
            oui.setVisible(false);
            non.setVisible(false);
            keepGoing = dealerPlay();
        }
        else {
            playerField.appendText("\nVotre score est de " + score[0] + " points !");
            message.setText("\nVoulez-vous tirer une autre carte ?");
            one.setVisible(false);
            eleven.setVisible(false);
            oui.setVisible(true);
            non.setVisible(true);
        }
    }


    public static void keepGoing(boolean answer) {

        String completeCard;
        if (answer) {
            String card = cardsDealing(0);
            completeCard = completeCard(card);
            playerField.appendText("\nVous avez tiré " + completeCard + ".");
                if (completeCard.equals("l'As de carreau") || completeCard.equals("l'As de coeur") || completeCard.equals("l'As de trèfle") || completeCard.equals("l'As de pique")) {
                }
                else {
                    playerField.appendText("\nVotre score est de " + score[0] + " points !");
                }
            if(score[0]==21) {
                oui.setVisible(false);
                non.setVisible(false);
                keepGoing = dealerPlay();
            }
            else if(score[0]>21) {
                message.setText("\nVous avez perdu !");
                keepGoing = true;
                oui.setVisible(false);
                non.setVisible(false);
                restart.setVisible(true);
            }
            else {
                if (completeCard.equals("l'As de carreau") || completeCard.equals("l'As de coeur") || completeCard.equals("l'As de trèfle") || completeCard.equals("l'As de pique")) {
                    message.setText("\nSouhaitez-vous compter 1 ou 11 points ?");
                }
                else message.setText("\nVoulez-vous tirer une autre carte ?");
            }
        }
        else {
            oui.setVisible(false);
            non.setVisible(false);
            keepGoing = dealerPlay();
        }
    }


    public static boolean dealerPlay() {
        String card = "";
        dealerField.appendText("\nJean Claude a retourné sa deuxième carte, " + completeCard(maskedCard) + ", son score est de " + score[1] + " points");
        while(score[1]<17)
        {
            dealerField.appendText("\nJean-Claude réfléchit...");
            card = cardsDealing(1);
            dealerField.appendText("\nJean Claude a tiré une autre carte, " + completeCard(card) + ", son nouveau score est de " + score[1] + " points");
        }
        if(score[1]>21) {
            message.setText("\nJean Claude a perdu, donc vous avez gagné !");
            restart.setVisible(true);
            return true;
        }
        else{
            if(score[1] > score[0]) {
                message.setText("\nVous avez perdu !");
                restart.setVisible(true);
                return true;
            }
            else if(score[0] == score[1]) {
                message.setText("\nVous avez tous les deux perdus, imbéciles !");
                restart.setVisible(true);
                return true;
            }
            else {
                message.setText("\nVous avez gagné, Jean Claude est dégouté !");
                restart.setVisible(true);
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


    public static void restart() {

        mainField.clear();
        playerField.clear();
        dealerField.clear();
        score[0] = 0;
        score[1] = 0;
        gameStart();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane root = new GridPane();
        root.setPadding(new Insets(72));
        root.setHgap(15);
        root.setVgap(15);
        
        Scene scene = new Scene(root, 1100, 650);
        primaryStage.setTitle("Vegas Always Wins");
        primaryStage.setScene(scene);
        primaryStage.show();

        mainField.setEditable(false);
        mainField.setPrefWidth(1000);
        root.add(mainField, 1, 0, 8, 1);

        playerHand.setText("  Votre main :");
        root.add(playerHand, 1, 1, 4, 1);
        playerHand.setStyle("-fx-font-weight: bold;");
        
        playerField.setEditable(false);
        playerField.setPrefWidth(1000);
        root.add(playerField, 1, 2, 8, 1);
        
        dealerHand.setText("  La main de Jean-Claude :");
        root.add(dealerHand, 1, 3, 4, 1);
        dealerHand.setStyle("-fx-font-weight: bold;");
        
        dealerField.setEditable(false);
        dealerField.setPrefWidth(1000);
        root.add(dealerField, 1, 4, 8, 1);
        
        message.setEditable(false);
        message.setPrefWidth(1000);
        message.setPrefRowCount(1);
        root.add(message, 1, 5, 4, 1);
        message.setStyle("-fx-font-weight: bold;");
        
        oui.setText("Oui");
        root.add(oui, 5, 5);
        oui.setStyle("-fx-font-weight: bold;");
        
        non.setText("Non");
        root.add(non, 6, 5);
        non.setStyle("-fx-font-weight: bold;");

        one.setText("1 point");
        root.add(one, 7, 5);
        one.setStyle("-fx-font-weight: bold;");
        one.setVisible(false);

        eleven.setText("11 points");
        root.add(eleven, 8, 5);
        eleven.setStyle("-fx-font-weight: bold;");
        eleven.setVisible(false);
        
        restart.setText("Recommencer");
        root.add(restart, 1, 6);
        restart.setStyle("-fx-font-weight: bold;");
        restart.setVisible(false);
        restart.setOnAction(event -> restart());
        
        exit.setText("Quitter");
        root.add(exit, 1, 7);
        exit.setStyle("-fx-font-weight: bold;");
        exit.setOnAction(event -> exit());

        oui.setOnAction(event -> keepGoing(true));
        non.setOnAction(event -> keepGoing(false));

        one.setOnAction(even -> oneEleven(1));
        eleven.setOnAction(even -> oneEleven(11));

        gameStart();
    }


    public static void gameStart() {

        mainField.setText("Bonjour, bienvenue au VAW (Vegas Always Wins).");
        mainField.appendText("\nVous prenez place à la table de Black Jack. Des joueurs vous lancent des regards menaçants.");
        mainField.appendText("\nLe jeu commence. Jean-Claude, votre dealer, distribue les cartes.");

        String card = cardsDealing(0);
        playerField.setText("Vous avez tiré " + completeCard(card) + ".");
        card = cardsDealing(0);
        playerField.appendText("\nVous avez tiré " + completeCard(card) + ".");
        playerField.appendText("\nVotre score est de " + score[0] + " points !");

        card = cardsDealing(1);
        dealerField.setText("Jean-Claude a tiré " + completeCard(card) + ".");
        dealerField.appendText("\nSon score est de " + score[1] + " points !");
        maskedCard = cardsDealing(1);
        dealerField.appendText("\nJean-Claude a tiré une carte qui restera face cachée.");

        if (score[0] == 21) {
            keepGoing = dealerPlay();
        }

        message.setText("\nVoulez vous tirer une autre carte ?");
        oui.setVisible(true);
        non.setVisible(true);
        one.setVisible(false);
        eleven.setVisible(false);
        restart.setVisible(false);
    }

    
    public static void exit() {
        System.exit(0);
    }
}