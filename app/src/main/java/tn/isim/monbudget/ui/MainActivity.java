package tn.isim.monbudget.ui;
// Sprint 2 - Navigation
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import tn.isim.monbudget.databinding.ActivityMainBinding;
import tn.isim.monbudget.util.PrefsManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        String nom = PrefsManager.getUserName(this);

        // On garde uniquement tvHello qui existe déjà dans ton XML
        b.tvHello.setText("Bonjour " + nom);

        b.btnCategories.setOnClickListener(v -> startActivity(new Intent(this, CategoryListActivity.class)));
        b.btnSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }
}