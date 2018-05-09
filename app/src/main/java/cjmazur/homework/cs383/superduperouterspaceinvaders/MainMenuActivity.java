package cjmazur.homework.cs383.superduperouterspaceinvaders;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainMenuActivity extends AppCompatActivity {

    private Button beginGame;
    private Button quitGame;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);



        beginGame = (Button)findViewById(R.id.begin_game_button);
        quitGame = (Button)findViewById(R.id.quit_game_button);

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
    }
}
