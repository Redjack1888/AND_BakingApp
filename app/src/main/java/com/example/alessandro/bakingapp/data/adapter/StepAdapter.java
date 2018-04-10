package com.example.alessandro.bakingapp.data.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alessandro.bakingapp.R;
import com.example.alessandro.bakingapp.data.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

// Class that manages the list of recipe preparation steps (RecyclerView)
public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private final Context context;
    private final List<Step> steps;
    private final StepOnClickHandler clickHandler;
    private int selectedRowIndex = 0;

    public StepAdapter(Context context, List<Step> steps, StepOnClickHandler clickHandler) {
        this.context = context;
        this.steps = steps;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_step_list_item,
                parent,
                false
        );
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepViewHolder holder, int position) {
        if (steps == null) {
            return;
        }

        if (context.getResources().getBoolean(R.bool.isTablet)) {
            if (selectedRowIndex == position) {
                holder.layoutStep.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                holder.textViewStep.setTextColor(ContextCompat.getColor(context, R.color.colorTextBlack));
                holder.imageViewStep.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_chevron_right_black));
            } else {
                holder.layoutStep.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTheme));
                holder.textViewStep.setTextColor(ContextCompat.getColor(context, R.color.colorTextDefault));
                holder.imageViewStep.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_chevron_right));

            }
        }

        Step step = steps.get(position);
        holder.textViewStep.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (steps == null) {
            return 0;
        }
        return steps.size();
    }

    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    public interface StepOnClickHandler {
        void onClick(int position);
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.layout_step)
        ConstraintLayout layoutStep;

        @BindView(R.id.text_view_step)
        TextView textViewStep;

        @BindView(R.id.image_view_step)
        ImageView imageViewStep;

        StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickHandler.onClick(getAdapterPosition());
        }
    }
}
