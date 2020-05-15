package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "Whack-A-Mole 2";
    private CountDownTimer myCountDown;
    private CountDownTimer placeMoleCountDown;
    int count = 0;
    private TextView Result;
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */



    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */
        myCountDown = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
                String toastMsg = "Get Ready In " + millisUntilFinished / 1000 + " seconds";
                Toast.makeText(getApplicationContext(), toastMsg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(), "GO!",Toast.LENGTH_SHORT).show();
                placeMoleTimer();
                myCountDown.cancel();
            }
        };
        myCountDown.start();


    }
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        placeMoleCountDown = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setNewMole();
                Log.v(TAG, "New Mole Location!");
            }

            @Override
            public void onFinish() {
                placeMoleCountDown.start();
            }
        };
        placeMoleCountDown.start();
    }
    private static final int[] BUTTON_IDS = {
        /* HINT:
            Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
            You may use if you wish to change or remove to suit your codes.*/
        R.id.Button1,R.id.Button2,R.id.Button3,R.id.Button4,R.id.Button5,R.id.Button6,R.id.Button7,R.id.Button8,R.id.Button9
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView Result = findViewById(R.id.Result);
        setNewMole();
        Intent receivingEnd = getIntent();
        int message = receivingEnd.getIntExtra("Result",count);

        Log.v(TAG, "Current User Score: " + String.valueOf(message));

        Result.setText(Integer.toString(message));
        count = message; // set current user score in new page
        readyTimer();



        //for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
        //}
    }
    @Override
    protected void onStart(){
        super.onStart();
    }
    private void doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
        TextView Result = findViewById(R.id.Result);
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
    }

    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        for (int i : BUTTON_IDS) {
            Button btn = (Button) findViewById(i);
            btn.setText("O");
        }
        Button random = (Button)findViewById(BUTTON_IDS[randomLocation]);
        random.setText("*");
    }

    @Override
    public void onClick(View view) {
        for (int i : BUTTON_IDS){
            if(view.getId() == i){
                doCheck((Button)findViewById(i));
            }
        }
    }
}

