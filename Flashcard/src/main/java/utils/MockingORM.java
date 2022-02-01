package utils;

import objects.Card;

public class MockingORM {
    // This is just a stub mocking an orm call
    public static Card getCardFromCardNumber(Integer cardId) {
        Card card = new Card();
        card.setCardId(cardId);
        card.setQuestion("What is an ORM?");
        card.setAnswer1("Ordinary Relational Map");
        card.setAnswer2("Object-Relational Mapping");
        card.setAnswer3("Object-Rotational Mapping");
        card.setAnswer4("Objective Relation Map");
        card.setCreatorId(0);

        // Also should check if card exists / is not null
        return card;
    }

    // Sends a new card to be persisted.
    // Returns the card but with the ID number populated.
    public static Card submitNewCard(Card card) {
        card.setCardId(3);
        return card;
    }

    // "updates" an existing card
    public static Card updateCard(Card card) {
        card.setQuestion("New question here");
        return card;
    }

    // "deletes" an existing card
    public static Boolean deleteCard(Integer cardId) {
        System.out.println("Yeah sure, card #" + cardId + " was deleted.");
        return true;
    }
}
