<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%-- /WEB-INF/decorators/layouts/gdulayout.jsp 
	<sitemesh:write property="title" />
	<sitemesh:write property="head" />
	<sitemesh:write property="body" />
	
--%>
<!DOCTYPE html>
<html lang="en">
<head>
<sitemesh:write property="title" />
<title>구디 농수산물 직판</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/5/w3.css">
<link rel="stylesheet" href="https://www.w3schools.com/lib/w3-theme-black.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<%-- summernote 관련 설정
    jquery, bootstrap 기능 사용 --%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote.min.js"></script>
<style>
html,body,h1,h2,h3,h4,h5,h6 {font-family: "Roboto", sans-serif;}
.w3-sidebar {
  z-index: 3;
  width: 250px;
  top: 43px;
  bottom: 0;
  height: inherit;
}
</style>
</head>
<body>
<sitemesh:write property="head" />
<!-- Navbar -->
<div class="w3-top">
  <div class="w3-bar w3-theme w3-top w3-left-align w3-large">
    <a class="w3-bar-item w3-button w3-right w3-hide-large w3-hover-white w3-large w3-theme-l1" href="javascript:void(0)" onclick="w3_open()"><i class="fa fa-bars"></i></a>
    <a href="${path}/user/mypage?userid=${sessionScope.loginUser.userid}" class="w3-bar-item w3-button w3-theme-l1"><div id="mainlogo"></div></a>
    <a href="${path}/item/list" class="w3-bar-item w3-button w3-hide-small w3-hover-white">상품 등록/구매</a>
    <a href="${path}/cart/cartView" class="w3-bar-item w3-button w3-hide-small w3-hover-white">나의 장바구니</a>
  </div>
  </div>
</div>

<!-- Sidebar -->
    	
<nav class="w3-sidebar w3-bar-block w3-collapse w3-large w3-theme-l5 w3-animate-left" id="mySidebar">
  <a href="javascript:void(0)" onclick="w3_close()" class="w3-right w3-xlarge w3-padding-large w3-hover-black w3-hide-large" title="Close Menu">
    <i class="fa fa-remove"></i>
  </a>
  <h4 class="w3-bar-item"><b>반갑습니다 ${sessionScope.loginUser.userid} 님</b></h4>
  <a class="w3-bar-item w3-button w3-hover-black" href="${path}/board/list?boardid=1">공지사항</a>
  <a class="w3-bar-item w3-button w3-hover-black" href="${path}/board/list?boardid=2">자유게시판</a>
  <a class="w3-bar-item w3-button w3-hover-black" href="${path}/board/list?boardid=3">QnA</a>


</nav>
<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>
<!-- Main content: shift it to the right by 250 pixels when the sidebar is visible -->
<div class="w3-main" style="margin-left:250px">
	<div class="w3-row w3-padding-64">
	    <div class="w3-twothird w3-container">
	 	  <sitemesh:write property="body"/>
		</div>
	</div>
    <%-- 수출입은행 환율 정보 표시 영역 --%>
  <div style="width:100%;">
  	<div id="exchange" style="width:70%; margin: 6px;"></div>
  </div>
  <%-- 게시판 추이 파이그래프 --%>
  <header>게시판 현황(파이)</header>
  <div class="w3-row-padding w3-margin-bottom">
    <div class="w3-half">
      <div class="w3-container w3-padding-16 w3-center">
      	<input type="radio" name="pie" onchange="piegraph(2)"
      	 checked="checked">자유게시판 &nbsp;&nbsp;
      	<input type="radio" name="pie" onchange="piegraph(3)">QnA &nbsp;&nbsp;
      	<div id="piecontainer"
      		 style="width:100%; border:1px solid #ffffff">
      	</div>
      </div>
    </div>
  </div>
  <header>게시판 현황(막대)</header>
  <div class="w3-row-padding w3-margin-bottom">
    <div class="w3-half">
      <div class="w3-container w3-padding-16 w3-center">
      	<input type="radio" name="barline" onchange="barlinegraph(2)"
      	 checked="checked">자유게시판 &nbsp;&nbsp;
      	<input type="radio" name="barline" onchange="barlinegraph(3)">QnA &nbsp;&nbsp;
      	<div id="barcontainer"
      		 style="width:100%; border:1px solid #ffffff">
      	</div>
      </div>
    </div>
  </div>
  <!--  시도, 구군 동리 선택 (ajax 문제) -->
  <span id="si">
  	<select name="si" onchange="getText('si')">
  	  <option value="">시도를 선택하세요</option>
  	</select>
  </span>
  <span id="gu">
  	<select name="gu" onchange="getText('gu')">
  	  <option value="">구군을 선택하세요</option>
  	</select>
  </span>  
  <span id="dong">
  	<select name="dong" onchange="getText('dong')">
  	  <option value="">동리를 선택하세요</option>
  	</select>
  </span>  
  
  <!--  footer -->
  <footer id="myFooter">
    <div class="w3-container w3-theme-l2 w3-padding-32">
      <h4>"최고의 품질로 모시겠습니다"</h4>
    </div>

    <div class="w3-container w3-theme-l1">
      <p>Powered by <a href="https://www.w3schools.com/w3css/default.asp" target="_blank">w3.css</a></p>
    </div>
  </footer>

<!-- END MAIN -->
</div>

<script>
// Get the Sidebar
var mySidebar = document.getElementById("mySidebar");

// Get the DIV with overlay effect
var overlayBg = document.getElementById("myOverlay");

// Toggle between showing and hiding the sidebar, and add overlay effect
function w3_open() {
  if (mySidebar.style.display === 'block') {
    mySidebar.style.display = 'none';
    overlayBg.style.display = "none";
  } else {
    mySidebar.style.display = 'block';
    overlayBg.style.display = "block";
  }
}

// Close the sidebar with the close button
function w3_close() {
  mySidebar.style.display = "none";
  overlayBg.style.display = "none";
}
</script>

<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js"></script>
<%--   시도구군동리 ajax 자바스크립트 영역 --%>
<script type="text/javascript">
$(function(){
	mainlogo();
	getSido1();
  //exchangeRate() // 수출입은행 환율정보 조회하기. 서버에서 HTML 형식(문자열)으로 리턴
	exchangeRate2() //수출입은행 환율정보 조회하기. 서버에서 Map 형식(JSON)으로 리턴
	piegraph(2) //글쓴이별 게시글 건수를 파이그래프로 출력
	barlinegraph(2) //글쓴이별 게시글 건수를 선/막대 그래프로 출력
	})
function getSido1() {
	$.ajax({
		url : "/ajax/select1",
		success : function(data) {
			console.log(data)
			let arr = data.substring(data.indexOf('[')+1, data.indexOf(']')).split(",");
			$.each(arr,function(i,item){
				$("select[name=si]").append(function(){
					return "<option>" + item + "</option>" 
				})
	  		})
	  	}
	})
}

function getText(name) {
	let city = $("select[name='si']").val();
	let gu = $("select[name='gu']").val();
	let disname;
	let toptext = "구군을 선택하세요";
	let params = "";
	if(name == "si") {
		params = "si=" + city.trim();
		disname = "gu";
	} else if (name == "gu") {
		params = "si=" + city.trim() + "&gu=" + gu.trim();
		disname = "dong";
		toptext = "동리를 선택하세요";
	} else {
		return;
	}
	$.ajax({
		url : "/ajax/select2",
		type : "POST",
		data : params,
		success : function(arr) { //서버에서 배열 객체로 전달 받음
			//서버에서 List 객체로 전달하는 경우, 클라이언트는 배열 객체로 받음
			$("select[name="+disname+"] option").remove();
			$("select[name="+disname+"]").append(function(){
				return "<option value=''>"+ toptext + "</option>"
			})
			$.each(arr,function(i,item){
				$("select[name="+disname+"]").append(function(){
					return "<option>"+item+"</option>"
				})
			})
		}
		
	})
}
function exchangeRate() {
	$.ajax("/ajax/exchange1", {
		success : function(data) {
			console.log(data)
			$("#exchange").html(data)
		},
		error : function(e) {
			alert("환율 조회시 서버 오류 발생 :"+ e.status)
		}
	})
}

function exchangeRate2() {
	$.ajax("/ajax/exchange2",{ // Map으로 데이터 수신
		success : function(json) { //서버 Map 객체로 전송, 클라이언트:JSON 형식
			console.log(json)
			let html = 
			"<h4 class='w3-center'>수출입은행<br>"+json.exdate+"</h4>";
			html += "<table class='w3-table-all w3-margin-right'>"
			html += "<tr><th>통화</th><th>기준율</th>"
					+ "<th>받으실때</th><th>보내실때</th></tr>"
			 //json.trlist : 4개의 배열 객체 저장
			$.each(json.trlist, function(i, tds) { //tds : 8개의 문자열 배열
				html +=
				"<tr><td>"+tds[0]+"<br>"+tds[1]+"</td><td>"+tds[4]+"</td>"
				+ "<td>"+tds[2]+"</td><td>"+tds[3]+"</td></tr>"
			})
			html += "</table>"
			$("#exchange").html(html)
		},
		error : function(e) {
			alert("환율 조회시 서버 오류 발생:"+ e.status)
		}
	})
}
//랜덤 컬러 뽑아주는 함수
let randomColorFactor = function() {
	return Math.round(Math.random() * 255)
}
let randomColor = function(opa) {
	return "rgba(" + randomColorFactor() + ","
				   + randomColorFactor() + ","
				   + randomColorFactor() + ","
				   + (opa || '.3') + ")"				   							
}


function piegraph(id) { //id 2: 자유게시판, 3:QnA
	$.ajax("/ajax/graph1?id="+id,{
		success : function(json) { // [...] : 배열.
			let canvas = "<canvas id='canvas1' style='width:100%'></canvas>"
			$("#piecontainer").html(canvas)
			pieGraphPrint(json,id)
		},
		error : function(e) {
			alert("서버오류:" + e.status)
		}
	})
}
function pieGraphPrint(arr,id) {
	// arr = [{홍길동:10}, {김삿갓:7}, ...] (배열 안에 map 객체)
	let colors = []
	let writers = []
	let datas = []
	$.each(arr,function(index){
		colors[index] = randomColor(0.5)
		for(key in arr[index]) { //arr[0] : {홍길동:10}
			writers.push(key) //글쓴이
			datas.push(arr[index][key]) //게시판 등록 건수
		}
	})
	let title = (id == 2) ? "자유게시판" : "QnA"
	let config = {
			type : 'pie',
			data : {
				datasets : [{ data:datas,
							  backgroundColor : colors}],
				labels : writers
			},
			options : {
				responsive : true,
				legend : {display:true, position:"right"},
				title : {
					display : true,
					text : '글쓴이별 ' + title + " 등록 건수",
					position : 'bottom'
					}
				}
			}
	let ctx = document.getElementById("canvas1")
	new Chart(ctx,config)
	}

//메인로고 크롤링
function mainlogo() {
	$.ajax("/ajax/mainlogo", {
		success : function(data) {
			console.log(data)
			$("#mainlogo").html(data);
		},
		error : function(e) {
			alert("로고 불러오기 실패"+ e.status)
		}
	})
}

//막대그래프
function barlinegraph(id) {
	$.ajax("/ajax/graph2?id="+id,{
		success : function(arr){
			let canvas2 = "<canvas id='canvas2' style='width:100%'></canvas>"
			$("#barcontainer").html(canvas2)
			barlineGraphPrint(arr,id)
		},
		error : function(e) {
			alert("서버오류 :" + e.status)
		}
	})
}

function barlineGraphPrint(arr,id) {
	let colors= []
	let regdates = []
	let datas = []
	$.each(arr, function(index){
		colors[index] = randomColor(0.5)
		for(key in arr[index]){
			regdates.push(key)
			datas.push(arr[index][key])
		}
	})
	let title = (id==2) ? "자유게시판" : "QnA"
	let config = {
			type : 'bar',
			data : {
				datasets : [
				{ type : "line",
					boardWidth : 2,
					borderColor : colors,
					label : '건수',
					fill : false,
					data : datas
					},
				{type : "bar",
				 backgroundColor : colors,
				 label : '건수',
				 data : datas
				 }
				],
				labels : regdates,
				},
			options : {
				responsive : true,
				legend : {display:false},
				title : {
					display : true,
					text : '최근 7일' + title + "등록건수",
					position : 'bottom'
				},
				scales : {
					xAxes : [{ display : true,
							   scaleLabel : {
								   display : true,
								   labelString : "작성일자"
							   }
					}],
				    yAxes : [{
				    	scaleLabel : {
				    		display : true,
				    		labelString : "게시물 등록 건수"
				    	},
				    	ticks : {beginAtZero : true}
				    }]
				}
			}
	}
	let ctx = document.getElementById("canvas2")
	new Chart(ctx, config)
}

</script>
</body>
</html>

