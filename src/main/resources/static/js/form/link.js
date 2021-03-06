$(function() {
	
	$("#formContainer").on("blur", "input[type='text']", changeFormGroupColor);
	$("#formContainer").on("click", ".btn-submit", submitData);
	$("#formContainer").on("click", ".close", removeThis);
	$("#formContainer").on("click", "#verifyBtn", sendVerify);
	$("#formContainer").on("change","input[type='file']",formUploadFile);
	
	var formName = $("#formContainer").attr("formNum");
	var url = "http://localhost:8100/system/form/findFormInfoByFormName";
	var params={};
	params.formName=formName;
	if(formName){
		$.post(url,params,function(result){
			if(result.code==0){
				$("#formContainer").empty();
				$("#formContainer").append(result.data.formHtml);
				$("#formContainer").attr("useNote",result.data.formNote);
				$("#formContainer").attr("useNoteType",result.data.formNoteType);
				createCss(result.data);
				if(result.data.formNote == 1){
					$("#formContainer").find("input[mark='phone']").each(function (i) {
						$(this).parents(".form-group").after("<div class='form-inline' style='margin-top: 10px;margin-bottom: 10px;'>"
							+"<button type='button' class='btn btn-primary' id='verifyBtn'>"+(result.data.formNoteType == 1? "发送验证码":"发送提取码")+"</button>"
							+"</div>")
					})
				}
				if(result.data.formButton){
					$("#formContainer").find(".btn-submit").text(result.data.formButton);
				}
				$("#formContainer").find("input").each(function () {
					const oldPlaceholder = $(this).attr("placeholder");
                    if(oldPlaceholder && oldPlaceholder.indexOf("姓名")!=-1){
                        $(this).attr("placeholder","已加密，请放心输入您的真实姓名");
                    }
                    if(oldPlaceholder && oldPlaceholder.indexOf("手机")!=-1){
                        $(this).attr("placeholder","已加密，请放心输入您的手机号");
                    }
                    if(oldPlaceholder && oldPlaceholder.indexOf("电话")!=-1){
                        $(this).attr("placeholder","已加密，请放心输入您的电话号码");
                    }
				})
			}else{
				$("#formContainer").empty();
				var errorHtml = "<h1>"+result.msg+"</h1>"
				$("#formContainer").append(errorHtml)
			}
		})
	}else{
		$("#formContainer").empty();
		$("#formContainer").append("formNum属性没有值");
	}
	
	
})
function createCss(formInfo){
	var linkTag1 = $("<link href='http://localhost:8100/css/bootstrap.min.css' rel='stylesheet'/>");
	if($("head").length>0){
		if(formInfo.formStrap==1){
			$("head:first").append(linkTag1);
		}
		if(formInfo.formCss){
			$("head:first").append("<style>\n"+formInfo.formCss+"\n</style>");
		}
		
	}else{
		alert("请将js代码放置在html网页内");
	}
}
function changeFormGroupColor() {
	var length = $(this).prev().find("span").length;
	if(length>0){
		if (!$(this).val().trim()) {
			$(this).parent("div").attr("class", "form-group has-error");
		} else {
			$(this).parent("div").attr("class", "form-group");
		}
	}
}
function removeThis(){
	$(this).parent("div").remove();
}

function submitData() {
	
	
	var params = {};
	var flag = true;
	var lengthHtml = "<p class='help-block' style='color:red;'>内容长度过长.</p>";
	
	$("#formContainer").find(".form-group").each(function(i) {
		
		if ($(this).find("input[type='file']").length == 1) {
			
			var fileName = $(this).find("input[type='file']").attr("id");
			var fileVal = "";
			if( $(this).find("input[mark='photo']").length==1){
				fileVal =  $(this).find("input[type='file']").next().attr("src");
			}else{
				fileVal =  $(this).find("input[type='file']").next().val();
			}
		
			if($(this).find("span").length>0){
				if(!fileVal || fileVal.length<0){
					flag = false;
					$(this).find("p").remove();
					$(this).append("<p class='help-block' style='color:red;'>请上传文件.</p>")
				}else{
					$(this).find("p").remove();
				}
			}
			
			params[fileName]=fileVal;
			
		}

		if ($(this).find("input[type='text']").length == 1) {
			
			var textVal = $(this).find("input[type='text']").val().trim();
			var textName = $(this).find("input[type='text']").attr("id");
			
			
			
			if( $(this).find("input[mark='varchar']").length==1){
				
				if($(this).find("span").length>0){
					if(textVal==""){
						flag = false;
						$(this).find("p").remove();
						$(this).append("<p class='help-block' style='color:red;'>请输入内容.</p>")
					}else{
						$(this).find("p").remove();
					}
				}else{
					$(this).find("p").remove();
				}
				
			}
			
				
				if( $(this).find("input[mark='int']").length==1){
					
					if($(this).find("span").length>0 || textVal.length>0){
						if (!(/(^[0-9]\d*$)/.test(textVal))){
							flag=false;
							var helpHtml = "<p class='help-block' style='color:red;'>请输入数字.</p>";
							$(this).find("p").remove();
							$(this).append(helpHtml);
						}else{
							$(this).find("p").remove();
						}
						
					}else{
						$(this).find("p").remove();
					}
					
					
				}
			
				
				if( $(this).find("input[mark='email']").length==1){
					if($(this).find("span").length>0 || textVal.length>0){
						if (!(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(textVal))){
							flag=false;
							var helpHtml = "<p class='help-block' style='color:red;'>请输入正确的邮箱.</p>";
							$(this).find("p").remove();
							$(this).append(helpHtml);
						}else {
							$(this).find("p").remove();
						}
					}else{
						$(this).find("p").remove();
					}
				}
				
				if( $(this).find("input[mark='phone']").length==1){
					
					if($(this).find("span").length>0  && $("#formContainer").attr("useNote")==1 && $("#formContainer").attr("useNoteType")==1){
						if($("#formContainer").find("input[mark='verify']").length<1){
							alert("请发送验证码！");
							flag=false;
						}else{
							var verifyNum = $("#formContainer").find("input[mark='verify']").val();
							 if(verifyNum){
								 var verifyNumAjax =  $.ajax({
										async:false, 
						                type: "post",
						                url: "http://localhost:8100/form/verifyNum",
						                timeout: 2000,
						                data:{"verifyNum":verifyNum,"phone":textVal},
						                success: function (result) {
						                	 if(result.state!=200){
												 alert("验证码错误");
												 flag=false;
											 }
						                },
						                complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
						            	　　　　if(status=='timeout'){//超时,status还有success,error等值的情况
						            			verifyNumAjax.abort();
						            	　　　　}
						            	　　}
						             
						              
						            }); 
							 }else{
								alert("请输入验证码！");
								flag=false;
							 }
						}
					}
					
					
					if($(this).find("span").length>0 || textVal.length>0){
						var mobile = /^1[3-9][0-9]{9}$/, phone = /^0\d{2,3}-?\d{7,8}$/ ,hk = /^([2|3|4|5|6|7|8|9])\d{7}$/,hk2 = /^(852[2|3|4|5|6|7|8|9])\d{7}$/,mk = /^[6]([8|6])\d{5}$/;
						if (!(mobile.test(textVal) || phone.test(textVal)|| hk.test(textVal)|| hk2.test(textVal)|| mk.test(textVal))){
							flag=false;
							var helpHtml = "<p class='help-block' style='color:red;'>请输入11位电话号码，香港号码需在前面加 852.</p>";
							$(this).find("p").remove();
							$(this).append(helpHtml)
						}else{
							$(this).find("p").remove();
						}
					}else{
						$(this).find("p").remove();
					}
				}
				
			if(textVal.length>150){
				flag = false;
				$(this).find("p").remove();
				$(this).append(lengthHtml)
			}
			params[textName]=textVal;
			
		}
		if ($(this).find("textarea").length == 1) {
			
			var textareaVal = $(this).find("textarea").val().trim();
			var textareaName = $(this).find("textarea").attr("id");
		
			if($(this).find("span").length>0){
				if(textareaVal==""){
					flag = false;
					$(this).find("p").remove();
					$(this).append("<p class='help-block' style='color:red;'>请输入内容.</p>")
				}else{
					$(this).find("p").remove();
				}
			}
			if(textareaVal.length>495){
				flag = false;
				$(this).find("p").remove();
				$(this).append(lengthHtml)
			}
			
			params[textareaName]=textareaVal;
		}
		
		
		
		if ($(this).find("select").length == 1) {
			var selectVal = $(this).find("select").val();
			var selectName = $(this).find("select").attr("id");
			params[selectName]=selectVal;
		}
		if ($(this).find("input[type='radio']").length > 1) {
			var checkVal =new Array();
			var radioName = $(this).find("input[type='radio']").attr("name");
			 $(this).find(":checked").each(function(i){
				 checkVal.push($(this).val());
			});
			 
			 if($(this).find("span").length>0){
				if(checkVal.length==0){
					flag = false;
					$(this).find("p").remove();
					$(this).append("<p class='help-block' style='color:red;'>请选择一个选项.</p>")
				}else{
					$(this).find("p").remove();
				}
			}
			params[radioName]=checkVal.toString();
		}
		
		
		
		if ($(this).find("input[type='checkbox']").length > 1) {
			var checkVal =new Array();
			var checkboxName = $(this).find("input[type='checkbox']").attr("name");
			 $(this).find(":checked").each(function(i){
				 checkVal.push($(this).val());
			});
			 
			 if($(this).find("span").length>0){
				if(checkVal.length==0){
					flag = false;
					$(this).find("p").remove();
					$(this).append("<p class='help-block' style='color:red;'>请选择一个或多个选项.</p>")
				}else{
					$(this).find("p").remove();
				}
			}
			params[checkboxName]=checkVal.toString();
		}
		
	})
	
	
	if(!flag){
		alert("【信息没填写完整】或者【 格式错误】 已红色标注。 ");
		return;
	}
	// 取消提交按钮的点击事件
	$("#formContainer").off("click", ".btn-submit");
	
	$("#formContainer").find(".alert").remove();
	
	var formName = $("#formContainer").attr("formNum");

	var sourceUrl = window.location.href;
	params.source_url=sourceUrl;
	var url = "http://localhost:8100/system/form/insertData/"+formName
	var submitHtml = $(".btn-submit").html();
        
	
       var insertDataAjax =  $.ajax({
            type: "post",
            url: url,
            timeout: 5000,
            data:params,
            beforeSend: function () {
            	changeSubmitBtn()
            },
            success: function (result) {
            	
    				$("#formContainer form")[0].reset();
    				$("#formContainer form").find("img").attr("src","");
    				 $(".btn-submit").removeClass("disabled");
    				 $(".btn-submit").html(submitHtml);
    				 $("#formContainer").on("click", ".btn-submit", submitData);
    				alert(result.msg);
    			
            },
            complete : function(XMLHttpRequest,status){
        	　　　　if(status=='timeout'){
        			insertDataAjax.abort();
        			$("#formContainer form")[0].reset();
        			$("#formContainer form").find("img").attr("src","");
        			$(".btn-submit").removeClass("disabled");
        			$(".btn-submit").html(submitHtml);
        			$("#formContainer").on("click", ".btn-submit", submitData);
        			alert("提交成功!");
        	　　　　}
        	　　}
         
           
        });
	
	
}
function changeSubmitBtn(){
	 $(".btn-submit").addClass("disabled");
	 $(".btn-submit").html("提交中，请稍等。");
}

//验证时间的大小-----------
var verifyCount = 60;

function sendVerify(){
	var phone = $("#formContainer input[mark=phone]").val();
	var useNoteType =  $("#formContainer").attr("useNoteType");
	var mobile = /^1[3-9][0-9]{9}$/;
	if (!(mobile.test(phone))){
		alert("请输入正确的手机号");
		return;
	}
	if(!$(this).prev().attr("mark") && useNoteType==1){
		$(this).before("<input  style='margin-right: 10px;' mark='verify' type='text' class='form-control'   placeholder='请输入验证码' >");
	}
	
	$.post("http://localhost:8100/form/sendVerify",{"phone":phone,"type": useNoteType},function(result){
		alert(result.message);
		verfityTime = setInterval(SetRemainTime, 1000);//启用计时器
		$("#formContainer #verifyBtn").attr("disabled","disabled");//禁用按钮
		$("#formContainer").off("click", "#verifyBtn");// 取消提交按钮的点击事件
	})
}
function SetRemainTime() {
    if (verifyCount == 1) {
	    clearInterval(verfityTime);//停止计时器
	    $("#formContainer #verifyBtn").removeAttr("disabled");//启用按钮
	    $("#formContainer #verifyBtn").text("重新发送");
	    $("#formContainer").on("click", "#verifyBtn", sendVerify);//重新绑定点击事件
	    verifyCount=60;
    }else {
   	 	verifyCount--;
   	 	$("#formContainer #verifyBtn").text(verifyCount + "秒后可以重新发送");
    }
}

function formUploadFile(){
	var inputFile = $("#"+$(this).attr("id"));
	var attrType = (inputFile.attr("mark")) == "photo"?"src":"href";
	if(!inputFile.val()){
		alert("没选择文件")	;
		return;
	}
	var fileM=document.querySelector("#"+$(this).attr("id"));
	var fileObj = fileM.files[0];
	 //判断大小
	if(fileObj.size > 10485760){
		alert("请选择小于10M的文件。");
		inputFile.val("");
		return;
	}
     // 判断文件格式
        var suffixs = new Array("jpg","png","gif");
        var fileName = fileObj.name;
        var suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
        var validateSuffix = false;
        for (var i = 0; i < suffixs.length; i++) {
          if (suffixs[i].toLowerCase() === suffix) {
            validateSuffix = true;
            break;
          }
        }
        if (!validateSuffix && attrType=="src") {
          alert("文件格式不正确！只支持上传：" + suffixs.join(","));
          inputFile.val("");
          return;
        }
	 
//创建formdata对象，formData用来存储表单的数据，表单数据时以键值对形式存储的。
	 	var formData = new FormData();
		formData.append('file', fileObj);
		$.ajax({
		        url: "http://localhost:8100/form/upload",
		        type: "post",
		        dataType: "json",
		        data: formData,
		        async: false,
		        cache: false,
		        contentType: false,
		        processData: false,
		        success: function (resp) {
		        	if(resp.code == 0){
		        		alert("上传成功！");
		        		setFormImgInputValue(inputFile,attrType,resp.url);
		        	}
		        },
			});
	
}

function setFormImgInputValue(obj,type,path){
	var realPath = "http://localhost:8100"+path;
	if(type=="src"){
		obj.next().attr(type,realPath);
	}else{
		obj.next().val(realPath);
	}
	
}