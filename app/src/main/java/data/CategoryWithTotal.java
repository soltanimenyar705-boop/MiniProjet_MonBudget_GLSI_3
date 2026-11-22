package tn.isim.monbudget.data;
// Sprint 1 - Entité Category
import androidx.room.Embedded;
import tn.isim.monbudget.data.entity.Category;

public class CategoryWithTotal {

    @Embedded
    public Category category;

    public double total;

    public CategoryWithTotal(Category category, double total) {
        this.category = category;
        this.total = total;
    }
}