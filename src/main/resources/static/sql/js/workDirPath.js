/*require jquery*/
$(function(){
	getContentPath();
})
function getContentPath() {
	workDir = "";
	var docLocation = document.location.toString();
	//ie8中没有origin
	//var hostUrl = document.location.origin;
	var index = docLocation.lastIndexOf('/')+1;
	workDir = docLocation.substr(0,index);
}
