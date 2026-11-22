package tn.isim.monbudget.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;
import tn.isim.monbudget.data.entity.Expense;

@Dao
public interface ExpenseDao {
    @Insert long insert(Expense e);
    @Update void update(Expense e);
    @Delete void delete(Expense e);
    @Query("SELECT * FROM expenses WHERE categoryId = :catId ORDER BY date DESC") LiveData<List<Expense>> getByCategory(int catId);
    @Query("SELECT COALESCE(SUM(amount),0) FROM expenses WHERE categoryId = :catId") LiveData<Double> totalByCategory(int catId);
}