package objects;

import annotations.Column;
import annotations.Table;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@Table(tableName="Card")
@JsonPropertyOrder({"card_id", "question", "answer1", "answer2", "answer3", "answer4", "correct_answer", "user_id"})
public class Card {
    @Column(columnName="card_id", primaryKey=true)
    Integer id;
    @Column(columnName="question", primaryKey=false)
    String question;
    @Column(columnName="answer1", primaryKey=false)
    String answer1;
    @Column(columnName="answer2", primaryKey=false)
    String answer2;
    @Column(columnName="answer3", primaryKey=false)
    String answer3;
    @Column(columnName="answer4", primaryKey=false)
    String answer4;
    @Column(columnName="correct_answer", primaryKey=false)
    Integer correctAnswer;
    @Column(columnName="user_id", primaryKey=false)
    Integer userId;

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

    @JsonSetter("question")
    public void setQuestion(String question) {
        this.question = question;
    }

    @JsonGetter("answer1")
    public String getAnswer1() {
        return answer1;
    }

    @JsonSetter("answer1")
    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    @JsonGetter("answer2")
    public String getAnswer2() {
        return answer2;
    }

    @JsonSetter("answer2")
    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    @JsonGetter("answer3")
    public String getAnswer3() {
        return answer3;
    }

    @JsonSetter("answer3")
    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    @JsonGetter("answer4")
    public String getAnswer4() {
        return answer4;
    }

    @JsonSetter("answer4")
    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    @JsonGetter("user_id")
    public Integer getUserId() {
        return userId;
    }

    @JsonSetter("user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @JsonGetter("correct_answer")
    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    @JsonSetter("correct_answer")
    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    // Empty constructor needed for Jackson
    public Card(){}

    // Partial constructor - ex. if user submitted a card in a form (post) - see CardServlet.doPost()
    public Card(String question, String answer1, String answer2, String answer3, String answer4, Integer correctAnswer, Integer userId) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswer = correctAnswer;
        this.userId = userId;
    }
}
