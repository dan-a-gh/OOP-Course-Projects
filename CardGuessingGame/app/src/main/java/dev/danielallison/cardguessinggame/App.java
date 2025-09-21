package dev.danielallison.cardguessinggame;

import java.util.*;

/**
 * As per professor's instructions, everything is to be done in 1 file
 * However this is bad practice and violates the single responsibility principle
 * App class is where it all happens
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Welcome to cards.");
    }

    /**
     * The Domain class contains the business logic
     * In this program, it describes playing cards, their operations, and their order according to this game
     */
    public class Domain {
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
            }

            private final Rank rank;
            private final Suit suit;

            public Suit getSuit() {
                return this.suit;
            }

            public Rank getRank() {
                return this.rank;
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

        public class Player {
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
        }
    }
}
