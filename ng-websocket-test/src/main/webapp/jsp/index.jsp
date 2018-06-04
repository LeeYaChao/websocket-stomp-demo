<%@ page contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	pageContext.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html>
<head>
	<title>Hello WebSocket</title>
	<script src="${basePath}/js/sockjs-0.3.4.js"></script>
	<script src="${basePath}/js/stomp.js"></script>
	<script src="${basePath}/js/jquery1x.js"></script>
	<script type="text/javascript">
		var stompClient = null;

		function setConnected(connected) {
			document.getElementById('connect').disabled                 = connected;
			document.getElementById('disconnect').disabled              = !connected;
			document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
			$('#systemDate').text('');
			$('#helloBroadcast').text('');
			$('#helloToUser').text('');
		}

		//建立连接
		function connect() {
			var name = $('#name').val();
			var socket  = new SockJS('${basePath}/ng-websocket');
			stompClient = Stomp.over(socket);
			stompClient.connect({user:name}, function (frame) {
				setConnected(true);
				console.log('Connected: ' + frame);
/*

                stompClient.subscribe('/topic/system-date', function(greeting) {
                    showSystemDate(greeting.body);
                });

                stompClient.subscribe('/user/queue/system-test', function(greeting) {
                    showSystemTest(greeting.body);
                });
*/

                stompClient.subscribe('/user/queue/reply', function(greeting) {
                    showGreeting(greeting.body);
                });
			});
		}

		function disconnect() {
			if (stompClient != null) {
				stompClient.disconnect();
			}
			setConnected(false);
			console.log("Disconnected");
		}

		function sendName() {
			var name = document.getElementById('name').value;
			var toUser = document.getElementById('toUser').value;
			var sendMessage = document.getElementById('sendMessage').value;

			var msg = JSON.stringify({
                'name': name,
                'toUser' :toUser,
				'sendMessageBody':sendMessage
            });
			$('#sendMessageBody').text(msg);

            stompClient.send("/app/hello", {
            }, msg);
		}

		function sendName1(){
		    $.get("/templateTest",function(){
		        console.log("dfd")
			})
		}
        function showSystemDate(message) {
            $('#showSystemDate').text(message)
        }

		function showSystemTest(message) {
			$('#showSystemTest').text(message)
		}

        function showGreeting(message) {
            $('#showGreeting').text(message)
        }

		$(document).ready(function(){

		});
	</script>
</head>
<body onload="disconnect()">
<div>
	<div>
		<label>输入您的姓名?</label><input type="text" id="name"/><br/>
		<button id="connect" onclick="connect();">Connect</button>
		<button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>

		<br>

		<br>
		<label>发送给?</label><input type="text" id="toUser"/>
		<button id="sendName" onclick="sendName();">Send</button><br/>
		<label>发送给1?</label><input type="text" id="toUser1"/>
		<button id="sendName1" onclick="sendName1();">Send</button><br/>
		<br/>发送服务器消息:<br/>
		<textarea id="sendMessage" style="width: 400px;height: 100px;"></textarea>

		<br/>发送服务器消息体:<br/>
		<textarea id="sendMessageBody" style="width: 400px;height: 100px;"></textarea>
	</div>

	<div id="conversationDiv">


		<br><br/>广播系统时间:<br/>
		<textarea id="showSystemDate" style="width: 400px;height: 100px;"></textarea>
		<br><br/>特定用户时间:<br/>
		<textarea id="showSystemTest" style="width: 400px;height: 100px;"></textarea>
		<br><br/>showGreeting:<br/>
		<textarea id="showGreeting" style="width: 400px;height: 100px;"></textarea>
	</div>
</div>
</body>
</html>