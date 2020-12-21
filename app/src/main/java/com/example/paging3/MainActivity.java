package com.example.paging3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.paging3.adapter.MyAdapter;
import com.example.paging3.model.Data;
import com.example.paging3.viewmodel.DataViewModel;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RecyclerView recyclerView=findViewById(R.id.m_recycler);
		MyAdapter listAdapter=new MyAdapter(new DiffUtil.ItemCallback<Data>() {
			@Override
			public boolean areItemsTheSame(@NonNull Data oldItem, @NonNull Data newItem) {
				return oldItem.getTitle().equals(newItem.getTitle());
			}

			@Override
			public boolean areContentsTheSame(@NonNull Data oldItem, @NonNull Data newItem) {
				return oldItem.getContent().equals(newItem.getContent());
			}
		});
		recyclerView.setAdapter(listAdapter);
		DataViewModel dataViewModel=new ViewModelProvider(this).get(DataViewModel.class);
		dataViewModel.getPaging().observe(this, dataPagingData -> listAdapter.submitData(getLifecycle(),dataPagingData));
	}


}