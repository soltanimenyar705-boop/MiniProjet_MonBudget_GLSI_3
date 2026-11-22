package tn.isim.monbudget.ui;
// Sprint 3 - Version finale des réglages
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import tn.isim.monbudget.databinding.ActivitySettingsBinding;
import tn.isim.monbudget.util.Langue;
import tn.isim.monbudget.util.PrefsManager;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PrefsManager.isDark(this)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        ActivitySettingsBinding b = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        // Traduction des éléments
        b.etName.setHint(Langue.t(this, "Nom d'utilisateur", "Username"));
        b.switchDark.setText(Langue.t(this, "Thème sombre", "Dark theme"));
        b.btnSave.setText(Langue.t(this, "Enregistrer", "Save"));

        b.etName.setText(PrefsManager.getUserName(this));
        b.switchDark.setChecked(PrefsManager.isDark(this));

        b.btnSave.setOnClickListener(v -> {
            String name = b.etName.getText().toString().trim();
            if (!name.isEmpty()) {
                PrefsManager.setUserName(this, name);
            }
            PrefsManager.setDark(this, b.switchDark.isChecked());
            Toast.makeText(this, Langue.t(this, "Réglages enregistrés !", "Settings saved!"), Toast.LENGTH_SHORT).show();
            finish();
        });

        b.switchDark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            PrefsManager.setDark(this, isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }
}