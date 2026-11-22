package tn.isim.monbudget.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import tn.isim.monbudget.data.CategoryWithTotal;
import tn.isim.monbudget.databinding.ActivityCategoryListBinding;
import tn.isim.monbudget.ui.adapters.CategoryAdapter;
import tn.isim.monbudget.util.Langue;
import tn.isim.monbudget.viewmodel.CategoryViewModel;

public class CategoryListActivity extends AppCompatActivity {
    private CategoryViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCategoryListBinding b = ActivityCategoryListBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        vm = new ViewModelProvider(this).get(CategoryViewModel.class);

        CategoryAdapter adapter = new CategoryAdapter(
                item -> {
                    Intent i = new Intent(this, ExpenseListActivity.class);
                    i.putExtra("categoryId", item.category.id);
                    i.putExtra("categoryName", item.category.name);
                    i.putExtra("categoryBudget", item.category.budget);
                    startActivity(i);
                },
                item -> {
                    new AlertDialog.Builder(this)
                            .setTitle(Langue.t(this, "Supprimer", "Delete"))
                            .setMessage(Langue.t(this, "Voulez-vous supprimer ", "Delete ") + item.category.name + " ?")
                            .setPositiveButton(Langue.t(this, "Oui", "Yes"), (dialog, which) -> vm.delete(item.category))
                            .setNegativeButton(Langue.t(this, "Non", "No"), null)
                            .show();
                }
        );

        b.recycler.setLayoutManager(new LinearLayoutManager(this));
        b.recycler.setAdapter(adapter);

        // Traduction du panneau de droite (uniquement s'il existe)
        if (b.tvGlobalTitle != null) {
            b.tvGlobalTitle.setText(Langue.t(this, "Solde du mois", "Monthly balance"));
            b.tvGlobalSubtitle.setText(Langue.t(this, "sur 550 DT de budget", "of 550 DT budget"));
        }

        vm.allWithTotals.observe(this, categories -> {
            adapter.submitList(categories);

            double totalGlobal = 0;
            for (CategoryWithTotal item : categories) {
                totalGlobal += item.total;
            }
            b.tvGlobalTotal.setText(String.format(java.util.Locale.getDefault(), "%.0f DT", totalGlobal));
        });

        b.fab.setOnClickListener(v -> startActivity(new Intent(this, CategoryEditActivity.class)));
    }
}