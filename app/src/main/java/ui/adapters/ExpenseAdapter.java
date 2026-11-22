package tn.isim.monbudget.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import tn.isim.monbudget.data.entity.Expense;
import tn.isim.monbudget.databinding.ItemExpenseBinding;

public class ExpenseAdapter extends ListAdapter<Expense, ExpenseAdapter.VH> {
    public interface OnItemClick { void onClick(Expense e); }
    public interface OnItemLongClick { void onLongClick(Expense e); }

    private final OnItemClick listener;
    private final OnItemLongClick longListener;

    public ExpenseAdapter(OnItemClick l, OnItemLongClick ll) {
        super(DIFF);
        this.listener = l;
        this.longListener = ll;
    }

    static final DiffUtil.ItemCallback<Expense> DIFF = new DiffUtil.ItemCallback<Expense>() {
        @Override public boolean areItemsTheSame(Expense a, Expense b) { return a.id == b.id; }
        @Override public boolean areContentsTheSame(Expense a, Expense b) {
            return a.label.equals(b.label) && a.amount == b.amount && a.date == b.date;
        }
    };

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new VH(ItemExpenseBinding.inflate(LayoutInflater.from(p.getContext()), p, false));
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        Expense e = getItem(pos);
        h.b.tvLabel.setText(e.label);
        h.b.tvAmount.setText(String.format(Locale.getDefault(), "%.2f DT", e.amount));

        // Affichage de la date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());
        h.b.tvDate.setText(sdf.format(new Date(e.date)));

        h.b.getRoot().setOnClickListener(v -> listener.onClick(e));
        h.b.getRoot().setOnLongClickListener(v -> {
            longListener.onLongClick(e);
            return true;
        });
    }

    static class VH extends RecyclerView.ViewHolder {
        final ItemExpenseBinding b;
        VH(ItemExpenseBinding b) { super(b.getRoot()); this.b = b; }
    }
}