package dev.danielallison.cardguessinggame;

import java.io.*;
import java.util.*;

/**
 * Entry point for the card guessing game application.
 */
public class App {

    /**
     * Main method to run the card guessing game.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to the card guessing game!");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("What is your name?:");

            String playerName = null;
            try {
                playerName = br.readLine();
            } catch (IOException e) {
                System.err.println("Error reading input: " + e.getMessage());
                System.exit(1);
            }

            if (playerName == null || playerName.isBlank()) {
                System.out.println("No name entered - exiting.");
                System.exit(1);
            }

            Domain.Player player = new Domain.Player(playerName);

            Domain.Deck deck = new Domain.Deck();
            player.receive(deck.drawRandom());
            player.receive(deck.drawRandom());

            System.out.println("You've been dealt two cards. Which is higher? [first/second]:");

            String guess = null;
            try {
                guess = br.readLine();
            } catch (IOException e) {
                System.err.println("Error reading input: " + e.getMessage());
                System.exit(1);
            }

            if (guess == null || guess.isBlank()) {
                System.out.println("No guess entered - exiting.");
                System.exit(1);
            }

            // Validate input. Valid guesses are first and second.
            if (!"first".equalsIgnoreCase(guess) && !"second".equalsIgnoreCase(guess)) {
                System.out.println("Invalid choice – exiting.");
                System.exit(1);
            }

            if (guess == "first") {
                if (player.guess(0) == true) {
                    System.out.println("Congrats! You guessed correctly!");
                } else {
                    System.out.println("You guessed incorrectly.");
                }
            } else {
                // Implicit guess second case
                if (player.guess(1) == true) {
                    System.out.println("Congrats! You guessed correctly!");
                } else {
                    System.out.println("You guessed incorrectly.");
                }
            }

            System.out.println("Let's reveal the cards.");
            System.out.println(
                    "First card: "
                            + player.hand().cards().get(0).getRank()
                            + "-"
                            + player.hand().cards().get(0).getSuit());
            System.out.println(
                    "Second card: "
                            + player.hand().cards().get(1).getRank()
                            + "-"
                            + player.hand().cards().get(1).getSuit());

            System.out.println("Play again? [yes/no(default)]:");
            try {
                String playAgain = br.readLine();
                if (!"yes".equalsIgnoreCase(playAgain)) {
                    System.out.println("Thanks for playing!");
                    System.exit(0); // normal termination
                }
            } catch (IOException e) {
                System.err.println("Error reading input: " + e.getMessage());
                System.exit(1);
            }
        }
    }
}

/**
 * The Domain class contains the business logic for the card guessing game.
 * It describes playing cards, their operations, and their order according to this game.
 */
class Domain {
    /**
     * Represents a playing card with a suit and rank.
     * Playing cards are immutable.
     */
    public static class Card implements Comparable<Card> {
        /**
         * Constructs a new card.
         * @param suit The suit of the card.
         * @param rank The rank of the card.
         */
        public Card(Suit suit, Rank rank) {
            this.suit = suit;
            this.rank = rank;
        }

        /**
         * Enumeration of card suits with custom order.
         */
        public enum Suit {
            SPADE(4),
            HEART(3),
            DIAMOND(2),
            CLUB(1);

            private int value;

            /**
             * Constructs a suit with a specific order value.
             * @param value The integer value of the suit. Spades(4) > Hearts(3) > Diamonds(2) > Clubs(1)
             */
            private Suit(int value) {
                this.value = value;
            }

            /**
             * Returns the integer value of the suit for comparison.
             * @return The suit's value.
             */
            public int getValue() {
                return this.value;
            }

            /**
             * Returns the string representation of the suit.
             * @return The suit as a string.
             */
            public String getString() {
                switch (this.value) {
                    case 4:
                        return "SPADE";
                    case 3:
                        return "HEART";
                    case 2:
                        return "DIAMOND";
                    case 1:
                        return "CLUB";
                    default:
                        throw new IllegalStateException("Unexpected value: " + this.value);
                }
            }
        }

        /**
         * Enumeration of card ranks with custom order.
         */
        public enum Rank {
            TWO(2),
            THREE(3),
            FOUR(4),
            FIVE(5),
            SIX(6),
            SEVEN(7),
            EIGHT(8),
            NINE(9),
            TEN(10),
            JACK(11),
            QUEEN(12),
            KING(13),
            ACE(14);

            private int value;

            /**
             * Constructs a rank with a specific value.
             * @param value The integer value of the rank. Aces are high.
             */
            private Rank(int value) {
                this.value = value;
            }

            /**
             * Returns the integer value of the rank for comparison.
             * @return The rank's value.
             */
            public int getValue() {
                return this.value;
            }

            /**
             * Returns the string representation of the rank.
             * @return The rank as a string.
             */
            public String getString() {
                switch (this.value) {
                    case 2:
                        return "TWO";
                    case 3:
                        return "THREE";
                    case 4:
                        return "FOUR";
                    case 5:
                        return "FIVE";
                    case 6:
                        return "SIX";
                    case 7:
                        return "SEVEN";
                    case 8:
                        return "EIGHT";
                    case 9:
                        return "NINE";
                    case 10:
                        return "TEN";
                    case 11:
                        return "JACK";
                    case 12:
                        return "QUEEN";
                    case 13:
                        return "KING";
                    case 14:
                        return "ACE";
                    default:
                        throw new IllegalStateException("Unexpected value: " + this.value);
                }
            }
        }

        private final Rank rank;
        private final Suit suit;

        /**
         * Returns the suit of the card as a string.
         * @return The suit.
         */
        public String getSuit() {
            return this.suit.getString();
        }

        /**
         * Returns the rank of the card as a string.
         * @return The rank.
         */
        public String getRank() {
            return this.rank.getString();
        }

        /**
         * Compares this card to another card.
         * @param card A Card to compare to.
         * @return Returns 1 if this is higher than the other card, 0 if equal, -1 if lower.
         */
        @Override
        public int compareTo(Card card) {
            if (this.suit != card.suit) {
                return Integer.compare(this.suit.getValue(), card.suit.getValue());
            } else if (this.rank != card.rank) {
                return Integer.compare(this.rank.getValue(), card.rank.getValue());
            } else {
                return 0;
            }
        }

        /**
         * Checks if this card is equal to another object.
         * @param obj The object to compare.
         * @return True if equal, false otherwise.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Card other = (Card) obj;
            return this.compareTo(other) == 0;
        }
    }

    /**
     * Represents a deck of playing cards.
     */
    public static class Deck {
        private List<Card> cards = new ArrayList<>(52);
        private Random rng;

        /**
         * Constructs a new deck with a default random number generator.
         */
        public Deck() {
            this(new Random());
        }

        /**
         * Constructs a new deck with a specified random number generator.
         * @param rng A random number generator.
         */
        public Deck(Random rng) {
            this.rng = Objects.requireNonNull(rng);
            for (Card.Suit suit : Card.Suit.values()) {
                for (Card.Rank rank : Card.Rank.values()) {
                    Card tmp = new Card(suit, rank);
                    this.cards.add(tmp);
                }
            }
            assert cards.size() == 52;
        }

        /**
         * Returns an unmodifiable list of cards in the deck.
         * @return The list of cards.
         */
        public List<Card> cards() {
            return Collections.unmodifiableList(cards);
        }

        /**
         * Shuffles the cards in the deck.
         */
        public void shuffle() {
            Collections.shuffle(cards);
        }

        /**
         * Draws a card from the deck at the specified index.
         * Removes the card from the deck.
         * @param index The index of the card to draw.
         * @return The drawn card.
         * @throws NoSuchElementException if the deck is empty.
         */
        public Card draw(int index) {
            if (cards.isEmpty()) {
                throw new NoSuchElementException("Deck is empty");
            }
            return this.cards.remove(index);
        }

        /**
         * Draws a card from the top of the deck (index 0).
         * Removes the card from the deck.
         * @return The drawn card.
         */
        public Card draw() {
            return this.draw(0);
        }

        /**
         * Draws a random card from the deck.
         * Removes the card from the deck.
         * @return The drawn card.
         */
        public Card drawRandom() {
            return draw(this.rng.nextInt(cards.size()));
        }
    }

    /**
     * Represents a hand of playing cards.
     * Immutable.
     */
    public static class Hand {
        private List<Card> cards = new ArrayList<>();

        /**
         * Constructs a hand with the specified cards.
         * @param cards The list of cards.
         */
        public Hand(List<Card> cards) {
            this.cards = Collections.unmodifiableList(Objects.requireNonNull(cards));
        }

        /**
         * Returns the list of cards in the hand.
         * @return The list of cards.
         */
        public List<Card> cards() {
            return cards;
        }

        /**
         * Returns an empty hand.
         * @return An empty hand.
         */
        public static Hand empty() {
            return new Hand(Collections.emptyList());
        }

        /**
         * Returns a new hand with the specified card added.
         * @param card The card to add.
         * @return The new hand.
         */
        public Hand add(Card card) {
            List<Card> newCards = new java.util.ArrayList<>(cards);
            newCards.add(card);
            return new Hand(newCards);
        }

        /**
         * Returns a new hand with the specified card removed.
         * @param card The card to remove.
         * @return The new hand.
         */
        public Hand remove(Card card) {
            List<Card> newCards = new ArrayList<>(cards);
            newCards.remove(card);
            return new Hand(newCards);
        }

        /**
         * Checks if this hand is equal to another object.
         * @param o The object to compare.
         * @return True if equal, false otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Hand)) return false;
            Hand other = (Hand) o;
            return cards.equals(other.cards);
        }
    }

    /**
     * Represents a player in the card guessing game.
     */
    public static class Player {
        private final UUID id;
        private final String name;
        private Hand hand = Hand.empty();

        /**
         * Constructs a player with the specified name.
         * @param name The player's name.
         */
        public Player(String name) {
            this.id = UUID.randomUUID();
            this.name = name;
        }

        /**
         * Returns the player's unique identifier.
         * @return The player's UUID.
         */
        public UUID id() {
            return id;
        }

        /**
         * Returns the player's name.
         * @return The player's name.
         */
        public String name() {
            return name;
        }

        /**
         * Returns the player's hand.
         * @return The player's hand.
         */
        public Hand hand() {
            return hand;
        }

        /**
         * Receives a card into this player's hand.
         * @param card The card to receive.
         */
        public void receive(Card card) {
            hand = hand.add(card);
        }

        /**
         * Discards a card from this player's hand.
         * @param card The card to discard.
         */
        public void discard(Card card) {
            hand = hand.remove(card);
        }

        /**
         * Checks if the guessed card is the highest in the player's hand.
         * @param handIndexLargestCard The index of the guessed card.
         * @return True if the guessed card is the highest, false otherwise.
         */
        public boolean guess(int handIndexLargestCard) {
            Card guessCard = hand.cards().get(handIndexLargestCard);
            for (Card card : hand.cards()) {
                if (guessCard == card) continue;
                int compareResult = guessCard.compareTo(card);
                if (compareResult <= 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
