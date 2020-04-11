package com.example.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        // 1, 第一参数上下文
        // 2，数据库名字，传入null 就是在内存中创建一个数据库
        // 3，游标工厂，如果使用系统默认的游标工厂就传入null
        // 4，数据库的版本号，用版本号来控制数据库的升级和降级
        super(context, "yb.db", null, 3);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 当数据库文件第一次创建的时候 会调用这个方法，此方法中进行表结构创建和数据初始化
        // _id  sqlite 中 id这一列用 _id
        // sqlite 数据库存的都是字符串
        db.execSQL("create table info(_id integer primary key autoincrement,name varchar(20), phone varchar(20))");
        System.out.println("oncreate 调用");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 当 version 变大时调用
        // 表结构的修改 （添加字段）
        // db.execSQL("alter table info add age integer");
        // 添加新的表格
        System.out.println("oldVersion"+ oldVersion + "newVersion" + newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
