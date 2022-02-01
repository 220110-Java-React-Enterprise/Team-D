package objects;

// The idea behind this is that if the user wants to take a quiz - they could create a list (randomized?) of cards to
// answer, and this would track their progress.  Long term it could relate to a history of quizzes or something

import java.util.ArrayList;

// TODO: (maybe) leaving this unimplemented for now since it's still half-baked
// Did not make a mapping in web.xml or anything for this
public class Quiz {
    Integer quizId;
    String date;    // or date object
    ArrayList<Card> cards;   // a list of the cards in the list - does ArrayList have fixed ordering?
    // as a result cards may need an additional attribute like "solved" that isn't stored in the db, or something


    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    // Empty constructor needed by Jackson
    public Quiz(){}
}
