package tn.isim.monbudget.ui.adapters;
// Sprint 2 - Dépenses
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Locale;
import tn.isim.monbudget.R;
import tn.isim.monbudget.data.CategoryWithTotal;
import tn.isim.monbudget.databinding.ItemCategoryBinding;

public class CategoryAdapter extends ListAdapter<CategoryWithTotal, CategoryAdapter.VH> {

    public interface OnItemClick { void onClick(CategoryWithTotal c); }
    public interface OnItemLongClick { void onLongClick(CategoryWithTotal c); }

    private final OnItemClick listener;
    private final OnItemLongClick longListener;

    public CategoryAdapter(OnItemClick l, OnItemLongClick ll) {
        super(DIFF);
        this.listener = l;
        this.longListener = ll;
    }

    static final DiffUtil.ItemCallback<CategoryWithTotal> DIFF = new DiffUtil.ItemCallback<CategoryWithTotal>() {
        @Override public boolean areItemsTheSame(CategoryWithTotal a, CategoryWithTotal b) { return a.category.id == b.category.id; }
        @Override public boolean areContentsTheSame(CategoryWithTotal a, CategoryWithTotal b) {
            return a.category.name.equals(b.category.name) && a.category.budget == b.category.budget && a.total == b.total;
        }
    };

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new VH(ItemCategoryBinding.inflate(LayoutInflater.from(p.getContext()), p, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        CategoryWithTotal item = getItem(pos);
        double budget = item.category.budget;
        double total = item.total;
        double solde = budget - total;

        h.b.tvCategoryName.setText(item.category.name);
        h.b.tvBudget.setText(String.format(Locale.getDefault(), "Budget : %.2f DT", budget));
        h.b.tvTotal.setText(String.format(Locale.getDefault(), "Total : %.2f DT", total));
        h.b.tvSolde.setText(String.format(Locale.getDefault(), "Solde : %.2f DT", solde));

        // Vrai calcul de la barre de progression
        int progress = (int) ((total / budget) * 100);
        h.b.progressBar.setProgress(Math.min(progress, 100));

        if (solde < 0) {
            h.b.tvSolde.setTextColor(ContextCompat.getColor(h.itemView.getContext(), R.color.red_solde));
            h.b.tvSolde.setBackgroundResource(R.drawable.bg_badge_red);
            h.b.progressBar.setProgressTintList(ContextCompat.getColorStateList(h.itemView.getContext(), R.color.red_solde));
        } else {
            h.b.tvSolde.setTextColor(ContextCompat.getColor(h.itemView.getContext(), R.color.green_solde));
            h.b.tvSolde.setBackgroundResource(R.drawable.bg_badge_green);
        }

        h.b.getRoot().setOnClickListener(v -> listener.onClick(item));
        h.b.getRoot().setOnLongClickListener(v -> {
            longListener.onLongClick(item);
            return true;
        });
    }

    static class VH extends RecyclerView.ViewHolder {
        final ItemCategoryBinding b;
        VH(ItemCategoryBinding b) { super(b.getRoot()); this.b = b; }
    }
}