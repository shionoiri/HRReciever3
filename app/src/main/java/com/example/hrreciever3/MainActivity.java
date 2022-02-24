package com.example.hrreciever3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et;
                et = (EditText) findViewById(R.id.editText);
                AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... Void) {
                        String receiveString = "NG";
                        ServerSocket serverSocket = null;
                        BufferedReader reader = null;
                        try {
                            serverSocket = new ServerSocket(8080);
                            while (true) {
                                try {
                                    Socket socket = serverSocket.accept();

                                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                    receiveString = reader.readLine();
                                    reader.close();
                                    socket.close();
                                    serverSocket.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return receiveString;
                    }


                    @Override
                    protected void onPostExecute(String string) {

                        et.setText("HeartRate=" + string);
                    }
                };
                task.execute();
            }
        });
    }
}