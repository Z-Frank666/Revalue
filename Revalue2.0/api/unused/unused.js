import http from "../../common/http.js";

export const getallUnused = ()=>{
	return http.get('/api/unused/all')
}

export const queryUnusedApi = (id) =>{
	return http.get(`/api/unused/${id}`)
}

export const getallTags = ()=>{
	return http.get('/api/unused/tags')
}

export const saveUnused = (parm)=>{
	return http.put('/api/unused/add',parm)
}

export const saveImage = (parm)=>{
	return http.put('/api/unused/addimg',parm)
}

export const getShowImg = ()=>{
	return http.get('/api/unused/showImg')
}

export const getImgs = (id)=>{
	return http.get(`/api/unused/detail/imgs/${id}`)
}

export const getDetail = (id)=>{
	return http.get(`/api/unused/detail/message/${id}`)
}

export const collect = (parm)=>{
	return http.put('/api/unused/detail/collect',parm)
}

export const incollect = (parm)=>{
	return http.post('/api/unused/detail/incollect',parm)
}

export const checkcollect = (parm)=>{
	return http.post('/api/unused/detail/checkcollect',parm)
}

export const quickdeal = (userId,itemId)=>{
	return http.put(`/api/unused/detail/deal/${userId}/${itemId}`)
}

export const unusedSearch =(keyword)=>{
	return http.post(`/api/unused/search/${keyword}`)
}
