package tn.isim.monbudget.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import tn.isim.monbudget.data.dao.ExpenseDao;
import tn.isim.monbudget.data.database.AppDatabase;
import tn.isim.monbudget.data.entity.Expense;

public class ExpenseViewModel extends AndroidViewModel {
    private final ExpenseDao dao;
    private final ExecutorService io = Executors.newSingleThreadExecutor();

    public ExpenseViewModel(@NonNull Application app) {
        super(app);
        dao = AppDatabase.get(app).expenseDao();
    }

    public LiveData<List<Expense>> byCategory(int catId) { return dao.getByCategory(catId); }
    public LiveData<Double> total(int catId) { return dao.totalByCategory(catId); }

    public void insert(Expense e) { io.execute(() -> dao.insert(e)); }
    public void delete(Expense e) { io.execute(() -> dao.delete(e)); }
}