package com.ossovita.wpclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> users = new ArrayList<>();
    ArrayAdapter aa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setTitle("User List");
        listView = findViewById(R.id.userListView);
        aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1,users);
        listView.setAdapter(aa);

        ParseQuery<ParseUser> query = ParseUser.getQuery();//ParseUser objesi arayacak sorgu objesi oluşturduk
        query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null&&objects.size()>0){//hata yoksa ve gelen obje varsa
                    for(ParseUser user : objects){
                        users.add(user.getUsername());
                    }
                    aa.notifyDataSetChanged();
                }else{

                }
            }
        });


    }
}