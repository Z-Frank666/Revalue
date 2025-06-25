import http from "../../common/http.js";

export const getallTags = ()=>{
	return http.get('/api/buy/tags')
}

export const saveBuy = (parm)=>{
	return http.put('/api/buy/add',parm)
}

export const saveBuyImage = (parm)=>{
	return http.put('/api/buy/addimg',parm)
}

export const getallBuy = ()=>{
	return http.get('/api/buy/all')
}

export const queryBuyApi = (id) =>{
	return http.get(`/api/buy/${id}`)
}

export const getImgCnt = (id) =>{
	return http.get(`/api/buy/imgcnt/${id}`)
} 

export const showAll =()=>{
	return http.post('/api/buy/showall')
}

export const showBySort=(id)=>{
	return http.post(`/api/buy/showbysort/${id}`)
}

export const queryDetail = (id)=>{
	return http.get(`/api/buy/detail/${id}`)
}

export const queryItem = (id)=>{
	return http.get(`/api/buy/item/${id}`)
}

export const addComment = (parm)=>{
	return http.put('/api/buy/comment/add',parm)
}

export const showcomment = (id)=>{
	return http.get(`/api/buy/comment/show/${id}`)
}

export const getImgList = (id)=>{
	return http.get(`/api/buy/detail/imglist/${id}`)
}

export const buySearch =(keyword)=>{
	return http.post(`/api/buy/search/${keyword}`)
}