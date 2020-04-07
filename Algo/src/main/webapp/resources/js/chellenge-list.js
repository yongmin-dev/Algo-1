/**
 * 
 */
window.addEventListener('load',
e=>{
	const searchChallengeBtn = document.querySelector("#btn-search-challenge");
	console.log(searchChallengeBtn);
	searchChallengeBtn.addEventListener('click',
	e=>{
		const searchType = document.querySelector("#search-type");
		const keyword =document.querySelector("#input-search");
		const contextPath = getContextRootPath();
		console.log("search keyword:",searchType.value,keyword.value);
		location.href=`${contextPath}/challenge/list?page=${1}&type=${searchType.value}&keyword=${keyword.value}`;
	}
	);
});