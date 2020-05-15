package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Whack-A-Mole";
    private TextView Result;
    private Button Button1;
    private Button Button2;
    private Button Button3;
    private List<Button> ButtonList = new ArrayList<>();
    int count = 0;
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Result = (TextView) findViewById(R.id.Result);
        Button1 = (Button) findViewById(R.id.Button1);
        Button2 = (Button) findViewById(R.id.Button2);
        Button3 = (Button) findViewById(R.id.Button3);
        ButtonList.add(Button1);
        ButtonList.add(Button2);
        ButtonList.add(Button3);

        Button1.setOnClickListener(this);
        Button2.setOnClickListener(this);
        Button3.setOnClickListener(this);
        Log.v(TAG, "Finished Pre-Initialisation!");


    }

    @Override
    protected void onStart() {
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        if (checkButton.getText() == "*") {
            count += 1;
            String numAsString = String.valueOf(count);
            Result.setText(numAsString);
            Log.v(TAG, "Hit, score added!");
            setNewMole();
        } else {
            count -= 1;
            if (count < 0) {
                count = 0;
            }
            String numAsString = String.valueOf(count);
            Result.setText(numAsString);
            Log.v(TAG, "Missed, score deducted!");
            setNewMole();

        }
        if(count % 10 == 0 && count>0){
            nextLevelQuery();
        }
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
    }

    private void nextLevelQuery() {
        Log.v(TAG, "Advance option given to user!");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning! Insane Whack-A-Mole Incoming!");
        builder.setMessage("Would like to enter the advance version of “Whack-A-Mole!”");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.v(TAG, "User decline!");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/
    }

    private void nextLevel() {
        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
        intent.putExtra("Result",count); //count == score
        startActivity(intent);
        /* Launch advanced page */
    }

    private void setNewMole() {
        for (Button i : ButtonList) {
            i.setText("o"); //reset mole
        }
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        Button random = ButtonList.get(randomLocation);
        random.setText("*");
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Button1:
                Log.v(TAG, "Button Left Clicked!");
                break;
            case R.id.Button2:
                Log.v(TAG, "Button Middle Clicked!");
                break;
            case R.id.Button3:
                Log.v(TAG, "Button Right Clicked");
                break;
        }

        doCheck((Button)v);
    }
}

