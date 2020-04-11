package com.example.SQLite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    private MyOpenHelper openHelper;
    private EditText et_user;
    private EditText et_pwd;
    private EditText et_find;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        openHelper = new MyOpenHelper(this);

        et_user = (EditText)findViewById(R.id.username);
        et_pwd = (EditText)findViewById(R.id.password);
        et_find = (EditText)findViewById(R.id.findUser);

    }
    public void register(View v){
        String username = et_user.getText().toString();
        String password = et_pwd.getText().toString();
        SQLiteDatabase database = openHelper.getReadableDatabase();
        //String sql = "insert into info (name, phone, age)values('zhangsan', '156165', 30)";
        String sql = "insert into userInfo (name, pwd) values('"+username+"','"+password+"')";
        database.execSQL(sql);
        database.close();
    }
    public void login(View v){
        String username = et_user.getText().toString();
        String password = et_pwd.getText().toString();
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql = "select * from userInfo where name = '"+username+"'";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String pwd = cursor.getString(cursor.getColumnIndex("pwd"));
            if (password.equals(pwd)) {
                Toast.makeText(this, "成功登录 用户："+username, Toast.LENGTH_LONG).show();
                startActivity(new Intent(Main2Activity.this, AVplayer.class));
            }
        }
    }
    public void findPwd(View v){
        String username = et_find.getText().toString();
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql = "select * from userInfo where name = '"+username+"'";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String pwd = cursor.getString(cursor.getColumnIndex("pwd"));
            Toast.makeText(this,"密码为"+pwd, Toast.LENGTH_LONG).show();
        }
    }

    class MyOpenHelper extends SQLiteOpenHelper {
        public MyOpenHelper(Context context) {
            super(context, "user.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table userInfo(_id integer primary key autoincrement,name varchar(20), pwd varchar(20))");
            System.out.println("oncreate 调用");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
