package com.example.paging3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.example.paging3.BaseDataSource;
import com.example.paging3.ExecutorBacken;
import com.example.paging3.model.Data;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;

import kotlinx.coroutines.CoroutineScope;

public class DataViewModel extends ViewModel {


	PagingConfig pagingConfig=new PagingConfig(20,10,false,20);//初始化配置
	public ListeningExecutorService executorService= MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

	public LiveData<PagingData<Data>> getPaging(){
		CoroutineScope viewmodelScope= ViewModelKt.getViewModelScope(this);
		Pager<Integer, Data> pager = new Pager<Integer,Data>(pagingConfig, ()->new BaseDataSource<>(new ExecutorBacken(executorService)));
		return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager),viewmodelScope);
	}

}
