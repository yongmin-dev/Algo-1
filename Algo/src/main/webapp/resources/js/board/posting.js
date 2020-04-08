window.addEventListener("load", init, false);

function init() {
	// 쓰기

	let writeBtn = document.getElementById("writeBtn");
	let cancelBtn = document.getElementById("cancelBtn");
	let listBtn = document.getElementById("listBtn");
	
	
	writeBtn.addEventListener("click", function(e) {
		e.preventDefault();
		document.getElementById("boardPostVO").submit();
	}, false);

	// 취소
	cancelBtn.addEventListener("click", function(e) {
		e.preventDefault();
		document.getElementById("boardPostVO").reset();
	}, false);

	// 목록
	listBtn.addEventListener("click", function(e) {
		let returnPage = e.target.getAttribute('data-returnPage');
		e.preventDefault();
		location.href = getContextPath() + "/board/list/" + returnPage;
	}, false);
}