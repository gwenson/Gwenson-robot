var prod={}
var host=window.location.host;
console.log(host.indexOf("gwenson.com"));
if(host.indexOf("gwenson.com")>-1){
	prod.localhost="http://www.gwenson.com";
}else{
	prod.localhost="http://localhost:8080";
}
