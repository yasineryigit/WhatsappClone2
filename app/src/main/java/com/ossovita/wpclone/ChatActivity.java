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

import com.parse.FindCallback;
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
        chatListView.setAdapter(aa);
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

        //Bizden giden ve oradan gelen mesaj objelerini alır ve zamana göre sıralar
        ParseQuery<ParseObject> query = ParseQuery.or(queries);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null&&objects.size()>0){
                    messages.clear();
                    for(ParseObject message : objects){
                        /*mesaj objelerinden mesajı alır ve eğer bu bizim gödnerdiğimiz mesaj ise
                        başına > işareti koyarak belli ediyoruz                 */
                       String messageContent = message.getString("message");
                       if(message.getString("sender").equals(ParseUser.getCurrentUser().getUsername())){
                           messageContent = "> "+messageContent;
                       }//tüm mesaj metinlerini alıp diziye atıyoruz
                        messages.add(messageContent);
                    }
                    aa.notifyDataSetChanged();
                }
            }
        });





    }

    public void sendChat(View v){
        //Message classı içerisine bir obje gönderiyoruz
        ParseObject message = new ParseObject("Message");
        String messageContent = chatEditText.getText().toString();
        message.put("sender", ParseUser.getCurrentUser().getUsername());
        message.put("recipient",activeUser);
        message.put("message",messageContent);

        chatEditText.setText("");
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    //Yazdığımız mesajın direkt olarak buraya listeye düşmesi için adapteri notify ediyoruz
                   messages.add(messageContent);
                   aa.notifyDataSetChanged();
                }
            }
        });
    }
}