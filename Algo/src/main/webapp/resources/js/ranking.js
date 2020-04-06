/**
 * 
 */

window.addEventListener("load",
e=>{
	const searchRankBtn = document.querySelector("#btn-search-rank");
	console.log(searchRankBtn );
	if(searchRankBtn){
		const contextPath = getContextRootPath();
		const cNum = searchRankBtn.getAttribute("data-cnum");
		searchRankBtn.addEventListener('click',
		e=>{
			const type = document.querySelector("input[type='radio'][name='type']:checked").value;
			location.href=`${contextPath}/ranking/${cNum}/1?type=${type}`;
		}
		);
		console.log(`${contextPath}/ranking/${cNum}/1?type=${type}`);		
	}
}
);
