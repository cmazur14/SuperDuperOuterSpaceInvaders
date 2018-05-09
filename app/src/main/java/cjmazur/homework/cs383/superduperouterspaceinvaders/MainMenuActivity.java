package cjmazur.homework.cs383.superduperouterspaceinvaders;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainMenuActivity extends AppCompatActivity {

    private Button beginGame;
    private Button quitGame;
    private TextView score1;
    private TextView score2;
    private TextView score3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);



        beginGame = (Button)findViewById(R.id.begin_game_button);
        quitGame = (Button)findViewById(R.id.quit_game_button);
        score1 = findViewById(R.id.score1);
        score2 = findViewById(R.id.score2);
        score3 = findViewById(R.id.score3);
        score1.setText("1st place: " + SharedPrefManager.getInstance(getApplicationContext()).getScore1());
        score2.setText("2nd place: " + SharedPrefManager.getInstance(getApplicationContext()).getScore2());
        score3.setText("3rd place: " + SharedPrefManager.getInstance(getApplicationContext()).getScore3());


        //final Database d=Database.get();

        beginGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        quitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        beginGame = (Button)findViewById(R.id.begin_game_button);

        beginGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        quitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }
}
