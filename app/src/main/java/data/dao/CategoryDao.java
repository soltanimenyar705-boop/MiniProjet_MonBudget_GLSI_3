package tn.isim.monbudget.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;
import tn.isim.monbudget.data.CategoryWithTotal;
import tn.isim.monbudget.data.entity.Category;

@Dao
public interface CategoryDao {
    @Insert long insert(Category c);
    @Update void update(Category c);
    @Delete void delete(Category c);

    @Query("SELECT * FROM categories ORDER BY name ASC")
    LiveData<List<Category>> getAll();

    @Query("SELECT * FROM categories WHERE id = :id")
    LiveData<Category> getById(int id);

    // Requête pour calculer le total réel de chaque catégorie (pour la barre de progression)
    @Query("SELECT c.*, (SELECT COALESCE(SUM(amount),0) FROM expenses WHERE categoryId = c.id) as total FROM categories c ORDER BY c.name ASC")
    LiveData<List<CategoryWithTotal>> getCategoriesWithTotals();
}