package dev.danielallison.cardguessinggame;

import static org.junit.Assert.*;

import dev.danielallison.cardguessinggame.Domain.*;
import java.util.*;
import java.util.stream.Collectors;
import org.junit.Test;

public class AppTest {
    @Test
    public void cardSuitOrder() {
        App app = new App();
        Card spadeSuit = new Card(Card.Suit.SPADE, Card.Rank.TWO);
        Card heartSuit = new Card(Card.Suit.HEART, Card.Rank.TWO);
        Card diamondSuit = new Card(Card.Suit.DIAMOND, Card.Rank.TWO);
        Card clubSuit = new Card(Card.Suit.CLUB, Card.Rank.TWO);

        // compareTo will return 1 when the left is greater than the right
        assert (spadeSuit.compareTo(heartSuit) == 1);
        assert (heartSuit.compareTo(diamondSuit) == 1);
        assert (diamondSuit.compareTo(clubSuit) == 1);
    }

    @Test
    public void acesAreHigh() {
        App app = new App();
        Card aceCard = new Card(Card.Suit.SPADE, Card.Rank.ACE);
        Card twoCard = new Card(Card.Suit.SPADE, Card.Rank.TWO);

        assert (aceCard.compareTo(twoCard) == 1);
    }

    @Test
    public void compareEqualCards() {
        App app = new App();
        Card card1 = new Card(Card.Suit.SPADE, Card.Rank.TWO);
        Card card2 = new Card(Card.Suit.SPADE, Card.Rank.TWO);

        assert (card1.equals(card2));
    }

    @Test
    public void deckContainsExactly52UniqueCards() {
        App app = new App();
        Deck deck = new Deck();
        assertEquals(52, deck.cards().size());

        Set<String> uniq =
                deck.cards().stream()
                        .map(c -> c.getSuit() + "-" + c.getRank())
                        .collect(Collectors.toSet());

        assertEquals(52, uniq.size());
    }
}
