/**
 * 
 */
var webSocket;
var displayName;
var mobileNumber;

function join() {
	displayName = disName.value;
	mobileNumber = mobNum.value;
	document.getElementById("formm").style.display="none";
	document.getElementById("secondFull").style.display="flex";
	window.alert("Welcome "+displayName);
}
function roomId() {
var id;
if(mobileNumber<roomID.value) {
	id = mobileNumber+roomID.value;
}
else {
	id = roomID.value + mobileNumber;
}
console.log(id);
	webSocket = new WebSocket("ws:"+window.location.hostname+":8080/WebsocketDemo/demo/"+ id);
	webSocket.onmessage = (message) =>{
			console.log(message);
			var div = document.createElement("div");
			div.setAttribute("class","msg");
			div.innerText=message.data.slice(10,message.length);
			textArea.append(div);
			console.log(message.data.slice(0,10));
			if(message.data.slice(0,10)==mobileNumber) {
				div.style.float = "right";
				div.style.backgroundColor = "#0070DA";
			}else {
				div.style.float = "left";
				div.style.backgroundColor ="#3E4648";
			}
			textArea.innerHTML+="<br><br><br><br><br><br>";
		}
}

function sendMessage() {
	if(webSocket!=null && text.value!="") {
		webSocket.send(mobileNumber+text.value);
		text.value="";
	}
}