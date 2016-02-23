package com.sys1yagi.android.rxrecyclerview_load_more.view;

import com.sys1yagi.android.rxrecyclerview_load_more.databinding.RecyclerViewItemBinding;
import com.sys1yagi.android.rxrecyclerview_load_more.model.Item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerViewItemBinding binding;

        public ViewHolder(RecyclerViewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    List<Item> items = new ArrayList<>();

    public void addAll(List<Item> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewItemBinding binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.binding.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
