package com.example.covid19tracker.Questions;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covid19tracker.R;
import com.example.covid19tracker.result.ResultNegativeActivity;
import com.example.covid19tracker.result.ResultPositiveActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import  com.example.covid19tracker.userSession.UserDataa;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firestore.v1.DocumentTransform;
import java.time.*;

public class questions extends AppCompatActivity {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /*
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * */
    ///////////////////////////////////////////////////////////////////////////////////////////////



    private TextView submit_button,back_button;
    public int Score=0,conuter=1,number_check_answer=0;
    ImageView progresbar;
    TextView textView;

    Stack<Integer> stack_next_button = new Stack<Integer>();
    Stack<String> saved_diseaes = new Stack<String>();

    Stack<Integer> stack_back_button = new Stack<Integer>();
    HashMap<String, String> useraccount=new HashMap<>();
    HashMap<String, Object> updated_data_account=new HashMap<>();
    FirebaseFirestore firestore_useraccount;
    UserDataa userDataa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
         userDataa=new UserDataa(questions.this);
        submit_button=findViewById(R.id.submit);
        back_button=findViewById(R.id.back);
        useraccount=userDataa.getUserData();
        Date date = new Date();

        ///////////////////////////////////////////////////////////////////////////////////////////////
        /*         intialize Questions by class Question           */
        Question Q1=new Question(new Answer("No symptom (from the below)",0),new Answer("Breathing Problem",1),new Answer("Fever",2),new Answer("Dry Cough",3),new Answer("Running Nose",4),new Answer("Asthma",5));
        Question Q2=new Question(new Answer("No symptom (from the below)",0),new Answer("Chronic Lung Disease",1),new Answer("Headache",2),new Answer("Heart Disease",3),new Answer("Diabetes",4),new Answer("Gastrointestinal problem",5));
        Question Q3=new Question(new Answer("No symptom (from the below)",0),new Answer("Are you committed to wearing masks",1),new Answer("Do you regularly use hand sanitizer",2));

        CheckBox  Answer1,Answer2,Answer3,Answer4,Answer5,Answer6;
        Answer1=findViewById(R.id.Question_1);
        Answer2=findViewById(R.id.Question_2);
        Answer3=findViewById(R.id.Question_3);
        Answer4=findViewById(R.id.Question_4);
        Answer5=findViewById(R.id.Question_5);
        Answer6=findViewById(R.id.Question_6);
        progresbar=findViewById(R.id.Questions_activtiy_progressbar);
        textView=findViewById(R.id.anwsers_text_questions);


        Answer1.setText(Q1.Answer_1.getAnswer());
        Answer2.setText(Q1.Answer_2.getAnswer());
        Answer3.setText(Q1.Answer_3.getAnswer());
        Answer4.setText(Q1.Answer_4.getAnswer());
        Answer5.setText(Q1.Answer_5.getAnswer());
        Answer6.setText(Q1.Answer_6.getAnswer());


        back_button.setVisibility(View.INVISIBLE);


        firestore_useraccount=FirebaseFirestore.getInstance();

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (conuter) {




                    case 2:


                        Answer1.setText(Q1.Answer_1.getAnswer());
                        Answer2.setText(Q1.Answer_2.getAnswer());
                        Answer3.setText(Q1.Answer_3.getAnswer());
                        Answer4.setText(Q1.Answer_4.getAnswer());
                        Answer5.setText(Q1.Answer_5.getAnswer());
                        Answer6.setText(Q1.Answer_6.getAnswer());
                        conuter--;
                        back_button.setVisibility(View.INVISIBLE);
                        Score=0;
                        saved_diseaes.clear();
                        //Toast.makeText(questions.this, "your befor   "+Score + " ", Toast.LENGTH_SHORT).show();
                        progresbar.setImageResource(R.drawable.progressbar_1of3);
                        textView.setText("1 of 3 answered");


                        break;


                    case 3:

                        Answer1.setText(Q2.Answer_1.getAnswer());
                        Answer2.setText(Q2.Answer_2.getAnswer());
                        Answer3.setText(Q2.Answer_3.getAnswer());
                        Answer4.setText(Q2.Answer_4.getAnswer());
                        Answer5.setText(Q2.Answer_5.getAnswer());
                        Answer6.setText(Q2.Answer_6.getAnswer());
                        Answer4.setVisibility(View.VISIBLE);
                        Answer5.setVisibility(View.VISIBLE);
                        Answer6.setVisibility(View.VISIBLE);
                        submit_button.setVisibility(View.VISIBLE);
                        submit_button.setText("Next");

                        while (number_check_answer!=0)
                        {

                            subtractsecore(stack_next_button.pop());
                            saved_diseaes.pop();
                            number_check_answer--;
                        }
                        conuter--;
                        //Toast.makeText(questions.this, Score + " ", Toast.LENGTH_SHORT).show();
                        progresbar.setImageResource(R.drawable.progressbar_2of3);
                        textView.setText("2 of 3 answered");
                        //Toast.makeText(questions.this, "your befor   "+Score + " ", Toast.LENGTH_SHORT).show();



                }


                Answer1.setChecked(false);
                Answer2.setChecked(false);
                Answer3.setChecked(false);
                Answer4.setChecked(false);
                Answer5.setChecked(false);
                Answer6.setChecked(false);



            }




        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (conuter) {

                    case 1:
                        number_check_answer=0;
                        is_check(Q1, Answer1, Answer2, Answer3, Answer4, Answer5, Answer6);
                        if(number_check_answer!=0) {
                            //Toast.makeText(questions.this, questionAnwser.get("1").toString(), Toast.LENGTH_SHORT).show();
                            Answer1.setText(Q2.Answer_1.getAnswer());
                            Answer2.setText(Q2.Answer_2.getAnswer());
                            Answer3.setText(Q2.Answer_3.getAnswer());
                            Answer4.setText(Q2.Answer_4.getAnswer());
                            Answer5.setText(Q2.Answer_5.getAnswer());
                            Answer6.setText(Q2.Answer_6.getAnswer());
                            back_button.setVisibility(View.VISIBLE);
                            conuter++;
                            // Toast.makeText(questions.this, number_check_answer + " ", Toast.LENGTH_SHORT).show();
                            progresbar.setImageResource(R.drawable.progressbar_2of3);
                            textView.setText("2 of 3 answered");
                            //Toast.makeText(questions.this, "your after   "+Score + " ", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(questions.this,  "  اختر  مرض ياسطى ", Toast.LENGTH_SHORT).show();
                        }


                        break;


                    case 2:
                        number_check_answer=0;
                        is_check(Q2, Answer1, Answer2, Answer3, Answer4, Answer5, Answer6);
                        //Toast.makeText(questions.this, questionAnwser.get("1").toString(), Toast.LENGTH_SHORT).show();
                        if(number_check_answer!=0) {
                            Answer1.setText(Q3.Answer_1.getAnswer());
                            Answer2.setText(Q3.Answer_2.getAnswer());
                            Answer3.setText(Q3.Answer_3.getAnswer());
                            Answer4.setVisibility(View.INVISIBLE);
                            Answer5.setVisibility(View.INVISIBLE);
                            Answer6.setVisibility(View.INVISIBLE);
                            conuter++;
                            submit_button.setText("Result");
                            // Toast.makeText(questions.this, number_check_answer + " ", Toast.LENGTH_SHORT).show();
                            progresbar.setImageResource(R.drawable.progressbar_3of3);
                            textView.setText("3 of 3 answered");
                            // Toast.makeText(questions.this, "your after   "+Score + " ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(questions.this,  "  متختار مرض ياعم انت ", Toast.LENGTH_SHORT).show();
                        }

                        break;


                    case 3:
                        number_check_answer=0;
                        is_check(Q3, Answer1, Answer2, Answer3, Answer4, Answer5, Answer6);
                        if(number_check_answer!=0) {
                            //submit_button.setVisibility(View.INVISIBLE);
                            // Toast.makeText(questions.this,number_check_answer+" ", Toast.LENGTH_SHORT).show();
                           // //Toast.makeText(questions.this, "your befor   "+Score + " ", Toast.LENGTH_SHORT).show();
                            if(Score<10) {




                                updated_data_account.put("symtoms_of_last_check",saved_diseaes.toString());
                                updated_data_account.put("result_of_last_check","Negative");
                                updated_data_account.put("date_of_last_check",DateFormat.format("MMMM d, yyyy ", date.getTime()));
                                try {
                                    firestore_useraccount.collection("users").document(useraccount.get("user_id")).update(updated_data_account);
                                }catch (Exception e)
                                {
                                    Toast.makeText(getApplicationContext(),"Your Data Doesn't Uploaded check your internet",Toast.LENGTH_LONG);
                                }
                                startActivity(new Intent(questions.this, ResultNegativeActivity.class));
                            }
                            else{

                                updated_data_account.put("symtoms_of_last_check",saved_diseaes.toString());
                                updated_data_account.put("result_of_last_check","Positive");
                                updated_data_account.put("date_of_last_check",DateFormat.format("MMMM d, yyyy ", date.getTime()));
                                try {
                                    firestore_useraccount.collection("users").document(useraccount.get("user_id")).update(updated_data_account);
                                }catch (Exception e)
                                {
                                    Toast.makeText(getApplicationContext(),"Your Data Doesn't Uploaded check your internet",Toast.LENGTH_LONG);
                                }

                                startActivity(new Intent(questions.this, ResultPositiveActivity.class));

                            }
                            /*
                        *
                        *
                        *
                        *
                        *
                        *
                        *
                        *
                        *
                        *
                        *
                        *
                        *
                        *
                        * */

                        }
                        else
                        {
                            Toast.makeText(questions.this,  " صدق بالله انا ماشوفت علق غيرك هنا ", Toast.LENGTH_SHORT).show();
                        }





                }


            }
        });





    }


    public void pulssecore (Answer answer)
    {
        Score+=answer.getScore();
        stack_next_button.push(answer.getScore());
        saved_diseaes.push(answer.getAnswer());
        Toast.makeText(questions.this, answer.getAnswer().toString(), Toast.LENGTH_SHORT).show();

    }
    public void subtractsecore (int number_should_be_subtracted)
    {
        Score-=number_should_be_subtracted;

    }
    public void is_check(Question Q ,CheckBox Answer1,CheckBox Answer2,CheckBox Answer3,CheckBox Answer4,CheckBox Answer5,CheckBox Answer6)
    {

        if(Answer1.isChecked())
        {
            pulssecore(Q.Answer_1);
            Answer1.setChecked(false);
            number_check_answer++;


        }

        if(Answer2.isChecked())
        {
            pulssecore(Q.Answer_2);

            Answer2.setChecked(false);
            number_check_answer++;

        }

        if(Answer3.isChecked())
        {
            pulssecore(Q.Answer_3);

            Answer3.setChecked(false);
            number_check_answer++;

        }
        if(Answer4.isChecked())
        {
            pulssecore(Q.Answer_4);

            Answer4.setChecked(false);
            number_check_answer++;

        }
        if(Answer5.isChecked())
        {
            pulssecore(Q.Answer_5);

            Answer5.setChecked(false);
            number_check_answer++;

        }

        if(Answer6.isChecked())
        {
            pulssecore(Q.Answer_6);

            Answer6.setChecked(false);
            number_check_answer++;

        }

    }
}