package com.ossovita.wpclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    EditText chatEditText;
    String activeUser;
    ArrayList<String> messages = new ArrayList<>();
    ArrayAdapter aa;
    ListView chatListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        activeUser = intent.getStringExtra("username");
        setTitle("Chat with" + activeUser);
        chatEditText = findViewById(R.id.chatEditText);
        chatListView = findViewById(R.id.chatListView);
        aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1,messages);

        //Message classı içerisinde sorgu yapmak üzere query objesi oluşturduk
        //query1 bizim gönderdiğimiz mesajları getirecek
        ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>("Message");
        query1.whereEqualTo("sender",ParseUser.getCurrentUser().getUsername());
        query1.whereEqualTo("recipient",activeUser);


        //query2 karşı tarafın gönderdiği mesajları getirecek
        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("Message");
        query2.whereEqualTo("recipient",ParseUser.getCurrentUser().getUsername());
        query2.whereEqualTo("sender",activeUser);

        //Bu sorguları bir listeye atıyoruz
        List<ParseQuery<ParseObject>> queries = new ArrayList<>();
        queries.add(query1);
        queries.add(query2);






    }

    public void sendChat(View v){
        //Message classı içerisine bir obje gönderiyoruz
        ParseObject message = new ParseObject("Message");
        message.put("sender", ParseUser.getCurrentUser().getUsername());
        message.put("recipient",activeUser);
        message.put("message",chatEditText.getText().toString());
        chatEditText.setText("");
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Toast.makeText(ChatActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}