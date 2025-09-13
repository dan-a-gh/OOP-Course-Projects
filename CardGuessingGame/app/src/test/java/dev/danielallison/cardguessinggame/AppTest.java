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

        assert(spadeSuit.getSuit().getValue() > heartSuit.getSuit().getValue());
        assert(heartSuit.getSuit().getValue() > diamondSuit.getSuit().getValue());
        assert(diamondSuit.getSuit().getValue() > clubSuit.getSuit().getValue());
    }

    @Test public void acesAreHigh() {
        App app = new App();
        Card aceCard = new Card(Card.Suit.SPADE, Card.Rank.ACE);
        Card twoCard = new Card(Card.Suit.SPADE, Card.Rank.TWO);

        assert(aceCard.getRank().getValue() > twoCard.getRank().getValue());
    }
}
