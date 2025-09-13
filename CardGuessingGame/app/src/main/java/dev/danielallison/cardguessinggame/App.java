package dev.danielallison.cardguessinggame;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
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
        public static class Card {
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

            /**
             * Constructor. Creates a new card.
             * @param suit The suit of the card.
             * @param rank The rank of the card.
             */
            public Card(Suit suit, Rank rank) {
                this.suit = suit;
                this.rank = rank;
            }

            public Suit getSuit() {
                return this.suit;
            }

            public Rank getRank() {
                return this.rank;
            }
        }
    }
}
