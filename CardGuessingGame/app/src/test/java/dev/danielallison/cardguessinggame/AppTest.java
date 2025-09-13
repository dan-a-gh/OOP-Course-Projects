package dev.danielallison.cardguessinggame;

import org.junit.Test;
import static org.junit.Assert.*;

import dev.danielallison.cardguessinggame.App;
import dev.danielallison.cardguessinggame.App.Domain.Card;

public class AppTest {
    @Test public void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }

    @Test public void cardSuitOrder() {
        App app = new App();
        Card spadeSuit = new Card(Card.Suit.SPADE, Card.Rank.TWO);
        Card heartSuit = new Card(Card.Suit.HEART, Card.Rank.TWO);
        Card diamondSuit = new Card(Card.Suit.DIAMOND, Card.Rank.TWO);
        Card clubSuit = new Card(Card.Suit.CLUB, Card.Rank.TWO);

        // compareTo will return 1 when the left is greater than the right
        assert(spadeSuit.compareTo(heartSuit) == 1);
        assert(heartSuit.compareTo(diamondSuit) == 1);
        assert(diamondSuit.compareTo(clubSuit) == 1);
    }

    @Test public void acesAreHigh() {
        App app = new App();
        Card aceCard = new Card(Card.Suit.SPADE, Card.Rank.ACE);
        Card twoCard = new Card(Card.Suit.SPADE, Card.Rank.TWO);

        assert(aceCard.compareTo(twoCard) == 1);
    }

    @Test public void compareEqualCards() {
        App app = new App();
        Card card1 = new Card(Card.Suit.SPADE, Card.Rank.TWO);
        Card card2 = new Card(Card.Suit.SPADE, Card.Rank.TWO);

        assert(card1.equals(card2));
    }
}
