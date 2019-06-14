<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/hynix/front/include/inc_doctype.jsp"%>
<%@ include file="/hynix/front/include/taglibs.jsp"%>
<head>
<%@ include file="/hynix/front/include/inc_head.jsp"%>
<script type="text/javascript" src="/hynix/front/js/ajaxfileupload.js"></script>

<!--달력사용 CSS 추가 from admin  -->
<link rel="stylesheet" type="text/css" href="/hynix/front/css/calender.css" />
<link rel="stylesheet" type="text/css" href="/hynix/front/css/local_campus.css?ver=2">

<!-- <script src="/hynix/front/js/calendar.js"></script> -->
	
<script language="javascript">

$("#lnb").ready(function() {
	$(this).lnbTest({
		d1 : 2,
		d2 : 2
	});
});

	/* 준영 달력*/
	var aparkCal = {
		cal: null,
		weekUnit: 7,
		
		// 선택 놀이공원 체크
		aparkNo : "${aparkNo}",
		
		// 롯데월드 평일 인원 init 함수에서 설정
		weekdayMaxNum : 0,
		weekendMaxNum : 0,

		searchDay : new Date("${year}", "${month}"), //선택한 달력 날짜 (년,월만 확인)
		nowDay : new Date("${year}", "${month}", "${date}"), //현재 날짜 2~5일 체크하기 위함
		
		/* 이용일 5일전부터 이용일 2일전까지 버튼 노출, init 함수에서 세팅 */ 
		availableDateStart : new Date(), //신청가능일 부터 2일 후 날짜
		availableDateEnd : new Date(), // 신천가능일 부터 5일 후 날짜
		
		//달력 배열을 생성함.
		createCalArray: function() {
			var unit = this.weekUnit;

			var sDate = new Date(this.searchDay.getFullYear() ,this.searchDay.getMonth(), 1);
			var lastDate = new Date(this.searchDay.getFullYear() ,this.searchDay.getMonth()+1,0);
			
			var week = Math.ceil((sDate.getDay() + lastDate.getDate()) / 7);
			
			// 1일의 시작 위치 확인하기 위한 값. 	
			var startDate = sDate.getDay() * -1;
			
			// 마지막날 이후의 공백을 채우기 위한 값. 	
			var endDate = unit - (lastDate.getDay()+1);
			
			/* 달력 배열 생성 */
			var cal = []; //달력 전체 배열
			var week = null; // 한주씩 담을 배열
			var node = new Object(); //각 일에 필요 데이터를 넣기 위한 객체
			
			for(var i = startDate, cnt = 0; i < lastDate.getDate() + endDate; i++, cnt++){
				if(cnt % 7 == 0){
					week = [];
					cal.push(week);
				}
				var date = i+1; // 1일부터 시작하므로 +1을 함
				var currentDate = new Date(this.searchDay.getFullYear() ,this.searchDay.getMonth(), date);
				
				node = {};
				node.date = date;
				node.day = "" + currentDate.getFullYear() + ('0' + (currentDate.getMonth() + 1)).slice(-2) + ('0' + currentDate.getDate()).slice(-2);
				node.applyNum = "-";  //신청인원 
				node.flagWeek = ((cnt%7) >= 1 && (cnt%7) <= 5) ? "weekday" : "weekend"; // cnt == (0:일요일, 1:월, ~ 6:토)
				node.btnCls = ""; //신청상태 버튼 정보
				
				/* 해당일에 신청한 총인원, 대기번호부여 여부(주말추첨 확인하기 위함) 입력 */
				for(var j = 0; j < aparkCal.dailyCntList.length; j++) {
					var dailyNode = aparkCal.dailyCntList[j];
					var hopeDy = Number(dailyNode.USEHOPEDY.substr(6)); //희망신청일자
					if( date == hopeDy && this.aparkNo == dailyNode.APARKNO){
						node.applyNum = dailyNode.SUM;
						node.waitYN = dailyNode.WAITYN;
						break;
					}
				}
				
				/* 제한일 체크 */
				for(var j = 0; j < aparkCal.limDayList.length; j++) {
					var limNode = aparkCal.limDayList[j];
					//제한일 체크
					node.flagWeek = node.day >= limNode.APARKLIMFRDT && node.day <= limNode.APARKLIMTODT ? "limDay" : node.flagWeek;
					//신청가능일 체크
					node.flagWeek = node.day >= limNode.APARKREQFRDT && node.day <= limNode.APARKREQTODT ? "weekend" : node.flagWeek;
					//해당일 알림 MSG
					//node.limMsg = limNode.LIMRSN;
					
					/* 
						limNode.APARKLIMFRDT;		//제한 시작일
						limNode.APARKLIMTODT;		//제한 종료일
						limNode.APARKREQFRDT;		//신청가능 시작일
						limNode.APARKREQTODT;		//신청가능 종료일
					 */	
				}
				
				//이용가능인원
				node.maxNum = node.flagWeek == "weekend" ? this.weekendMaxNum : (node.flagWeek == "limDay") ? 0 : this.weekdayMaxNum;
				
				/* 신청상태 체크 */
				// 이용일 5일전부터 이용일 2일전까지 신청버튼 노출
				if((currentDate >= this.availableDateStart) && (currentDate <= this.availableDateEnd)) {
					node.status = "신청";
					//node.btnCls = "";
					
					//이용가능 인원보다 신청자수가 많으면 대기 상태
					node.status = node.applyNum >= node.maxNum ? "대기" : "신청" ;
					//node.btnCls = node.applyNum >= node.maxNum ? "" : "" ;
					
					//주말 추첨전에는 대기버튼 추첨후에는 신청버튼
					node.status = node.flagWeek == "weekend" && node.waitYN == "N" ? "신청" : node.status ;
					//node.btnCls = node.flagWeek == "weekend" ? "roomOpen" : node.btnCls ;
					
					//제한 일 일경우 신청불가
					node.status = node.flagWeek == "limDay" ? "신청불가" : node.status ;
				}else {
					node.status = "신청불가";
				}
				
				node.msg = "";
				switch (node.status) {
				case "신청":
					node.msg = "놀이공원 신청가능합니다.";
					break;
				case "대기":
					node.msg = "신청인원이 신청이용정원을 초과하였습니다. 대기자로 신청됩니다..";
					break;
				case "신청불가":
					node.msg ="명절, 휴무, 이용접수기간 완료 등으로 신청불가 합니다.";
					break;
				default:
					break;
				} 
				
				week.push(node);  // 주 배열에 일 정보 입력 
			}
			/*//달력 배열 생성 */
			this.cal = cal;
		},
		
		changeCalendar: function(yyyy,mm) {
			var url = "/hynix/aparkWriteAjax.do";
			/* var data = $("#smsSetForm").serialize(); */
			$.ajax({
				type : 'POST',
				url : url,
				data : {yyyy : yyyy, mm : mm},
				async : false,
				dataType: 'json',
				success : function(response) {
					aparkCal.searchDay = new Date(response.year, response.month-1);
					aparkCal.dailyCntList = response.dailyCntList;
					aparkCal.createCalArray();
				},
				error : function(request, textStatus, errorThrown) {
				}
			});
		},
		
		goStep2: function(hopeDay, flagWeek, aparkNo, status, applyNum) {
			
			aparkCal.dateCheck(hopeDay, status);
			if(!aparkCal.nextStep){ return;}			
			
			var yyyymmdd	= hopeDay.toString();
			var year			= yyyymmdd.substr(0,4);
			var month		= yyyymmdd.substr(4,2);
			var date			= yyyymmdd.substr(6,2);
			
			if(confirm(year+"년 "+month+"월 "+date+" 일 놀이공원 신청하셨습니다.\r\n다음 신청 페이지(신청 STEP 2)로 이동하시겠습니까?" )) {
				var url = "/hynix/aparkWrite2.do";
				var weekEndYn = flagWeek=="weekend" ? "Y" : "N";
				$('#useHopeDy').val(hopeDay);
				$('#weekEndYn').val(weekEndYn);
				$('#aparkNo').val(aparkNo);
				$('#reqPrnnumCnt').val(applyNum);
				
				document.gForm.action= url;
				document.gForm.submit();
			}

		},
		
		/* 2019.06 신청 날짜 검증 (기존 체크 로직 참고해서 작성함)*/
		dateCheck: function(hopeDay, status) {
			var aparkNo = aparkCal.aparkNo;
			var cday = hopeDay
			var url = "/hynix/aparkLimChk.do";
			var result = false
			$.ajax({
				type : 'POST',
				url : url,
				cache : false,
				dataType : "json",
				async : false,
				data : {cday:cday,aparkNo:aparkNo},
				success : function(data) {
					var msg = data.msg;
					if(msg == cday){
						aparkCal.nextStep = true;
						aparkCal.checkAbleReq(aparkNo, cday);
						return true;
					}else if(msg != cday){
						//조건에 안맞아서 메세지를 띄운다.
						if( "신청불가" == status){
							var msg = "";
							msg += "지금은 신청 가능 기간이 아닙니다.\r\n" +
									"이용일 5일~2일전까지 신청 가능합니다.\r\n" + 
									"(공휴일의 경우에는 별도 공지 예정)";
						}
						alert(msg);
						aparkCal.nextStep = false;
						return false;
					}
				},
				error : function(request, textStatus, errorThrown) {
					alert("서버와 통신이 실패하였습니다.");
				}
			}); 
		},
		
		/* 2019.06 놀이공원 중복 신청 체크 (기존 체크 로직 참고해서 작성함) */
		checkAbleReq: function(aparkNo, useHopeDy) {
			 var postData = {aparkNo:aparkNo,useHopeDy:useHopeDy};
			 
				$.ajax({
				 type : "POST",
				 url : "/hynix/aparkChkReq.do",
				 contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				 dataType : "json",
				 async : false,
				 data: postData,
				 success : function(data) {
					if(data.result > 0){
						alert("같은 날짜에 신청한 내역이 있습니다.\r\n같은 날짜에 놀이공원을 신청할 수 없습니다.");
						aparkCal.nextStep = false;
					}else{
					}
				},
				 error : function(err) {
				  alert('서버와의 통신이 실패하였습니다.');
				  aparkCal.nextStep = false;
				 }
				});
	 	},
		
	 	setCalendar: function() {
	 		$calPark = $('#park-datepicker');
			// add column
			$calPark.find('table thead tr').prepend('<th class="added-column">구분</th>');
			$calPark.find('table tbody tr').prepend('<td class="added-column"><span class="top-side">날짜</span><span class="bottom-side">이용가능/<em>신청</em></span><span class="bottom-side">신청상태</span></td>');
			$calPark.find('table td.ui-datepicker-other-month').html('<span class="top-side">&nbsp;</span><span class="bottom-side"></span><span class="bottom-side"></span>');
			// add data
			$calPark.find('table td:not(.ui-datepicker-other-month):not(.added-column)').append('<span class="bottom-side">28명/<em>26명</em></span>');
		
	 		for (var i = 0; i < this.cal.length; i++) {
	 			var week = this.cal[i];
	 			var calParkWeek = $calPark.find('table tbody tr').eq(i);
	 			
	 			for (var j = 0; j < week.length; j++) {
	 				var dateObj = week[j];
	 				var calParkTd = calParkWeek.find('td:not(.added-column)').eq(j);
	 				var calParkDate = calParkTd.find('.ui-state-default').text();
	 				
	 				//날짜가 같을경우만 변경
	 				if(String(dateObj.date) != calParkDate){ continue; }
	 				
	 				calParkTd.find('.bottom-side:first').html(dateObj.maxNum+'/<em>'+ dateObj.applyNum +'</em>'); 
	 				
	 				var buttonTag = '<span class="bottom-side"><button type="button" class="'+""+'" title="'+ dateObj.msg +'"' + 
	 							'onclick="aparkCal.goStep2('+dateObj.day+",\'"+dateObj.flagWeek+"\',\'"+aparkCal.aparkNo+"\',\'"+dateObj.status+"\',\'"+dateObj.applyNum+'\');">'+ dateObj.status +'</button></span>';
	 				calParkTd.append(buttonTag);
	 			}
	 		}
	 	},
	 	
	 	initDatepicker: function() {
	 		$.datepicker._updateDatepicker_original = $.datepicker._updateDatepicker;
		    $.datepicker._updateDatepicker = function(inst) {
		        $.datepicker._updateDatepicker_original(inst);
		        var afterShow = this._get(inst, 'afterShow');
		        if (afterShow)
		            afterShow.apply((inst.input ? inst.input[0] : null));  // trigger custom callback
		    }
		    
			$( "#park-datepicker" ).datepicker({
				beforeShowDay: function(date) { return false; }, // datepicker 의 날짜 선택기능 비활성
				dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
				changeMonth: true,
				minDate: new Date(aparkCal.nowDay.getFullYear(), aparkCal.nowDay.getMonth()-3),
			    maxDate: new Date(aparkCal.nowDay.getFullYear(), aparkCal.nowDay.getMonth()+3),
				onChangeMonthYear: function(year, month, inst) {
					aparkCal.changeCalendar(year, ('0' + month).slice(-2));
				},
				afterShow: function() { //datepicker 달력 생성 후 항상 실행됨
					aparkCal.setCalendar();
				}
			});
	 	},
	 	
		init: function(){
			/* 이용일 5일전부터 이용일 2일전까지 버튼 노출  */
			aparkCal.availableDateStart = new Date(aparkCal.nowDay.getFullYear(), aparkCal.nowDay.getMonth(), aparkCal.nowDay.getDate()+2); //신청가능일 부터 2일 후 날짜
			aparkCal.availableDateEnd = new Date(aparkCal.nowDay.getFullYear(), aparkCal.nowDay.getMonth(), aparkCal.nowDay.getDate()+5); // 신천가능일 부터 5일 후 날짜
			
			/* 이용가능 인원 세팅 */
			for(var i = 0; i<aparkCal.aparkInfoList.length; i++) {
				var aparkInfo = aparkCal.aparkInfoList[i];
				
				if(aparkInfo.aparkNo == aparkCal.aparkNo) {
					aparkCal.weekdayMaxNum = aparkInfo.weekDayPrnnum;
					aparkCal.weekendMaxNum = aparkInfo.weekEndPrnnum;
				}
			}
			
			aparkCal.createCalArray();
			aparkCal.initDatepicker();
		}
		
	}
	aparkCal.dailyCntList = ${dailyCntList};
	aparkCal.limDayList = ${limDayList};
	aparkCal.aparkInfoList = ${aparkInfo};
	$(aparkCal.init);
	/*//준영 달력*/
</script>
<script src="/hynix/front/js/calendar.js"></script>
<style>
	.pStep-indicator {
		margin-top: 27px;
		text-align:right;
	}
	.pStep-indicator:before {
		content:"STEP";
		display:inline-block;
		background: #f14211;
		color:white;
		padding: 0 .5em;
		border-radius: 1em;
		line-height: 1.5em;
		height: 1.5em;
		margin-right: .3em;
	}
	.pStep-indicator li {
		display:inline-block;
		background: #e1e1e1;
		border-radius: 50%;
		text-align:center;
		color: white;
		line-height: 1.5em;
		width: 1.5em;
		height: 1.5em;
		margin: 0 .3em;
	}
	.pStep-indicator li.on {
		background: #f14211;
	}
</style>

</head>
<body>
	<%@ include file="/hynix/front/include/inc_header.jsp"%>
	<!-- containerFull -->
	<div id="containerFull"><%@ include file="../common/inc_hotnews.jsp"%>
		<!-- Container -->
		<div id="container"><%@ include file="/hynix/front/include/inc_lnb_normal.jsp"%>
			<!-- Contents Wrap -->
			<div id="contentsWrap">
				<!-- location  -->
				<div id="loc">
					<ul>
						<li class="fir">
							<a href="${pageContext.request.contextPath}/hynix/getFrontMain.do">
								<img src="/hynix/front/images/common/loc_home.gif" alt="홈으로 가기" />
							</a>
						</li>
						<li>회사복지</li>
						<li>
							<a href="${pageContext.request.contextPath}/hynix/getGeneralMain.do">일반 복지서비스</a>
						</li>
						<li class="last">
							<a href="${pageContext.request.contextPath}/hynix/aparkMain.do?welfMnuNo=2210">여가생활 지원 서비스</a>
						</li>
					</ul>
				</div>
				<!--// location  -->
				<hr />
					
				<form name="gForm" id="gForm" onsubmit="return true;" method="post">
					<input type="hidden" name="useHopeDy" id="useHopeDy" value=""/>
					<input type="hidden" name="weekEndYn" id="weekEndYn" value=""/>
					<input type="hidden" name="aparkNo" id="aparkNo" value=""/>
					<input type="hidden" name="reqPrnnumCnt" id="reqPrnnumCnt" value=""/>
					
					<section id="contents">
						<!-- 타이틀 -->
						<div class="pHeader">
							<h1 class="tit">놀이공원 신청</h1>
						</div>
		
						<div class="pTab pTab2">
							<ul>
								<c:forEach var="aparkInfoVO" items="${aparkInfo}" varStatus="status">
                                     <li class="${aparkInfoVO.aparkNo== aparkNo ? 'fir on' : ''}">
                                     	<a href="${mainSSSlUrl}/hynix/aparkWrite.do?welfMnuNo=2211&aparkNo=${aparkInfoVO.aparkNo}" 
                                     		name="aparkNo" id="aparkNo" value="<c:out value="${aparkInfoVO.aparkNo}"/>">${aparkInfoVO.aparkNm}</a>
                                     </li>
                             	</c:forEach>
							</ul>
						</div>
		
						<ul class="pStep-indicator">
							<li class="on">1</li>
							<li>2</li>
						</ul>
		
						<!-- 놀이공원  조회  -->
						<div id="park-datepicker"></div>
			
					</section>
				</form>
				
				<!-- // 총사용가능인원수 -->
				<section class="pSec">
					<h2 class="titB">총 사용 가능 인원 수</h2>

					<div class="pInd">
						<div class="tbl">
							<table>
								<caption>총 사용 가능 인원 수</caption>
								<thead>
									<tr>
										<th>평일</th>
										<th>주말&공휴일</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<c:forEach var="aparkInfoVO" items="${aparkInfo}" varStatus="status">
											<c:if test="${aparkInfoVO.aparkNo== aparkNo}">
												<td>${aparkInfoVO.weekDayPrnnum}명/日</td>
												<td>${aparkInfoVO.weekEndPrnnum}명/日</td>
											</c:if>
										</c:forEach>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</section>
				<!-- // 총사용가능인원수 -->
				
				<!-- 접수마감시간 -->
				<section class="pSec">
					<h2 class="titB">접수마감시간</h2>

					<div class="pInd">
						<div class="tbl">
							<table>
								<caption>접수마감시간</caption>
								<colgroup>
									<col style="width: 14%;" />
									<col style="width: 18%;" />
									<col />
								</colgroup>
								<tbody>
									<tr class="rowFir">
										<th class="fir" scope="row">월요일</th>
										<td rowspan="4">놀이공원 이용 시</td>
										<td class="al">전주 목요일 오후 4시 이전</td>
									</tr>
									<tr>
										<th class="fir" scope="row">화~금요일</th>
										<td class="al">이용일 2일 이전</td>
									</tr>
									<tr>
										<th class="fir" scope="row">주말(토/일)</th>
										<td class="al">수요일 오후 4시 이전</td>
									</tr>
									<tr>
										<th class="fir" scope="row">휴일</th>
										<td class="al">휴일 시작 기준일 1주일 전 先 접수.(SK 하이웰 팝업창 공지로 미리 알려 드립니다)</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</section>
				<!--// 접수마감시간 -->
				
				<!-- // 신청상태 정보 -->
				<section class="pSec">
					<h2 class="titB">신청상태 정보</h2>

					<div class="pInd">
						<div class="tbl">
							<table>
								<caption>신청상태 정보</caption>
									<tr>
										<th>신청불가</th>
										<td>명절, 휴무, 이용접수기간 완료 등으로 신청 불가한 상태</td>
									</tr>
									<tr>
										<th>신청</th>
										<td>놀이공원 신청 가능한 상태</td>
									</tr><tr>
										<th>대기</th>
										<td>日 신청인원에 대한 신청이용정원이 초과한 상태로 대기자 신청 가능 상태</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</section>
				<!-- // 신청상태 정보 -->
				
				<!-- // 날씨 정보 안내 -->
				<section class="pSec">
					<h2 class="titB">날씨 정보 안내</h2>

					<div class="pInd">
						<ul class="bulArr">
							<li>놀이공원신청하기 전 날씨 정보를 확인해 보세요. <a href="http://www.weather.go.kr/weather/main.jsp" target="_blank" class="btM2">날씨 정보 확인하기<span class="btR"></span></a></li>
						</ul>
					</div>
				</section>
				<!-- // 날씨 정보 안내-->
				
				<!--// contents -->
			</div>
			<!--//  Contents Wrap -->
			<hr />
			<%@ include file="/hynix/front/include/inc_quick.jsp"%>
		</div>
		<!--// Container -->
	</div>
	<!--// containerFull -->
	<%@ include file="/hynix/front/include/inc_footer.jsp"%>
	
</body>
</html>