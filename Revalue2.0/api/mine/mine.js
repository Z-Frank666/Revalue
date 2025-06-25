import http from "../../common/http.js";

export const myCollections = (id)=>{
	return http.get(`/api/mine/getcollection/${id}`)
}

export const incollectById = (id)=>{
	return http.put(`/api/mine/incollect/${id}`)
}

export const myUnuseds = (id)=>{
	return http.get(`/api/mine/getunused/${id}`)
}

export const myBuys = (id)=>{
	return http.get(`/api/mine/getbuy/${id}`)
}

export const queryBuyDetail = (id)=>{
	return http.get(`/api/mine/getbuydetail/${id}`)
}

export const queryUnusedDetail = (id)=>{
	return http.get(`/api/mine/getunuseddetail/${id}`)
}

export const updateBuy = (parm)=>{
	return http.post('/api/mine/updatebuy',parm)
}

export const deleteBuy = (id)=>{
	return http.post(`/api/mine/deletebuy/${id}`)
}

export const hidebuy = (id)=>{
	return http.post(`/api/mine/hidebuy/${id}`)
}

export const updateUnused = (parm)=>{
	return http.post('/api/mine/updateunused',parm)
}

export const deleteUnused = (id)=>{
	return http.post(`/api/mine/deleteunused/${id}`)
}

export const hideUnused = (id)=>{
	return http.post(`/api/mine/hideunused/${id}`)
}

export const getpayorder = (id)=>{
	return http.get(`/api/mine/payorder/${id}`)
}

export const getsaleorder = (id)=>{
	return http.get(`/api/mine/saleorder/${id}`)
}

export const payorderDetail = (id)=>{
	return http.get(`/api/mine/payorderdetail/${id}`)
}

export const saleorderDetail = (id)=>{
	return http.get(`/api/mine/saleorderdetail/${id}`)
}

export const resetpassword = (parm)=>{
	return http.post('/api/mine/reset',parm)
}