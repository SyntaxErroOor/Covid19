package com.example.covid19tracker.Questions;

public class Answer
{

String answer;
    int score;

public Answer(String answer, int score) {
        this.answer = answer;
        this.score = score;
        }


public void setAnswer(String answer) {
        this.answer = answer;
        }

public void setScore(int score) {
        this.score = score;
        }


public String getAnswer() {
        return answer;
        }

public int getScore() {
        return score;
        }
        }
