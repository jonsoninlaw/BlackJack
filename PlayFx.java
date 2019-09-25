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
    static String[] cards = {"1H", "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "DH", "JH", "QH", "KH", "1S", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "DS", "JS", "QS", "KS", "1C", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "DC", "JC", "QC", "KC", "1D", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "DD", "JD", "QD", "KD"};
    static int[] score = new int[2];
    static Scanner entree = new Scanner(System.in);
    static String maskedCard;
    static boolean keepGoing = false;
    static String card;

    public static void main(String[] args) {

        launch(args);
    }


    public static String cardsDealing(int player, TextArea field) {
        
        
        Random random = new Random();
        int randomIndex;
        boolean check = false;
        String selectedCard = "";
        
        while (check == false) {
            randomIndex = random.nextInt(52);
            selectedCard = cards[randomIndex];
            check = cardCheck(selectedCard, randomIndex);
        }
        
        score(selectedCard, player, field);
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

    public static void score(String card, int player, TextArea field) {
        switch(card.charAt(0)){
            case '1':
                ace(player, field);
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

    public static void ace(int player, TextArea field) {

        if (player == 1) {
            if (score[1] <= 10) {
                score[1] += 11;
            }
            else {
                score[1] += 1;
            }
        }
        else {
            field.appendText("Vous avez tiré un as, souhaitez-vous compter 1 ou 11 points ?");
            // String choix = entree.next();
            String choix = "11";
            while(!choix.equals("1") && !choix.equals("11")) {
                field.appendText("N'essayez pas de tricher !");
                choix = entree.next();
            }
            score[0] += Integer.valueOf(choix);
        }
    }

    public static void keepGoing(TextArea mainField, TextArea playerField, TextArea dealerField, boolean answer, Button oui, Button non, Button restart, TextArea message) {

        if (answer) {
            String card = cardsDealing(0, playerField);
            playerField.appendText("\nVous avez tiré " + completeCard(card) + ".");
            playerField.appendText("\nVotre score est de " + score[0] + " points !");
            
            if(score[0]==21) {
                oui.setVisible(false);
                non.setVisible(false);
                keepGoing = dealerPlay(dealerField, restart, message);
            }
            else if(score[0]>21) {
                message.setText("\nVous avez perdu !");
                keepGoing = true;
                oui.setVisible(false);
                non.setVisible(false);
                restart.setVisible(true);
            }
            else {
                message.setText("\nVoulez vous tirer une autre carte ? (O/N)");
            }
        }
        else {
            oui.setVisible(false);
            non.setVisible(false);
            keepGoing = dealerPlay(dealerField, restart, message);
        }
    }

    public static boolean dealerPlay(TextArea field, Button restart, TextArea message) {
        String card = "";
        field.appendText("\nJean Claude a retourné sa deuxième carte, " + completeCard(maskedCard) + ", son score est de " + score[1] + " points");
        while(score[1]<17)
        {
            card = cardsDealing(1, field);
            field.appendText("\nJean Claude a tiré une autre carte, " + completeCard(card) + ", son nouveau score est de " + score[1] + " points");
        }
        if(score[1]>21) {
            message.setText("\nJean Claude a perdu, donc vous avez gagné, petit veinard !!!");
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

    public static void restart(TextArea field1, TextArea field2, TextArea field3, Button restart, Button oui, Button non, TextArea message) {
        field1.clear();
        field2.clear();
        field3.clear();
        score[0] = 0;
        score[1] = 0;
        gameStart(field1, field2, field3, restart, oui, non, message);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        root.setPadding(new Insets(30));
        root.setHgap(15);
        root.setVgap(15);

        TextArea field1 = new TextArea();
        field1.setEditable(false);
        field1.setPrefWidth(1000);
        field1.setPrefRowCount(3);
        root.add(field1, 1, 0, 3, 1);

        Label playerHand = new Label();
        playerHand.setText("  Votre main :");
        root.add(playerHand, 1, 1, 3, 1);
        playerHand.setStyle("-fx-font-weight: bold;");

        TextArea field2 = new TextArea();
        field2.setEditable(false);
        field2.setPrefWidth(1000);
        field2.setPrefRowCount(8);
        root.add(field2, 1, 2, 3, 1);

        Label dealerHand = new Label();
        dealerHand.setText("  La main de Jean-Claude :");
        root.add(dealerHand, 1, 3, 3, 1);
        dealerHand.setStyle("-fx-font-weight: bold;");

        TextArea field3 = new TextArea();
        field3.setEditable(false);
        field3.setPrefWidth(1000);
        field3.setPrefRowCount(8);
        root.add(field3, 1, 4, 3, 1);

        TextArea message = new TextArea();
        message.setEditable(false);
        message.setPrefWidth(1000);
        message.setPrefRowCount(1);
        root.add(message, 1, 5, 3, 1);
        message.setStyle("-fx-font-weight: bold;");

        Button oui = new Button();
        oui.setText("Oui");
        root.add(oui, 1, 6);
        oui.setStyle("-fx-font-weight: bold;");
        
        Button non = new Button();
        non.setText("Non");
        root.add(non, 2, 6);
        non.setStyle("-fx-font-weight: bold;");

        Button restart = new Button();
        restart.setText("Recommencer");
        root.add(restart, 1, 6);
        restart.setStyle("-fx-font-weight: bold;");
        restart.setVisible(false);
        restart.setOnAction(event -> restart(field1, field2, field3, restart, oui, non, message));

        Button exit = new Button();
        exit.setText("Quitter");
        root.add(exit, 1, 7);
        exit.setStyle("-fx-font-weight: bold;");
        exit.setOnAction(event -> exit());

        oui.setOnAction(event -> keepGoing(field1, field2, field3, true, oui, non, restart, message));
        non.setOnAction(event -> keepGoing(field1, field2, field3, false, oui, non, restart, message));

        Scene scene = new Scene(root, 1100, 650);
        primaryStage.setTitle("Vegas Always Wins");
        primaryStage.setScene(scene);
        primaryStage.show();

        gameStart(field1, field2, field3, restart, oui, non, message);


    }

    public static void gameStart(TextArea field1, TextArea field2, TextArea field3, Button restart, Button oui, Button non, TextArea message) {

        field1.setText("Bonjour, bienvenue au VAW (Vegas Always Wins).");
        field1.appendText("\nVous prenez place à la table de Black Jack. Des joueurs vous lancent des regards menaçants.");
        field1.appendText("\nLe jeu commence. Jean-Claude, votre dealer, distribue les cartes.");

        String card = cardsDealing(0, field2);
        field2.setText("Vous avez tiré " + completeCard(card) + ".");
        card = cardsDealing(0, field2);
        field2.appendText("\nVous avez tiré " + completeCard(card) + ".");
        field2.appendText("\nVotre score est de " + score[0] + " points !");

        card = cardsDealing(1, field3);
        field3.setText("Jean-Claude a tiré " + completeCard(card) + ".");
        field3.appendText("\nSon score est de " + score[1] + " points !");
        maskedCard = cardsDealing(1, field3);
        field3.appendText("\nJean-Claude a tiré une carte qui restera face cachée.");

        if (score[0] == 21) {
            keepGoing = dealerPlay(field3, restart, message);
        }

        message.setText("\nVoulez vous tirer une autre carte ? (O/N)");
        oui.setVisible(true);
        non.setVisible(true);
        restart.setVisible(false);
    }

    public static void exit() {
        System.exit(0);
    }

}