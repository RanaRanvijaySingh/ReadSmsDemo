package rana.com.readsmsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IncomingMessageReceiver mIncomingMessageReceiver;
    private final String TAG = "main activity";
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponent();
    }

    private void initializeComponent() {
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvRegister.setText("Register");
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRegisterButton();
            }
        });
    }

    /**
     * Function is called when the user click on the send button.
     * This function is to valid the user with the server by the phone number.
     */
    private void onClickRegisterButton() {
        tvRegister.setText("Registering ...");
        mIncomingMessageReceiver = new IncomingMessageReceiver();
        mIncomingMessageReceiver.setActivityContext(this);
    }

    public void notificationReceivedDataIs(String phoneNumber, String message) {
        mIncomingMessageReceiver.clearAbortBroadcast();
        Toast.makeText(getApplicationContext(), phoneNumber + " : OTP received : " + message, Toast
                .LENGTH_LONG).show();
    }
}
