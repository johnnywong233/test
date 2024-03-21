<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!--TODO -->
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Hello, World</title>
    <style type="text/css">
        * {
            margin: 0px auto;
            padding: 0px;
        }

        body {
            height: 100%;
            width: 960px;
            margin: 0px auto;
            padding: 0px
        }

        #map {
            height: 350px;
            width: 400px;
            border: 1px solid red;
            float: left;
        }
    </style>
    <script type="text/javascript" src="https://api.map.baidu.com/api?v=1.4"></script>
    <script type="text/javascript"
            src="https://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>
    <link rel="stylesheet" href="https://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css"/>
</head>

<body>
<div id="map"></div>
<div style="border: 1px solid blue;margin:0px;padding:0px;width:240px;height:350px;float:left;">
    <input type="text" id="val" style="width:180px;height:25px;size:20;text-align: left;"/>
    <input type="button" id="btn" onclick="res('val')" value="搜索" style="width:40px;height:25px;"/>
    <div id="result" style="margin:0px;padding:0px;width:240px;height:300px;overflow:auto;"></div>
</div>
<script type="text/javascript">
    var map = new BMap.Map("map");          // 创建地图实例
    var poi = new BMap.Point(113.23129, 23.387826);
    map.centerAndZoom(poi, 18); // 初始化地图，设置中心点坐标和地图级别
    map.addControl(new BMap.ScaleControl());   // 添加默认比例尺控件
    map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_LEFT, type: BMAP_NAVIGATION_CONTROL_SMALL}));  //左上角，仅包含平移和缩放按钮
    map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
    map.addControl(new BMap.MapTypeControl());          //添加地图类型控件
    var marker = new BMap.Marker(poi); //创建marker对象
    var local = new BMap.LocalSearch("全国", {				//创建本地搜索
        renderOptions: {
            map: map,
            panel: "result",
            autoViewport: true,
            selectFirstResult: false
        }
    });
    var res = function (id) {
        var val = $(id).value;
        if (val == "数神软件" || val == "数神") {
            map.centerAndZoom(poi, 18); // 初始化地图，设置中心点坐标和地图级别
        } else {
            local.search(val);
        }
    }
    //	map.addEventListener("click",function(e){
    //alert(e.point.lng + "," + e.point.lat);
    //});
    var content = '<div style="width:130px;height:40;margin:0;padding:0px;">数神软件公司<br/>电话:12346578987</div>';//创建检索信息窗口对象
    var searchInfoWindow = new BMapLib.SearchInfoWindow(map, content, {
        title: "数神软件公司",      //标题
        width: 120,             //宽度
        height: 40,              //高度
        panel: "result",         //检索结果面板
        enableAutoPan: true,     //自动平移
        searchTypes: [
            BMAPLIB_TAB_SEARCH,   //周边检索
            BMAPLIB_TAB_TO_HERE,  //到这里去
            BMAPLIB_TAB_FROM_HERE //从这里出发
        ]
    });
    //var infoWindow = new BMap.InfoWindow(content);  // 创建信息窗口对象
    //marker.addEventListener("click", function(){
    //	 this.openInfoWindow(infoWindow);
    //});
    marker.disableDragging(); //marker不可拖拽
    marker.addEventListener("click", function (e) {
        searchInfoWindow.open(marker);
    });
    map.addOverlay(marker); //在地图中添加marker
    searchInfoWindow.open(marker); //在marker上打开检索信息串口
    function $(id) {
        return document.getElementById(id);
    }
</script>
</body>
</html>