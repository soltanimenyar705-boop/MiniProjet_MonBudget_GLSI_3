package tn.isim.monbudget.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import tn.isim.monbudget.data.dao.*;
import tn.isim.monbudget.data.entity.*;

@Database(entities = {Category.class, Expense.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    public abstract CategoryDao categoryDao();
    public abstract ExpenseDao expenseDao();

    public static AppDatabase get(Context ctx) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(), AppDatabase.class, "budget.db").build();
                }
            }
        }
        return INSTANCE;
    }
}