package com.example.alan.convertmusicscore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    public String example = "#664 455 4455 4455 556#6#764[#2][1][1][1][2]\n" +
            "#664 455 4455 4455 556#6#75 56#6#76424\n" +
            "455#66424455\n" +
            "\n" +
            "7#6#4 #4#5#5 #4#4#5#5 #4#4#5#5 #5#5#67[#1]#6#4[3][#1][#1][#1][#2]\n" +
            "7#6#4 #4#5#5 #4#4#5#5 #4#4#5#5 #5#5#67[#1]#5 #5#67[#1]#6#4#2#4 #4#5#5\n" +
            "7#6#4#2#4 #4#5#5\n" +
            "\n" +
            "#5 #55#5#6#5 #55#5#6#7\n" +
            "#7[#1]#7#6#7#6#5[#2]#7\n" +
            "\n" +
            "#5 #55#5#6#5 #55#5#6#7#3#6#55#5544\n" +
            "\n" +
            "#7#7#7#7#7 #7#6#55 5[#2][#2]\n" +
            "\n" +
            "[#2][#2][#1]#7#6[#1]#7#6#555#3#5\n" +
            "#5#5#5#5#5 #5#67#6#6#4#4\n" +
            "#4#3#3#2#35#5\n" +
            "#5#55#35\n" +
            "\n" +
            "7#6#4 #4#5#5 #4#4#5#5 #4#4#5#5 #5#5#67[#1]#6#4[3][#1][#1][#1][#2]\n" +
            "7#6#4 #4#5#5 #4#4#5#5 #4#4#5#5 #5#5#67[#1]#5 #5#67[#1]#6#4#2\n" +
            "\n" +
            "7#6#4 #4#5#5 #4#4#5#5 #4#4#5#5 #5#5#67[#1]#6#4[3][#1][#1][#1][#2]\n" +
            "7#6#4 #4#5#5 #4#4#5#5 #4#4#5#5 #5#5#67[#1]#5 #5#67[#1]#6#4#2#4 #4#5#5\n" +
            "7#6#4#2#4 #4#5#5\n" +
            "\n" +
            "\n" +
            "#5 #55#5#6#5 #55#5#6#7\n" +
            "#7[#1]#7#6#7#6#5[#2]#7\n" +
            "\n" +
            "#5 #55#5#6#5 #55#5#6#7#3#6#55#5544\n" +
            "\n" +
            "#7#7#7#7#7 #7#6#55 5[#2][#2]\n" +
            "\n" +
            "[#2][#2][#1]#7#6[#1]#7#6#555#3#5\n" +
            "#5#5#5#5#5 #5#67#6#6#4#4\n" +
            "#4#3#3#2#35#5\n" +
            "#5#55#35\n" +
            "\n" +
            "7#6#4 #4#5#5 #4#4#5#5 #4#4#5#5 #5#5#67[#1]#6#4[3][#1][#1][#1][#2]\n" +
            "7#6#4 #4#5#5 #4#4#5#5 #4#4#5#5 #5#5#67[#1]#5 #5#67[#1]#6#4#2\n" +
            "\n" +
            "7#6#4 #4#5#5 #4#4#5#5 #4#4#5#5 #5#5#67[#1]#6#4[3][#1][#1][#1][#2]\n" +
            "7#6#4 #4#5#5 #4#4#5#5 #4#4#5#5 #5#5#67[#1]#5 #5#67[#1]#6#4#2#4 #4#5#5\n" +
            "7#6#4#2#4 #4#5#5\n" +
            "\n" +
            "#2#5 #5#67#6#4#2#4 [#1][#1]7#67[#1][#1]7#67[#1][#2][#2]\n" +
            "#57[#2][#2][#1]7#67[#1]#5 #3#4#5#5545\n" +
            "\n" +
            "\n" +
            "#664 455 4455 4455 556#6#764[#2][1][1][1][2]\n" +
            "#664 455 4455 4455 556#6#75 56#6#7642\n" +
            "#664 455 4455 4455 556#6#764[#2][#3][#2][#2][2][2]\n" +
            "224 455 4455 4455 556#6#75 56#6#7[2][#2][2]\n" +
            "\n" +
            "7#6#4 #4#5#5 #4#4#5#5 #4#4#5#5 #5#5#67[#1]#6#4[3][#1][#1][#1][#2]\n" +
            "7#6#4 #4#5#5 #4#4#5#5 #4#4#5#5 #5#5#67[#1]#5 #5#67[#1][#2][3][#2]\n" +
            "\n" +
            "7#6#4 #4#5#5 #5#5#6#6 #6#677 #5#5#67[#1]#6#4[#1][#4][3][3][#2]\n" +
            "7#6#4 #4#5#5 #4#4#5#5 #4#4#5#5 #5#5#67[#1]#5 #5#67[#1]#6#4#2#4 #4#5#5\n" +
            "7#6#4#2#4 #4#5#5";
    private String test =
            "1\n" + "\n" +
                    "[2](2)(#2)(b2)[#2][b2]2\n" + "\n" +
                    "3\n" + "\n" +
                    "4\n" + "\n" +
                    "5\n" + "\n" +
                    "6\n" + "\n" +
                    "6\n" + "\n" +
                    "6\n" + "\n" +
                    "6\n" + "\n" +
                    "6\n" + "\n" +
                    "7\n";
    private EditText et_content;
    private EditText et_title;
    private EditText et_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_content = findViewById(R.id.music_score_content);
        et_title = findViewById(R.id.music_score_title);
        et_key = findViewById(R.id.music_score_key);

        et_content.setText(example);
        et_title.setText("Only my Railgun");
        et_key.setText("C");

        FloatingActionButton fab = findViewById(R.id.convert_btn);
        fab.setOnClickListener(
                (v) -> {
                    Intent intent = new Intent(this, MusicScoreActivity.class);
                    intent.putExtra("content", et_content.getText().toString());
                    intent.putExtra("title", et_title.getText().toString());
                    intent.putExtra("key", et_key.getText().toString());
                    startActivity(intent);
                });
    }

    private static final String TAG = "MainActivity";
}
