package tn.isim.monbudget.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.Locale;
import tn.isim.monbudget.databinding.ActivityExpenseListBinding;
import tn.isim.monbudget.ui.adapters.ExpenseAdapter;
import tn.isim.monbudget.util.Langue;
import tn.isim.monbudget.viewmodel.ExpenseViewModel;

public class ExpenseListActivity extends AppCompatActivity {
    private int catId;
    private String catName;
    private double catBudget;
    private double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityExpenseListBinding b = ActivityExpenseListBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        catId = getIntent().getIntExtra("categoryId", -1);
        catName = getIntent().getStringExtra("categoryName");
        catBudget = getIntent().getDoubleExtra("categoryBudget", 0);

        if (catId == -1) { finish(); return; }

        ExpenseViewModel vm = new ViewModelProvider(this).get(ExpenseViewModel.class);

        ExpenseAdapter adapter = new ExpenseAdapter(
                e -> {
                    // Clic court : Éditer
                    Intent i = new Intent(this, ExpenseEditActivity.class);
                    i.putExtra("expenseId", e.id);
                    i.putExtra("categoryId", catId);
                    startActivity(i);
                },
                e -> {
                    // Appui long : Supprimer
                    new AlertDialog.Builder(this)
                            .setTitle(Langue.t(this, "Supprimer", "Delete"))
                            .setMessage(Langue.t(this, "Voulez-vous supprimer cette dépense ?", "Delete this expense?"))
                            .setPositiveButton(Langue.t(this, "Oui", "Yes"), (dialog, which) -> vm.delete(e))
                            .setNegativeButton(Langue.t(this, "Non", "No"), null)
                            .show();
                }
        );

        b.recycler.setLayoutManager(new LinearLayoutManager(this));
        b.recycler.setAdapter(adapter);

        vm.byCategory(catId).observe(this, adapter::submitList);

        vm.total(catId).observe(this, t -> {
            total = t == null ? 0 : t;
            double solde = catBudget - total;
            String format = Langue.t(this, "%s\nTotal %.2f DT - Solde %.2f DT", "%s\nTotal %.2f DT - Balance %.2f DT");
            b.tvHeader.setText(String.format(Locale.getDefault(), format, catName, total, solde));
        });

        b.fab.setOnClickListener(v -> {
            Intent i = new Intent(this, ExpenseEditActivity.class);
            i.putExtra("categoryId", catId);
            startActivity(i);
        });

        b.btnShare.setText(Langue.t(this, "Partager", "Share"));
        b.btnEmail.setText(Langue.t(this, "E-mail", "E-mail"));

        b.btnShare.setOnClickListener(v -> {
            String msg = String.format(Langue.t(this, "Budget %s : Total %.2f DT, Solde %.2f DT", "Budget %s: Total %.2f DT, Balance %.2f DT"), catName, total, catBudget - total);
            Intent intent = new Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, msg);
            try { startActivity(Intent.createChooser(intent, Langue.t(this, "Partager", "Share"))); }
            catch (ActivityNotFoundException ex) { Toast.makeText(this, Langue.t(this, "Aucune application disponible", "No app available"), Toast.LENGTH_SHORT).show(); }
        });

        b.btnEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_SUBJECT, Langue.t(this, "Bilan ", "Summary ") + catName);
            try { startActivity(intent); }
            catch (ActivityNotFoundException ex) { Toast.makeText(this, Langue.t(this, "Aucune application e-mail", "No email app"), Toast.LENGTH_SHORT).show(); }
        });
    }
}