package utils;

import exceptions.InvalidInputException;
import objects.Card;

// These are just stubs mocking an ORM call.  They don't actually do anything.
public class MockingORM {
    public static Card getCardFromCardNumber (Integer cardId) throws InvalidInputException {
        // Assume some erroneous number
        if (cardId < 0) {
            throw new InvalidInputException("Card " + cardId + " not found!");
        }

        // Below is mocking the successful retrieval of this card
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
        System.out.println("Card has been submitted!");
        System.out.println("Mocking submit: " + card.getQuestion());
        return card;
    }

    // "updates" an existing card
    public static Card updateCard(Card card) {
        card.setQuestion("New question here");
        System.out.println("Some card was updated.");
        return card;
    }

    // "deletes" an existing card
    public static Boolean deleteCard(Integer cardId) {
        System.out.println("Yeah sure, card #" + cardId + " was deleted.");
        return true;
    }
}
