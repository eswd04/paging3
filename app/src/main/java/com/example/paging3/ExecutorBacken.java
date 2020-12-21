package com.example.paging3;


import com.example.paging3.idal.LoadDataInterface;
import com.example.paging3.model.Data;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import java.util.LinkedList;
import java.util.List;

//获取评论列表
public class ExecutorBacken implements LoadDataInterface<Data> {
	ListeningExecutorService executorService;
	public ExecutorBacken(ListeningExecutorService executorService) {
		this.executorService=executorService;
	}
	@Override
	public ListenableFuture<List<Data>> load(int pageNum) {
		return executorService.submit(() -> {
			List<Data> datas=new LinkedList<>();
			for (int i=1;i<=20;i++){
				datas.add(new Data("标题:"+((pageNum-1)*20+i),"内容:"+((pageNum-1)*20+i)));
			}
			long t=(long) (Math.random()*500);
			Thread.sleep(t); //模拟耗时操作
			return datas;
		});
	}

}
