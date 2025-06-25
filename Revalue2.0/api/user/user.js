import http from "../../common/http.js";

export const registerApi = (parm)=>{
	return http.post('/api/user/register',parm)
}

export const loginApi = (parm)=>{
	return http.post('/api/user/login',parm)
}

export const queryUserApi = (id) =>{
	return http.get(`/api/user/${id}`)
}

export const updateUserApi = (user) =>{
	return http.put('/api/user/update', user);
}