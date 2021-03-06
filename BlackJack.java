package Fproject;


import java.util.Scanner;

public class BlackJack {


    public static void main(String[] args) {


        int money;          // Amount of money the user has.
        int bet;            // Amount user bets on a game.
        boolean userWins;   // Did the user win the game?

        System.out.println("Welcome to the game of blackjack.");

        money = 100;  // User starts with $100.

        while (true) {
            System.out.println("You have " + money + " dollars.");
            do {
                System.out.println("How much you want to bet?");
                System.out.print("I want to bet: ");
                Scanner sc = new Scanner(System.in);
                int mbet = sc.nextInt();
                bet = mbet;
                if (bet < 0 || bet > money)
                    System.out.println("Your answer must be between 0 and " + money + '.');
            } while (bet < 0 || bet > money);
            if (bet == 0)
                break;
            userWins = playBlackjack();
            if (userWins)
                money = money + bet;
            else
                money = money - bet;

            if (money == 0) {
                System.out.println("Looks like you've are out of money!");
                break;
            }
        }

        System.out.println("You leave with $" + money + '.');

    } // end main()


    static boolean playBlackjack() {
        // Let the user play one game of Blackjack.
        // Return true if the user wins, false if the user loses.

        Deck deck;                  // A deck of cards.  A new deck for each game.
        BlackjackHand dealerHand;   // The dealer's hand.
        BlackjackHand userHand;     // The user's hand.

        deck = new Deck();
        dealerHand = new BlackjackHand();
        userHand = new BlackjackHand();

        /*  Shuffle the deck, then deal two cards to each player. */

        deck.shuffle();
        dealerHand.addCard( deck.dealCard() );
        dealerHand.addCard( deck.dealCard() );
        userHand.addCard( deck.dealCard() );
        userHand.addCard( deck.dealCard() );



          /* Check if one of the players has Blackjack (two cards totaling to 21).
             The player with Blackjack wins the game.  Dealer wins ties.
          */

        if (dealerHand.getBlackjackValue() == 21) {
            System.out.println("Dealer has the " + dealerHand.getCard(0)
                    + " and the " + dealerHand.getCard(1) + ".");
            System.out.println("User has the " + userHand.getCard(0)
                    + " and the " + userHand.getCard(1) + ".");

            System.out.println("Dealer has Blackjack.  Dealer wins.");
            return false;
        }

        if (userHand.getBlackjackValue() == 21) {
            System.out.println("Dealer has the " + dealerHand.getCard(0)
                    + " and the " + dealerHand.getCard(1) + ".");
            System.out.println("User has the " + userHand.getCard(0)
                    + " and the " + userHand.getCard(1) + ".");

            System.out.println("You have Blackjack.  You win.");
            return true;
        }

          /*  If neither player has Blackjack, play the game.  First the user
              gets a chance to draw cards (i.e., to "Hit").  The while loop ends
              when the user chooses to "Stand".  If the user goes over 21,
              the user loses immediately.
          */

        while (true) {

            /* Display user's cards, and let user decide to Hit or Stand. */


            System.out.println("Your cards are:");
            for ( int i = 0; i < userHand.getCardCount(); i++ )
                System.out.println(("    " + userHand.getCard(i)));
            System.out.println("Your total is " + userHand.getBlackjackValue());

            System.out.println("Dealer is showing the " + dealerHand.getCard(0));

            System.out.println("Hit (H) or Stand (S)? ");
            Scanner userAction = new Scanner(System.in);
            char hitOrStand = userAction.next(".").charAt(0);
              // User's response, 'H' or 'S'.
            do {

                if (hitOrStand != 'H' && hitOrStand != 'S')
                    System.out.println("Please respond H or S:  ");
            } while (hitOrStand != 'H' && hitOrStand != 'S');

               /* If the user Hits, the user gets a card.  If the user Stands,
                  the loop ends (and it's the dealer's turn to draw cards).
               */

            if ( hitOrStand == 'S' ) {
                // Loop ends; user is done taking cards.
                break;
            }
            else {  // userAction is 'H'.  Give the user a card.
                // If the user goes over 21, the user loses.
                Card newCard = deck.dealCard();
                userHand.addCard(newCard);

                System.out.println("User hits.");
                System.out.println("Your card is the " + newCard);
                System.out.println("Your total is now " + userHand.getBlackjackValue());
                if (userHand.getBlackjackValue() > 21) {

                    System.out.println("You busted by going over 21.  You lose.");
                    System.out.println("Dealer's other card was the "
                            + dealerHand.getCard(1));
                    return false;
                }
            }

        } // end while loop

          /* If we get to this point, the user has Stood with 21 or less.  Now, it's
             the dealer's chance to draw.  Dealer draws cards until the dealer's
             total is > 16.  If dealer goes over 21, the dealer loses.
          */


        System.out.println("User stands.");
        System.out.println("Dealer's cards are");
        System.out.println("    " + dealerHand.getCard(0));
        System.out.println("    " + dealerHand.getCard(1));
        while (dealerHand.getBlackjackValue() <= 16) {
            Card newCard = deck.dealCard();
            System.out.println("Dealer hits and gets the " + newCard);
            dealerHand.addCard(newCard);
            if (dealerHand.getBlackjackValue() > 21) {

                System.out.println("Dealer busted by going over 21.  You win.");
                return true;
            }
        }
        System.out.println("Dealer's total is " + dealerHand.getBlackjackValue());

          /* If we get to this point, both players have 21 or less.  We
             can determine the winner by comparing the values of their hands. */


        if (dealerHand.getBlackjackValue() == userHand.getBlackjackValue()) {
            System.out.println("Dealer wins on a tie.  You lose.");
            return false;
        }
        else if (dealerHand.getBlackjackValue() > userHand.getBlackjackValue()) {
            System.out.println("Dealer wins, " + dealerHand.getBlackjackValue()
                    + " points to " + userHand.getBlackjackValue() + ".");
            return false;
        }
        else {
            System.out.println("You win, " + userHand.getBlackjackValue()
                    + " points to " + dealerHand.getBlackjackValue() + ".");
            return true;
        }

    }  // end playBlackjack()


} // end class Blackjack
