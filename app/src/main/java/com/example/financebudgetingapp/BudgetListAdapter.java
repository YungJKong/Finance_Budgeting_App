package com.example.financebudgetingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.financebudgetingapp.model.Budget;

import java.util.List;

public class BudgetListAdapter extends ArrayAdapter<Budget> {

    private Context context;
    private List<Budget> budgets;

    public BudgetListAdapter(Context context, List<Budget> budgets) {
        super(context, R.layout.budget_list_item, budgets);
        this.context = context;
        this.budgets = budgets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.budget_list_item, parent, false);

        // Get references to views in the budget_list_item.xml layout
        TextView categoryTextView = rowView.findViewById(R.id.categoryTextView);
        TextView budgetAmountTextView = rowView.findViewById(R.id.budgetAmountTextView);
        TextView budgetLeftTextView = rowView.findViewById(R.id.budgetLeftTextView);

        // Get the Budget object at the current position
        Budget budget = budgets.get(position);

        // Set data to the views
        categoryTextView.setText(budget.getCategory());
        budgetAmountTextView.setText(String.format("%.2f", budget.getAmount()));
        budgetLeftTextView.setText(String.format("%.2f", budget.getBudgetLeft()));

        return rowView;
    }
}
