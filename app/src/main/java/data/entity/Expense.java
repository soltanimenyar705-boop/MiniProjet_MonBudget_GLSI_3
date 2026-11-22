package tn.isim.monbudget.data.entity;
// Sprint 1 - Expense
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "expenses",
        foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "categoryId", onDelete = ForeignKey.CASCADE),
        indices = {@Index("categoryId")})
public class Expense {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String label;
    public double amount;
    public long date;
    public int categoryId;

    public Expense(String label, double amount, long date, int categoryId) {
        this.label = label;
        this.amount = amount;
        this.date = date;
        this.categoryId = categoryId;
    }
}