package tn.isim.monbudget.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import tn.isim.monbudget.data.CategoryWithTotal;
import tn.isim.monbudget.data.dao.CategoryDao;
import tn.isim.monbudget.data.database.AppDatabase;
import tn.isim.monbudget.data.entity.Category;

public class CategoryViewModel extends AndroidViewModel {
    private final CategoryDao dao;
    private final ExecutorService io = Executors.newSingleThreadExecutor();

    // On utilise le LiveData avec le total réel
    public final LiveData<List<CategoryWithTotal>> allWithTotals;

    public CategoryViewModel(@NonNull Application app) {
        super(app);
        dao = AppDatabase.get(app).categoryDao();
        allWithTotals = dao.getCategoriesWithTotals();
    }

    public void insert(Category c) { io.execute(() -> dao.insert(c)); }
    public void update(Category c) { io.execute(() -> dao.update(c)); }
    public void delete(Category c) { io.execute(() -> dao.delete(c)); }
}