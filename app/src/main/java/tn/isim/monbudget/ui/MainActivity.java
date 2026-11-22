package tn.isim.monbudget.ui;

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

        // Traduction du "Bonjour" en "Hello"
        b.tvHello.setText(Langue.t(this, "Bonjour " + nom, "Hello " + nom));

        // Navigation vers les autres écrans
        b.btnCategories.setOnClickListener(v -> startActivity(new Intent(this, CategoryListActivity.class)));
        b.btnSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }
}