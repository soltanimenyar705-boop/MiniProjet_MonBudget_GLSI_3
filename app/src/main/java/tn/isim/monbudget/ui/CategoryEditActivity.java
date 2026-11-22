package tn.isim.monbudget.ui;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import tn.isim.monbudget.data.entity.Category;
import tn.isim.monbudget.databinding.ActivityCategoryEditBinding;
import tn.isim.monbudget.viewmodel.CategoryViewModel;

public class CategoryEditActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCategoryEditBinding b = ActivityCategoryEditBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        CategoryViewModel vm = new ViewModelProvider(this).get(CategoryViewModel.class);

        b.btnSave.setOnClickListener(v -> {
            String name = b.etName.getText().toString().trim();
            String budgetStr = b.etBudget.getText().toString().trim();

            if (name.isEmpty() || budgetStr.isEmpty()) {
                Toast.makeText(this, "Remplissez tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }
            double budget = Double.parseDouble(budgetStr);
            vm.insert(new Category(name, budget));
            finish();
        });

        // Le bouton supprimer est dans le layout XML (Figure 21)
        // On le gère ici (si tu as ajouté le bouton btnDelete dans le XML)
        if (b.btnDelete != null) {
            b.btnDelete.setOnClickListener(v -> {
                Toast.makeText(this, "Appui long sur une catégorie pour la supprimer", Toast.LENGTH_SHORT).show();
            });
        }
    }
}