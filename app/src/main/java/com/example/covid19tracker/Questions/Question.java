package com.example.covid19tracker.Questions;

public class Question {

    Answer Answer_1,Answer_2,Answer_3,Answer_4,Answer_5,Answer_6;


    public Question(Answer answer_1, Answer answer_2, Answer answer_3, Answer answer_4, Answer answer_5, Answer answer_6) {
        Answer_1 = answer_1;
        Answer_2 = answer_2;
        Answer_3 = answer_3;
        Answer_4 = answer_4;
        Answer_5 = answer_5;
        Answer_6 = answer_6;
    }

    public Question(Answer answer_1, Answer answer_2, Answer answer_3) {
        Answer_1 = answer_1;
        Answer_2 = answer_2;
        Answer_3 = answer_3;
    }

    public void setAnswer_1(Answer answer_1) {
        Answer_1 = answer_1;
    }

    public void setAnswer_2(Answer answer_2) {
        Answer_2 = answer_2;
    }

    public void setAnswer_3(Answer answer_3) {
        Answer_3 = answer_3;
    }

    public void setAnswer_4(Answer answer_4) {
        Answer_4 = answer_4;
    }

    public void setAnswer_5(Answer answer_5) {
        Answer_5 = answer_5;
    }

    public void setAnswer_6(Answer answer_6) {
        Answer_6 = answer_6;
    }

    public Answer getAnswer_1() {
        return Answer_1;
    }

    public Answer getAnswer_2() {
        return Answer_2;
    }

    public Answer getAnswer_3() {
        return Answer_3;
    }

    public Answer getAnswer_4() {
        return Answer_4;
    }

    public Answer getAnswer_5() {
        return Answer_5;
    }

    public Answer getAnswer_6() {
        return Answer_6;
    }


}
