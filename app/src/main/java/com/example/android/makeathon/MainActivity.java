package com.example.android.makeathon;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.apache.commons.net.ftp.FTPClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jxl.*;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RetrieveTask().execute();
    }

        class RetrieveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void...voids) {
//                File targetFile = new File(Environment.getExternalStorageDirectory() + File.separator + "Makeathon" + File.separator + "record.xls");
//                targetFile.mkdirs();
                FileOutputStream outputStream;

                FTPClient ftpClient = new FTPClient();
                try {
                    ftpClient.connect("192.168.5.3", 21);
                    ftpClient.enterLocalPassiveMode();
                    ftpClient.login("FTP_User", "ravi1998");

                    ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);

//                    outputStream = new FileOutputStream(targetFile);
                    InputStream inStream = ftpClient.retrieveFileStream("record.xls");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();

                        }
                    });
                    ftpClient.disconnect();
//                    outputStream.close();
                   Workbook wb =

                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "ioexception", Toast.LENGTH_SHORT).show();

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "exception", Toast.LENGTH_SHORT).show();

                        }
                    });                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.signOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LogInActivity.class));
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
