package com.example.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button call = findViewById(R.id.CallButton);
        Button map = findViewById(R.id.ViewMapButton);
        Button message = findViewById(R.id.SendTextButton);
        Button email = findViewById(R.id.SendEmailButton);
        Button browser = findViewById(R.id.BrowserButton);

        Button pickContactActivity= findViewById(R.id.contactActivity);
        Button thumbNailActivity =findViewById(R.id.thumbActivity);

        call.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                callButtonClick();
            }
        });

        map.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                viewMapButtonClick();
            }
        });

        message.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                sendMessageButtonClick();
            }
        });

        email.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                sendEmailButtonClick();
            }
        });

        browser.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                browserButtonClick();
            }
        });

        thumbNailActivity.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, ThumbNailCapture.class);
                startActivity(intent);
            }
        });


        pickContactActivity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, PickContact.class);
                startActivity(intent);
            }
        });
    }

    private void callButtonClick()
    {
        int phone = 1234567;
        Uri uri = Uri.parse(String.format("tel: %d", phone));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(uri);
        startActivity(intent);
    }

    private void viewMapButtonClick()
    {
        double latitude = -31.95;
        double longitude = 115.86;
        Uri uri = Uri.parse(String.format("geo: %f,%f", latitude,longitude));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }

    private void sendMessageButtonClick()
    {
        int phone = 1234567;
        String smsText = "Hello this is the test text";

        Uri uri = Uri.parse(String.format("smsto: %d",phone));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(uri);
        intent.putExtra("sms_body", smsText);
        startActivity(intent);
    }

    private void sendEmailButtonClick()
    {
        String[]mailTo = {"abc@gmail.com"};
        String subject = "test";
        String mailBody = "We are testing email";

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("message/rfc822");

        intent.putExtra(Intent.EXTRA_EMAIL, mailTo);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, mailBody);
        startActivity(intent);
    }

    private void browserButtonClick()
    {
        String url = "https://www.youtube.com/";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);

        PackageManager pm = getPackageManager();
        ResolveInfo ri = pm.resolveActivity(intent, 0);
        if(ri!=null)
        {
            Log.d("Name", ri.activityInfo.packageName);
            startActivity(intent);
        }
        else {
            Toast.makeText(MainActivity.this, "No suitable package found", Toast.LENGTH_SHORT).show();
        }
    }
}