package com.example.paging3;

import androidx.paging.ListenableFuturePagingSource;

import com.example.paging3.idal.LoadDataInterface;
import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



//基本的列表加载类
public class BaseDataSource<T> extends ListenableFuturePagingSource<Integer, T> {
	private ExecutorService pool= Executors.newCachedThreadPool();//使用线程池
	LoadDataInterface<T> loadDataInterface; //加载数据的接口
	public BaseDataSource(LoadDataInterface<T> loadDataInterface){
		this.loadDataInterface=loadDataInterface;
	}
	@NotNull
	@Override
	public ListenableFuture<LoadResult<Integer, T>> loadFuture(@NotNull LoadParams<Integer> params) {
		Integer nextPageNumber = params.getKey();
		if (nextPageNumber == null) {
			nextPageNumber = 1;//从第1页开始加载
		}
		Integer finalNextPageNumber = nextPageNumber;
		ListenableFuture<LoadResult<Integer, T>> pageFuture = Futures
				.transform(loadDataInterface.load(finalNextPageNumber), (Function<List<T>, LoadResult.Page<Integer, T>>) input -> {
//					这里传入的三个参数中,刚才请求的数据,第二个参数为请求的上一页的页数,当为null时不再加载上一页,第三个参数则是下一页,后两个参数不介绍,自行了解
					if (input==null){
						input=new ArrayList<>();
					}
//					load()经load得到数据后,将数据传回,返回加载结果
					return new LoadResult.Page<>(input,finalNextPageNumber==1?null:finalNextPageNumber-1,input.isEmpty()?null:finalNextPageNumber+1);
				}, pool);

		ListenableFuture<LoadResult<Integer,T>> partialLoadResultFuture = Futures.catching(
				pageFuture, Exception.class,
				LoadResult.Error::new, pool);

		return Futures.catching(partialLoadResultFuture,
				Exception.class, LoadResult.Error::new, pool);
	}
}
