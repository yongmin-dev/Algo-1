window.addEventListener("load", init, false);

function init() {
	changeMode(false);

	let modifyBtn = document.getElementById("modifyBtn");
	let cancelBtn = document.getElementById("cancelBtn");
	let replyBtn = document.getElementById("replyBtn");
	let listBtn = document.getElementById("listBtn");

	// 답글달기 버튼
	replyBtn.addEventListener("click", function(e) {
		e.preventDefault();
		console.log("답글");
		let returnPage = e.target.getAttribute('data-returnPage');
		let postNum = e.target.getAttribute('data-postNum');
		location.href = getContextPath() + "/board/reply/" + postNum + "/"
				+ returnPage;
	}, false);

	// 수정하기 버튼

	modifyBtn.addEventListener("click", function(e) {
			e.preventDefault();
			changeMode(true);
		}, false)

	
	// 삭제하기 버튼

		deleteBtn.addEventListener("click",
				function(e) {
					e.preventDefault();
					if (confirm("삭제하시겠습니까?")) {
					
						let postNum = e.target.getAttribute("data-postNum");
						let returnPage = e.target.getAttribute("data-returnPage");
						console.log("returnPage" + returnPage);
						console.log("postNum" + postNum);						
						location.href = getContextPath() + "/board/delete/" + postNum + "/"+ returnPage;
					}
				}, false)

	
	// 취소버튼(수정모드에서 읽기로 전환)
	cancelBtn.addEventListener("click", function(e){
		e.preventDefault();
		console.log('취소');
		changeMode("false");
	},false);
	
	// 저장하기
	saveBtn.addEventListener("click", function(e){
		e.preventDefault();
		console.log('저장');
		
		document.getElementById('boardPostVO').submit();
	},false);

	// 목록보기
	listBtn.addEventListener("click", function(e){
		e.preventDefault();
		console.log('목록');
		let returnPage = e.target.getAttribute('data-returnPage');
		location.href = getContextPath() + "/board/list/" + returnPage;
	},false);
	
	// 읽기모드, 수정모드
	
	function changeMode(flag){
		let rmodes = document.getElementsByClassName("rmode");
		let umodes = document.getElementsByClassName("umode");
		
		// 수정모드
		if(flag){
//			document.getElementById("boardTitle").textContent = "게시글 수정";
			document.getElementById("category.categoryNum").removeAttribute("disabled");
			document.getElementById("title").removeAttribute("readOnly");
			document.getElementById("content").removeAttribute("readOnly");
			// 수정모드 버튼 활성화
			Array.from(rmodes).forEach(e=>{e.style.display="none";});
			Array.from(umodes).forEach(e=>{e.style.display="block";});
			
		// 읽기 모드
		}else{
//			document.getElementById("boardTitle").textContent = "게시글 보기";
			document.getElementById("category.categoryNum").setAttribute("disabled",true);
			document.getElementById("title").setAttribute("readOnly",true);
			document.getElementById("content").setAttribute("readOnly", true);
			
			Array.from(rmodes).forEach(e=>{e.style.display="block";});
			Array.from(umodes).forEach(e=>{e.style.display="none";});
			
		}
		
		
	}
	
	//첨부파일 1건 삭제 : ajax 구현
	let fileList = document.getElementById("fileList");
	
	
	
	
	
	
	
	
	
	
	
}