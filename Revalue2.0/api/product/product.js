import http from "../../common/http.js";

export const getallProduct = ()=>{
	return http.get('/api/product/all')
}

export const queryProductApi = (id) =>{
	return http.get(`/api/product/${id}`)
}

export const getallTags = ()=>{
	return http.get('/api/product/tags')
}
