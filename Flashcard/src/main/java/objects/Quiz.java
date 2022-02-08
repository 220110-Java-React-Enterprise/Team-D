package objects;

// The idea behind this is that if the user wants to take a quiz - they could create a list (randomized?) of cards to
// answer, and this would track their progress.  Long term it could relate to a history of quizzes or something

import java.util.ArrayList;
import java.util.HashMap;

public class Quiz {
    Integer quizId;     // Primary Key
    Integer creatorId;  // Person who made this
    ArrayList<Card> cards;  // All the cards in this quiz
    HashMap<Integer, Boolean> correct = new HashMap<>();

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public HashMap<Integer, Boolean> getCorrect() {
        return correct;
    }

    public void setCorrect(HashMap<Integer, Boolean> correct) {
        this.correct = correct;
    }

    // Whenever a question is answered incorrectly, mark it incorrect, else, mark it correct/incorrect
    private void markCorrect(Integer cardNumber, Boolean correct) {
        this.correct.put(cardNumber, correct);
    }

    // Empty constructor needed by Jackson
    public Quiz(){}
}
