﻿/*  
* @description: 微信消息库回复管理  
* @author: WMM
* @update:
*/

var wechat = {
  itemArr: [],
  numb:0,
  elNum:1,
  type:1,
  /* 初始化 */
  delft: function(data){
    if (!data) {
         var itemobj = $('.js_appmsg_item');
        for (var i = 0; i < itemobj.length; i++) {
          this.itemArr[i] = this.newobj();
        }
        //console.log(this.itemArr)
    }else{
     // console.log(data)
      this.itemArr = data;
    }
 
  },
  /* 实时显示输入信息 */
  blurText: function(thiz){ 
    var txt= thiz.value,
        klass = thiz.name;
    $('.editing:visible').find('#appmsgItem'+this.elNum+' .j_'+klass).html(txt);
  },
  /* 存储一条数据对象 */
  newobj:function(){
    var editEl = $('#js_appmsg_editor'),
        obj = {};
      editEl.find('.j-datawx').each(function() {
      var keyname = this.tagName,
          key = this.name;
        if (keyname == 'INPUT' || keyname == 'TEXTAREA') {
          obj[key] = '';
        }else if(keyname == 'IMG'){
          obj[key] = '';
        }else if(keyname =='SELECT'){
          obj[key] = 1;
        }
    });
    obj['content'] = '';
    
    return obj;
  },
  /* 提交json */
  submitFrom: function(){
    this.getEdit();
    var flag = this.validationWeixin();
    if (!flag) {
        for(key in this.itemArr){
        for(key2 in this.itemArr[key]){
          if (key2 == 'pic') {
            delete this.itemArr[key][key2];
          };
        }
      }
      $('#weixinItem').val(JSON.stringify(this.itemArr));
      return true;
    }else{
      return false;
    }

  },
  /* 验证json */
  validationWeixin: function (){
    var errorArr = {name:"标题不能为空且长度不能超过64字",picturePath:"必须插入一张图片",content:"正文不能为空且长度不能超过16777215字",link:"链接地址不能为空或地址格式错误"}
    var flag = false;
      for(key in this.itemArr){
      for(key2 in this.itemArr[key]){
          if(this.validFun(key,key2)){
            this.numb = key;
            this.elNum = $('.editing').find('.js_appmsg_item').eq(key).attr('data-id');
            this.setEdit(); //把当前JSON对象 传递给函数设值
            this.pageTips(errorArr[key2]); //弹出错误
            flag = true;
            break;
          }

      }
      if (flag) break;
    }
    return flag;
  },
  validFun: function(key,key2){
    var flag = false;
    var value = this.itemArr[key][key2];

    if(key2 == 'name'){
      if(this.itemArr[key][key2].length==0 || this.itemArr[key][key2].length>64){
        //console.log(vailUrl(11))
        flag =true
      };
    }else if( key2 == 'picturePath' && value === ''){

      flag = true;

    }else if(key2 == 'content' && this.itemArr[key]['type'] == 2&& value === ''){
      flag = true;

    }else if(key2 == 'link' && this.itemArr[key]['type'] == 2 && !vailUrl(this.itemArr[key][key2])) {
       flag = true;

    }

  return flag;
  },
  /* 错误显示 */
  pageTips: function(txt){
    var str = '<div class="JS_TIPS page_tips error Shake"><div class="inner">'+txt+'</div></div>'
    $('body').append(str);
    setTimeout(this.delPageTips,3000);
  },
  /* 删除错误提示 */
  delPageTips: function(){
    $('.JS_TIPS').remove();
  },
  /* 确认编辑第几个 赋值 */
  editId: function(thiz){ 
    var nextNumb = $(thiz).closest('.js_appmsg_item').index();
        this.elNum = $(thiz).attr('data-id');
    if (this.numb == nextNumb ) {
     return;
    }else{
        this.getEdit();
        this.numb = nextNumb;
    }

    if (this.numb == 0) $('.appmsg_editor:visible').css('margin-top', '0');
    else if( this.numb >0) $('.appmsg_editor:visible').css('margin-top', 119*(this.numb-1)+186+'px');
    this.setEdit(this.itemArr[this.numb]);
  
    $('.appmsg_editor:visible').data('id',+this.numb+1);
    
  },
  /* 添加多图文 */
  editAdd: function(){
    var edEl = $('.editing:visible');
    var itemlen = edEl.find('.js_appmsg_item').length;
      if(itemlen<8){
        var item =  Number(edEl.find('.js_appmsg_item:last').attr('data-id')),
            str3 = '<div id="appmsgItem'+(item+1)+'" data-id="'+(item+1)+'" class="appmsg_item js_appmsg_item ">'
             + '<div class="js_appmsg_thumb appmsg_thumb">'
             + '  <img  src="" alt="">'
             + '</div>'
             + '<i class="appmsg_thumb default">缩略图</i>'
             + '<h4 class="appmsg_title j_name">标题</h4>'
             + '<div class="appmsg_edit_mask">'
             + '<a class="edit_gray" onclick="wechat.editId(this)"  data-id="'+(item+1)+'" href="javascript:void(0);">编辑</a>'
             + '<a class="del_gray" onclick="wechat.editDel(this)"  data-id="'+(item+1)+'" href="javascript:void(0);">删除</a>'
             + '</div>'
             + '</div>';

          edEl.find('#appmsgItem'+item).after(str3);
          this.itemArr[this.itemArr.length] = this.newobj();
      }else{
        this.pageTips("多条图文最多添加8条消息"); //弹出错误
      }
  },
  /* 删除多图文 */
  editDel: function(thiz){
    var itemel = $(thiz).closest('.js_appmsg_item'),
        nextNumb = itemel.index(),
        itemlen = $('.editing').find('.js_appmsg_item').length;
      if(itemlen>2) {
        this.elNum = 1;
        this.getEdit();
        itemel.remove();
        this.itemArr.splice($.inArray(nextNumb,this.itemArr),1);
        this.numb = 0;
        this.setEdit();
        $('.appmsg_editor:visible').css('margin-top', '0');
      }
      else this.pageTips("无法删除，多图文至少需要2条消息"); //弹出错误
      
  },
  /* 设置显示图片 */
  setPic: function(path){
     var picId = $('.editing:visible').find('#appmsgItem'+this.elNum+' .appmsg_thumb');
      picId.each(function(index, el) {
        if (this.tagName == 'IMG') {
          $(this).attr('src',path);
          $(this).addClass('default');
        }else if(this.tagName == 'I'){
           $(this).removeClass('default');
        }
      });

  },
  /* 设置右边编辑器值 */
  setEdit: function(){
    var editEl = $('#js_appmsg_editor'),
        thiz = this;

    if (this.numb == 0) $('.appmsg_editor:visible').css('margin-top', '0');
    else if( this.numb >0) $('.appmsg_editor:visible').css('margin-top', 119*(this.numb-1)+186+'px');

    editEl.find('.j-datawx').each(function() {
      var keyname = this.tagName;
      if (keyname == 'INPUT' || keyname == 'TEXTAREA') {
        this.value = thiz.itemArr[thiz.numb][this.name];
      }else if(keyname == 'IMG'){
        $(this).attr('src',thiz.itemArr[thiz.numb][this.name]);
        if ($(this).attr('src') != '') {
          $(this).show().parent().css('font-size', '0');
        }else{
          $(this).hide().parent().css('font-size', '14px');
        }
        
      }else if(keyname =='SELECT'){
        $(this).find('option[value="'+thiz.itemArr[thiz.numb][this.name]+'"]').attr('selected','selected');
        if(thiz.itemArr[thiz.numb][this.name] == 2){
          $(".js_url_area").show();
        }else if(thiz.itemArr[thiz.numb][this.name] == 1){
          $(".js_url_area").hide();
        }
      }

      });
    UE.getEditor('editor').setContent(thiz.itemArr[thiz.numb]['content'])

  },
  /* 存储每条回复信息 */
  getEdit: function(){
    var editEl = $('#js_appmsg_editor');
    var thiz = this;
    editEl.find('.j-datawx').each(function() {
      var keyname = this.tagName;

        if (keyname == 'INPUT' || keyname == 'TEXTAREA') {
          thiz.itemArr[thiz.numb][this.name] = this.value;
        }else if(keyname == 'IMG'){
          thiz.itemArr[thiz.numb][this.name] = $(this).attr('src');
        }else if(keyname =='SELECT'){
          thiz.itemArr[thiz.numb][this.name] = $(this).find('option:selected').val();
        }

    });

  thiz.itemArr[thiz.numb]['content'] = UE.getEditor('editor').getContent();
  }
}
/*//删除指定索引数组元素
Array.prototype.removeArr=function(dx) {
  var delArr;
　 if(isNaN(dx) || dx>this.length) {
    return false;
  }
　 for(var i=0,n=0;i<this.length;i++) {
　　　if(this[i]!=this[dx]) {
　　　　　this[n++]=this[i];
　　　}else{
        delArr=this[i];
　　　}
　 }
　 this.length-=1;
  return delArr;
}*/
//验证URL
function vailUrl(value){
  return  /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)*(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value);
}