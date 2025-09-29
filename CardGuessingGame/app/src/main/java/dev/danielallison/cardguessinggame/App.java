package dev.danielallison.cardguessinggame;

import java.io.*;
import java.util.*;

public class App {

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
 * The Domain class contains the business logic
 * In this program, it describes playing cards, their operations, and their order according to this game
 */
class Domain {
    /**
     * A playing card has a suit and a rank
     * Playing cards should not be able to change their suit and rank
     * Static so it can be instantiated without an outer instance
     */
    public static class Card implements Comparable<Card> {
        /**
         * Constructor. Creates a new card.
         * @param suit The suit of the card.
         * @param rank The rank of the card.
         */
        public Card(Suit suit, Rank rank) {
            this.suit = suit;
            this.rank = rank;
        }

        public enum Suit {
            SPADE(4),
            HEART(3),
            DIAMOND(2),
            CLUB(1);

            private int value;

            /**
             * Constructor. In this game, suits have a order, which is represented by an integer
             * @param value The integer value of the suit. Spades(4) > Hearts(3) > Diamonds(2) > Clubs(1)
             */
            private Suit(int value) {
                this.value = value;
            }

            /**
             * Useful for comparisons
             * @return Returns the integer value of the suit
             */
            public int getValue() {
                return this.value;
            }

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
             * Rank constructor. In this game, the rank is represented by an integer
             * @param value The integer value of the rank. Aces are high.
             */
            private Rank(int value) {
                this.value = value;
            }

            /**
             * Useful for comparisons
             * @return Returns the integer value of the rank
             */
            public int getValue() {
                return this.value;
            }

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

        public String getSuit() {
            return this.suit.getString();
        }

        public String getRank() {
            return this.rank.getString();
        }

        /**
         * Cards can be compared using the compare to method override
         * @param card A Card to compare to.
         * @return Returns 1 if this is higher other card, 0 if equal, -1 if this is lower than other card
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

        @Override
        public boolean equals(Object obj) {
            // Fast‑path: same reference
            if (this == obj) return true;

            // Guard against null and wrong type
            if (obj == null || getClass() != obj.getClass()) return false;

            // Safe cast – we now know o is a Card
            Card other = (Card) obj;

            // Re‑use existing compareTo logic
            return this.compareTo(other) == 0;
        }
    }

    public static class Deck {
        private List<Card> cards = new ArrayList<>(52);
        private Random rng;

        public Deck() {
            this(new Random());
        }

        /**
         * This constructor method is available when a specific seed for the rng is desired
         * @param rng A random number generator with Random class
         */
        public Deck(Random rng) {
            this.rng = Objects.requireNonNull(rng);
            for (Card.Suit suit : Card.Suit.values()) {
                for (Card.Rank rank : Card.Rank.values()) {
                    Card tmp = new Card(suit, rank);
                    this.cards.add(tmp);
                }
            }
            // Invariant: exactly 52 distinct cards
            assert cards.size() == 52;
        }

        public List<Card> cards() {
            return Collections.unmodifiableList(cards);
        }

        /**
         * Shuffles the cards in the deck
         */
        public void shuffle() {
            Collections.shuffle(cards);
        }

        /**
         * Draws a card from the deck at specified index. Removes the card from the deck. Requires non-empty deck.
         * @param index The index determines the position of the card in the deck that will be removed
         * @return Card
         */
        public Card draw(int index) {
            if (cards.isEmpty()) {
                throw new NoSuchElementException("Deck is empty");
            }
            return this.cards.remove(index);
        }

        /**
         * Draws a card from the top of the deck, defined as index 0. Removes the card from the deck. Requires non-empty deck.
         * @return Card
         */
        public Card draw() {
            return this.draw(0);
        }

        /**
         * Draw a random card from the deck. Removes the card from the deck. Requires non-empty deck.
         * @return Card
         */
        public Card drawRandom() {
            return draw(this.rng.nextInt(cards.size()));
        }
    }

    public static class Hand {
        private List<Card> cards = new ArrayList<>();

        public Hand(List<Card> cards) {
            this.cards = Collections.unmodifiableList(Objects.requireNonNull(cards));
        }

        public List<Card> cards() {
            return cards;
        }

        public static Hand empty() {
            return new Hand(Collections.emptyList());
        }

        public Hand add(Card card) {
            List<Card> newCards = new java.util.ArrayList<>(cards);
            newCards.add(card);
            return new Hand(newCards);
        }

        public Hand remove(Card card) {
            List<Card> newCards = new ArrayList<>(cards);
            newCards.remove(card);
            return new Hand(newCards);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Hand)) return false;
            Hand other = (Hand) o;
            return cards.equals(other.cards);
        }
    }

    public static class Player {
        /**
         * Use a universally unique identifier to uniquely identify a player
         */
        private final UUID id;

        private final String name;
        private Hand hand = Hand.empty();

        public Player(String name) {
            this.id = UUID.randomUUID();
            this.name = name;
        }

        public UUID id() {
            return id;
        }

        public String name() {
            return name;
        }

        public Hand hand() {
            return hand;
        }

        /**
         * Recieve a card into this player's hand
         * @param card A card
         */
        public void receive(Card card) {
            hand = hand.add(card);
        }

        /**
         * Discard a card from this player's hand
         * @param card A card
         */
        public void discard(Card card) {
            hand = hand.remove(card);
        }

        public boolean guess(int handIndexLargestCard) {
            Card guessCard = hand.cards().get(handIndexLargestCard);
            for (Card card : hand.cards()) {
                if (guessCard == card) continue; // skip comparing with itself
                int compareResult = guessCard.compareTo(card);
                // guess is wrong if any other card is equal or higher
                if (compareResult <= 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
