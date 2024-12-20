package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PickContact extends AppCompatActivity
{
    Button pickContact;
    Button more;
    TextView phone;
    TextView email;
    TextView name;
    int contactId;

    private static final int REQUEST_READ_CONTACT_PERMISSION = 3;

    ActivityResultLauncher<Intent>pickContactLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK)
                {
                    Intent data = result.getData();
                    processPickContactResult(data);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_contact);
        pickContact = findViewById(R.id.pickContact);
        email = findViewById(R.id.emailView);
        name = findViewById(R.id.nameView);
        phone = findViewById(R.id.phoneView);
        more = findViewById(R.id.moreButton);
        name.setVisibility(View.INVISIBLE);
        email.setVisibility(View.INVISIBLE);
        more.setVisibility(View.INVISIBLE);
        phone.setVisibility(View.INVISIBLE);

        pickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickContactButtonClicked();
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PickContact.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            REQUEST_READ_CONTACT_PERMISSION);
                }
                else {
                    moreButtonClicked();
                }
            }
        });
    }

    private void processPickContactResult(Intent data)
    {
        Uri contactUri = data.getData();
        String[] queryFields = new String[]
                {
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME
                };
        Cursor c = getContentResolver().query(
                contactUri, queryFields, null, null, null);
        try
        {
            if (c.getCount() > 0)
            {
                c.moveToFirst();
                this.contactId = c.getInt(0);
                String contactName = c.getString(1);
                name.setVisibility(View.VISIBLE);
                name.setText(contactName);
                more.setVisibility(View.VISIBLE);
            }
        }
        finally
        {
            c.close();
        }
    }

    private void pickContactButtonClicked()
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(ContactsContract.Contacts.CONTENT_URI);
        pickContactLauncher.launch(intent);
    }

    public void moreButtonClicked()
    {
        String result ="";
        Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String[] queryFields = new String[]{
                ContactsContract.CommonDataKinds.Email.ADDRESS
        };

        String whereClause = ContactsContract.CommonDataKinds.Email.CONTACT_ID + "a?";
        String [] whereValues = new String[]{
                String.valueOf(this.contactId)
        };
        Cursor c = getContentResolver().query(
                emailUri, queryFields, whereClause,whereValues ,null);
        try
        {
            c.moveToFirst();
            do
            {
                String emailAddress = c.getString(0);
                result = result + emailAddress+ " ";
            }
            while(c.moveToNext());
        }
        finally {
            c.close();
        }
        email.setText(result);
        email.setVisibility(View.VISIBLE);
        getPhoneNumber();
    }

    private void getPhoneNumber()
    {
        String result="";
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] queryFields = new String[] {
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        String whereClause = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";
        String [] whereValues = new String[]{
                String.valueOf(this.contactId)
        };
        Cursor c = getContentResolver().query(
                phoneUri, queryFields, whereClause,whereValues, null);
        try{
            c.moveToFirst();
            do{
                String phoneNumber = c.getString(0);
                result = result+phoneNumber+" ";
            }
            while (c.moveToNext());

        }
        finally {
            c.close();
        }

        phone.setText(result);
        phone.setVisibility(View.VISIBLE);
    }

    public void onRequestPermissionsResult(int requestCode,
                                          @NonNull String[] permissions,
                                          @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_READ_CONTACT_PERMISSION)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(PickContact.this, "Contact Reading Permission Granted",
                        Toast.LENGTH_SHORT).show();
                moreButtonClicked();
            }
        }
    }

    private void pickContactLauncher()
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(ContactsContract.Contacts.CONTENT_URI);
        pickContactLauncher.launch(intent);
    }
}
