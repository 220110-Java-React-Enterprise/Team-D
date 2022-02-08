package utils;

import exceptions.InvalidInputException;
import objects.Card;
import objects.User;


/**
 * This is a placeholder class that mocks the ORM interaction.  Only used for development,
 * none of the methods here actually do anything.
 */
public class MockingORM {
    public static Card getCardFromCardNumber (Integer card_id) throws InvalidInputException {
        // Assume some erroneous number
        if (card_id < 0) {
            throw new InvalidInputException("Card " + card_id + " not found!");
        }

        // Below is mocking the successful retrieval of this card
        Card card = new Card();
        card.setId(card_id);
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
        card.setId(3);
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
    public static Boolean deleteCard(Integer card_id) {
        System.out.println("Yeah sure, card #" + card_id + " was deleted.");
        return true;
    }

    // "looks up" a user by ID (Integer) and returns a User object (their info)
    public static User getUser(Integer id) {
        User user = new User();
        // setting fake information
        user.setId(id);
        user.setFirstName("Pooh");
        user.setLastName("Bear");
        user.setEmail("impooh@treehouse.net");
        return user;
    }

    // "registers" a user and stores their information, returning an ID (Integer)
    public static void addUser(User user) {
        user.setId(4);
    }

    // "updates" a user, returning... something
    public static Boolean updateUser(User user) throws InvalidInputException {
        // this is in place of some sql error
        if (user.getId() < 0) {
            throw new InvalidInputException("Invalid user ID: " + user.getId());
        }
        System.out.println("User has been updated.");
        return true;
    }

    // "deletes" a user, returning... something
    public static Boolean deleteUser(Integer userId) {
        System.out.println("Deleted user " + userId);
        return true;
    }
}
