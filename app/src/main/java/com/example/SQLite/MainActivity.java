package com.example.SQLite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openHelper = new MyOpenHelper(this);
        // getReadableDatabase 和getWritableDatabase 大部分情况下作用是相同的
        // 创建（数据库不存在）或者 打开一个数据库 可读可写
        // 当磁盘满， getReadableDatabase 返回只读数据库， 和getWritableDatabase出错
        // SQLiteDatabase database = openHelper.getReadableDatabase();

    }
    public void insert(View v) {
        // 获取到sqlitedatabase
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql = "insert into info (name, phone, age)values('zhangsan', '156165', 30)";
        // 通过 sqlitedatabase可以直接执行sql语句
        database.execSQL(sql);
        sql = "insert into info (name, phone, age)values('lisi', '15555', 20)";
        database.execSQL(sql);
        database.close();
    }
    public void query(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql = "select * from info";
        Cursor cursor = database.rawQuery(sql, null);
        // 查询返回一个游标 curson ，可以访问整个结果集
        // 游标默认也是指向所有结果之前的一行 可以通过 moveToNext() 方法移动游标
        while(cursor.moveToNext()) {
            // 可以通过列的索引取出当前行某一列的值， 需要注意列索引从 0 开始
            String name = cursor.getString(1);
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            System.out.println("name="+name+"phone="+phone);
        }
        cursor.close();
        database.close();
    }
    public void delete(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql = "delete from info where name = 'lisi'";
        database.execSQL(sql);
        database.close();
    }
    public void update(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String sql = "update info set phone = '123456' where name = 'zhangsan'";
        database.execSQL(sql);
        database.close();
    }
//      google API
    public void insert1(View v) {
        // "insert into info (name, phone, age)values('zhangsan', '156165', 30)";
        SQLiteDatabase database = openHelper.getReadableDatabase();
        String nullColumnHack = null;
        // 拼出要添加的数据
        ContentValues values = new ContentValues();
        values.put("name", "lisi");
        values.put("phone", "111122223");
        values.put("age", 25);
        long id = database.insert("info", nullColumnHack, values);
        if (id != -1) {
            Toast.makeText(this, "当前插入到了第" + id + "行", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "插入失败", Toast.LENGTH_SHORT).show();
        }
        database.close();
    }
    public void delete1(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        // 表名字
        String table = "info";
        // where 的条件， 注意这里不能包含 where 关键字= 后面的条件用 ？ 替换
        String whereClause = "name = ?";
        // 满足条件的值  替换 ？
        String[] whereArgs = {"lisi"};
        int delete = database.delete(table, whereClause, whereArgs);
        Toast.makeText(this, "删除了"+delete+"条数据", Toast.LENGTH_SHORT).show();
        database.close();
    }
    public void update1(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", "110");
        // 第一个参数表名
        // 第二个参数 ContentValues 用来封装 set 后面传入需要的值
        // 第三个参数 where
        // 返回更新了几个
        int update = database.update("info", values, "name = ?", new String[]{"zhangsan"});
        Toast.makeText(this, "更新了"+update+"个", Toast.LENGTH_SHORT).show();
        database.close();
    }
    public void query1(View v) {
        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query("info", new String[] {"name", "phone"}, "name = ?", new String[] {"zhangsan"}, null, null,null,null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String phone = cursor.getString(1);
            System.out.println("name"+ name+"phone ="+ phone);
        }
        cursor.close();
        database.close();
    }
}
