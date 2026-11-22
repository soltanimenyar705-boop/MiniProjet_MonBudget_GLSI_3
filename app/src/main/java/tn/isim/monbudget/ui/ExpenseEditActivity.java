package tn.isim.monbudget.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import tn.isim.monbudget.data.entity.Expense;
import tn.isim.monbudget.databinding.ActivityExpenseEditBinding;
import tn.isim.monbudget.util.Langue;
import tn.isim.monbudget.viewmodel.ExpenseViewModel;

public class ExpenseEditActivity extends AppCompatActivity {
    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityExpenseEditBinding b = ActivityExpenseEditBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        int catId = getIntent().getIntExtra("categoryId", -1);
        ExpenseViewModel vm = new ViewModelProvider(this).get(ExpenseViewModel.class);

        // Traduction des champs
        b.etLabel.setHint(Langue.t(this, "Libellé", "Label"));
        b.etAmount.setHint(Langue.t(this, "Montant (DT)", "Amount (DT)"));
        b.etDate.setHint(Langue.t(this, "Date", "Date"));
        b.btnSave.setText(Langue.t(this, "Enregistrer", "Save"));
        b.btnDelete.setText(Langue.t(this, "Supprimer", "Delete"));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        b.etDate.setText(sdf.format(new Date()));

        b.etDate.setOnClickListener(v -> {
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                b.etDate.setText(sdf.format(calendar.getTime()));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        b.btnSave.setOnClickListener(v -> {
            String label = b.etLabel.getText().toString().trim();
            String amountStr = b.etAmount.getText().toString().trim();

            if (label.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(this, Langue.t(this, "Remplissez tous les champs", "Fill all fields"), Toast.LENGTH_SHORT).show();
                return;
            }
            double amount = Double.parseDouble(amountStr);
            long dateTimestamp = calendar.getTimeInMillis();

            vm.insert(new Expense(label, amount, dateTimestamp, catId));
            finish();
        });

        b.btnDelete.setOnClickListener(v -> {
            Toast.makeText(this, Langue.t(this, "Suppression non disponible pour l'ajout", "Delete not available for add"), Toast.LENGTH_SHORT).show();
        });
    }
}