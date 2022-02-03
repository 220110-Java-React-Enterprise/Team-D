package objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

//@Table(tableName="Card")
public class Card {
    //@Column(columnName=card_id, primaryKey=true)
    Integer id;
    String question;
    String answer1;
    String answer2;
    String answer3;
    String answer4;
    Integer correctAnswer;
    Integer creatorId;

    // Getters and setters
    @JsonGetter("card_id")
    public Integer getId() {
        return id;
    }

    @JsonSetter("card_id")
    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    // Empty constructor needed for Jackson
    public Card(){}

    // Partial constructor - ex. if user submitted a card in a form (post) - see CardServlet.doPost()
    public Card(String question, String answer1, String answer2, String answer3, String answer4, Integer correctAnswer, Integer creatorId) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswer = correctAnswer;
        this.creatorId = creatorId;
    }
}
