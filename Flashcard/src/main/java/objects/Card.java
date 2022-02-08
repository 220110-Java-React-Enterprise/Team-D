package objects;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import annotations.Table;
import annotations.Column;

@Table(tableName="Card")
public class Card {
    @Column(columnName="card_id", primaryKey=true)
    Integer id;
    String question;
    String answer;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @JsonGetter("creator_id")
    public Integer getCreatorId() {
        return creatorId;
    }

    @JsonSetter("creator_id")
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    // Empty constructor needed for Jackson
    public Card(){}

    // Partial constructor - ex. if user submitted a card in a form (post) - see CardServlet.doPost()
    public Card(String question, String answer, Integer creatorId) {
        this.question = question;
        this.answer = answer;
        this.creatorId = creatorId;
    }
}
