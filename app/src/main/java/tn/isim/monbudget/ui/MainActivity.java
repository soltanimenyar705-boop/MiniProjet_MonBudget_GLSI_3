package tn.isim.monbudget.ui;
// Sprint 2 - Navigation
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import tn.isim.monbudget.databinding.ActivityMainBinding;
import tn.isim.monbudget.util.Langue;
import tn.isim.monbudget.util.PrefsManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        String nom = PrefsManager.getUserName(this);

        // Traduction dynamique pour "Bonjour" -> "Hello"
        b.tvHello.setText(Langue.t(this, "Bonjour " + nom, "Hello " + nom));

        // Les autres textes de l'écran d'accueil (si tu les as liés)
        if (b.tvSubtitle != null) {
            b.tvSubtitle.setText(Langue.t(this, "Votre budget du mois", "Your monthly budget"));
            b.tvSoldeMois.setText(Langue.t(this, "Solde du mois", "Monthly balance"));
            b.tvSurBudget.setText(Langue.t(this, "sur 550 DT de budget", "out of 550 DT budget"));
            b.btnCategories.setText(Langue.t(this, "CATÉGORIES", "CATEGORIES"));
            b.btnSettings.setText(Langue.t(this, "RÉGLAGES", "SETTINGS"));
        }

        b.btnCategories.setOnClickListener(v -> startActivity(new Intent(this, CategoryListActivity.class)));
        b.btnSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }
}