const baseUrl = 'http://localhost:8080'
 
export const http = (options = {}) =>{
	return new Promise((resolve,reject)=>{
	uni.request({
		url:baseUrl+options.url || '',
		method:options.type||'GET',
		data: options.data||{},
		header:options.header||{},
	}).then((response)=>{
		//处理请求
		resolve(response.data);
	}).catch(error=>{
		reject(error)
	})
	});
}

const get = (url,data,options={})=>{
	options.type = 'get';
	options.data = data;
	options.url = url;
	return http(options)
}

const post = (url,data,options = {})=>{
	options.type = 'post';
	options.data = data;
	options.url = url;
	return http(options)
}

const put = (url,data,options = {})=>{
	options.type = 'put';
	options.data = data;
	options.url = url;
	return http(options)
}

const upload = (parm) =>{
	return new Promise((resolve,reject)=>{
		uni.uploadFile({
			url:baseUrl+parm.url,
			filePath:parm.filePath,
			name:'file',
			formData:{
				openid:uni.getStorageSync('openid')
			},
			header:{
				
			},
			success:(res)=>{
				resolve(res.data);
			},
			fail:(error)=>{
				reject(error);
			}
		})
	})
}

export default{
	http,
	post,
	get,
	put,
	upload,
	baseUrl
}

	
