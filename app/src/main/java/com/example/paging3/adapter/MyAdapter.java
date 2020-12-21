package com.example.paging3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paging3.R;
import com.example.paging3.model.Data;

import org.jetbrains.annotations.NotNull;

public class MyAdapter extends PagingDataAdapter<Data, MyAdapter.Holder> {


	public MyAdapter(@NotNull DiffUtil.ItemCallback<Data> diffCallback) {
		super(diffCallback);
	}

	@NonNull
	@Override
	public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull Holder holder, int position) {
		Data data=getItem(position);
		holder.title.setText(data.getTitle());
		holder.content.setText(data.getContent());
	}

	class Holder extends RecyclerView.ViewHolder {

		TextView title, content;

		public Holder(@NonNull View itemView) {
			super(itemView);
			title = itemView.findViewById(R.id.title);
			content = itemView.findViewById(R.id.content);
		}
	}
}