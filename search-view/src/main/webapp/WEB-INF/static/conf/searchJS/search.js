$(document).ready(function(){
	load();
	
});
	function load(){
		var text=sessionStorage.searchText;
		if(text!=null){
			$("#search_text").val(text);
		}
		//sessionStorage.page=1;
		var currPage = sessionStorage.currPage;
		var findCount=sessionStorage.findCount;
		var count=sessionStorage.count;
		toSearch(text,currPage);
		
	}
	
	
	function disableEnter(event){
		var keyCode = event.keyCode?event.keyCode:event.which?event.which:event.charCode;
			if (keyCode ==13){
				event.keyCode=0;
				event.returnValue   =   false;
			// 此处处理回车动作
				search(0);
			}
		}
	
	/*function pageNoLast(){
		var no=sessionStorage.page;
		if(no>1){
			no=no-1;
			sessionStorage.page=no;
		}
		search(no);
	}
	
	function pageNoUp(){
		var no=sessionStorage.page;
		no=no+1;
		sessionStorage.page=no;
		search(no);
	}*/
	
	
	
	function toSearch(text,currPage){
		$.ajax({ 
	        type: "post", 
	       url: prod.localhost+"/searchPage", 
	       cache:false, 
	       async:true, 
	       data:{"condition":text,"currPage":currPage},
	        dataType:"json", 
	         success: function(data){ 

	 			if(data.result==true){
	 				var page=data.page.result;
	 				var advert=data.advert;
	 				var content="";
	 				if(page!=null){
	 					for(var i=0;i<page.length;i++){
	 						var p=page[i];
	 						if(p!=null&&page!=""){
	 							var text2=p.text;
	 							/*if(text2.length>388){
	 								text2=text2.substring(0,388-1);
	 							}*/
	 							var str='<div class="callout callout-info">'+
	 										'<div class="searchItem"> '+
	 											 '<h3 class="searchItemTitle"> <a target="_blank" href="'+p.url+'">'+p.title+'</a> </h3> '+
	 											 '<span class="searchCon"> '+text2+' </span>'+ 
	 											 '<div class="searchItemInfo"> '+
	 											 '</div> '+
	 											 '<div class="searchItemInfo"> '+
	 											  '<a class="searchURL" href="'+p.url+'" >'+p.url+'</a> '+
	 										 	'</div> '+
	 										'</div>'+
	 									"</div>";
	 							content=content+str;
	 						}
	 					}
	 					
	 				}
	 					sessionStorage.searchText=text;
	 					sessionStorage.currPage=data.page.pageNo;
	 					sessionStorage.findCount=false;
	 					sessionStorage.count=data.page.totalCount;
	 					var pageNo = data.page.pageNo;
	 					var totalPage=data.page.totalPage;
	 					var totalCount=data.page.totalCount;
	 					if(data.page==null||data.page.totalCount==null||totalCount==null||totalCount<0){
	 						totalCount =0;
	 					}
	 					if(data.page==null||data.page.pageNo==null||pageNo==null||pageNo<0){
	 						pageNo =0;
	 					}
	 					if(data.page==null||data.page.totalPage==null||totalPage==null||totalPage<0){
	 						totalPage =0;
	 					}
	 					content=content+
	 					'<div>'+
	 						'<div style="float: left;margin-top: 26px;font-size: 10pt;">'+
	 						 ' 共<i id="pageTotalCount">'+totalCount+'</i>条记录&nbsp; 第'+
	 						  '<i id="pageNum">'+pageNo+'</i>'+
	 						  '页/共<i id="pageCount">'+totalPage+'</i>页 '+
	 						 '</div>'+
	 						'<ul id="ulPage" class="pagination pagination-sm" data-function="" '+
	 					            	'data-count="" data-totalCount="" style="float: right;margin-right: 10px;margin-top: 25px;">'+
	 					         '<li class="disabled" id="upPage" onclick="jumpPage(this)" pageIndex="1"><a href="#">&laquo;</a></li>'+
	 					         '<li onclick="jumpPage(this)" pageIndex=""><a href="#">0</a></li>'+
	 					         '<li id="nextPage" onclick="jumpPage(this)" pageIndex="2"><a href="#">&raquo;</a></li>'+   	
	 					    '</ul>'+        	
	 					'</div>';
	 					$("#contentHtml").html(content);
	 					disposePageNum(pageNo, totalPage);
	 				
	 			}else{
	 				alert(data.msg);
	 			}
	 		
	        } 

	});
	}
	
	function search(currPage){
		var oldText=sessionStorage.searchText;
		var text=$("#search_text").val();
		sessionStorage.searchText=text;
		var count=sessionStorage.count;
		var findCount=true;
		
		if(currPage==null||currPage=='undefind'||currPage<0){
			currPage==1;
		}
//		sessionStorage.searchText=text;
		toSearch(text,currPage);
	}
	
	function jumpPage(obj){
		var pageNo=$(obj).attr("pageIndex");
		search(pageNo);
	}
	
	
	/**
	 * 处理上下页按钮
	 * @param pageNo 当前页
	 * @param count  总页数
	 */
	function disposePageNum(pageNo,count) {
		var up=0;
		var next=0;
		var countNum=8;
		if(count-pageNo>=4){
			if(pageNo-4<0){
				up=1;
				if(count>countNum){
					next=countNum;
				}else{
					next=count;
				}
			}else{
				up=pageNo-4;
				next=pageNo+4;
			}
			
		}else{
			up=count-countNum;
			if(up<1){
				up=1;
			}
			next=count;
		}
		if(up>0&&next<=count){
			var li='<li id="upPage" onclick="jumpPage(this)" pageIndex="'+(pageNo-1)+'"><a href="#">&laquo;</a></li>';
			for(var i=up; i<=next;i++){
				if(i==pageNo){
					li=li+'<li onclick="jumpPage(this)" class="disabled" pageIndex="'+i+'"><a href="#">'+i+'</a></li>';
				}else{
					li=li+'<li onclick="jumpPage(this)" pageIndex="'+i+'"><a href="#">'+i+'</a></li>';
				}
			}
			li=li+'<li id="nextPage" onclick="jumpPage(this)" pageIndex="'+(pageNo+1)+'"><a href="#">&raquo;</a></li>';
			$("#ulPage").html(li);
		}else{
			var $li=$("#ulPage").find(".disabled");
			$li.removeClass("disabled");
			var $li2=$("#ulPage").find("li");
			for(var i=0;i<$li2.length;i++){
				var index=$($li2[i]).attr("pageIndex");
				index=Number(index);
				if(index==pageNo){
					$($li2[i]).addClass("disabled");
				}
			}
			
		}
	}