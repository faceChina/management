void function() {
  function format(template, json) {
    return template.replace(/#\{(.*?)\}/g, function(all, key) {
      return json && (key in json) ? json[key] : "";
    });
  }

  headerHtml = format(
    String(function(){/*!
<link rel="stylesheet" type="text/css" href="../../base/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../../base/css/main.css">
<link rel="stylesheet" type="text/css" href="../../base/css/main-gw.css">
<!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->

<script type="text/javascript" src="../../base/js/jquery-1.8.3.js"></script>

<script type="text/javascript" src="../../base/js/underscore.js"></script>

<script type="text/javascript" src="../../base/js/artDialog/jquery.artDialog.js"></script>
<script type="text/javascript" src="../../base/js/artDialog/iframeTools.js"></script>

<script type="text/javascript" src="../../base/js/validate/jquery.validate.js"></script>

<script type="text/javascript" src="../../base/js/validate/messages_zh.js"></script>

<script type="text/javascript" src="../../base/plugins/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="../../base/js/ajaxupload3.9.js"></script>

<script type="text/javascript" src="../../base/js/jscolor/jscolor.js"></script>

<script type="text/javascript" src="../../base/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="../../base/plugins/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="../../base/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript" src="../../base/js/scale.js"></script>
<script type="text/javascript" src="../../base/js/getTableJSON.js"></script>
<script type="text/javascript" src="../../base/js/wechat.js"></script>

*/}).replace(/^[^\{]*\{\s*\/\*!?|\*\/[;|\s]*\}$/g, ''),
    {
      title: "代码里的模板",
      date: "2014-05-16"
    }
  );
}();
document.write(headerHtml);