<template>
  <div id="main-content" class="container">
  <div class="row">
    <div class="col-md-6">
      <form class="form-inline">
        <div class="form-group">
          <label for="connect">WebSocket connection:</label>
          <button id="connect" class="btn btn-default" type="submit">Connect</button>
          <button id="disconnect" class="btn btn-default" type="submit" disabled="disabled">Disconnect
          </button>
        </div>
      </form>
    </div>
    <div class="col-md-6">
      <form class="form-inline">
        <div class="form-group">
          <label for="name">What is your name?</label>
          <input type="text" id="name" class="form-control" placeholder="Your name here...">
          <div class="form-group">
            <label for="message">Input Message</label>
            <input type="text" id="chatMessage" class="form-control" placeholder="message.." />
          </div>
          <button id="chatSend" class="btn btn-default" type="button">Chat Send</button>
        </div>
        <button id="send" class="btn btn-default" type="submit">Send</button>
      </form>
    </div>
  </div>
  <div class="row">
    <div class="col-md-12">
      <table id="conversation" class="table table-striped">
        <thead>
        <tr>
          <th>Greetings</th>
        </tr>
        </thead>
        <tbody id="greetings">
        </tbody>
      </table>
    </div>
  </div>
</div>
</template>

<script>
import $ from "jquery";

export default {
  data() {
    return {
      stompClient : null
    }
  },
  methods: {
    setConnected(connected) {
      this.connect.prop("disabled", connected);
      this.disconnect.prop("disabled", !connected);
      if (connected) {
        this.conversation.show();
      }
      else {
        this.conversation.hide();
      }
      this.greetings.html("");
    },
    connect() {
      var socket = new SockJS('/websocket');
      this.stompClient = this.Stomp.over(socket);
      // SockJS와 stomp client를 통해 연결을 시도.
      this.stompClient.connect({}, function (frame) {
        if (this.setConnected()) {
          console.log('Connected: ' + frame);
          this.stompClient.subscribe('/topic/greetings', function (greeting) {
            this.showGreeting(JSON.parse(greeting.body).content);
          });
          this.stompClient.subscribe('/topic/chat', function (chat) {
          this.showChat(JSON.parse(chat.body));
          });
        }
      });
    },
    disconnect() {
      if (this.stompClient !== null) {
        this.stompClient.disconnect();
      }
      this.setConnected(false);
      console.log("Disconnected");
    },
    sendName() {
      // /app/hello로 JSON 파라미터를 메세지 body로 전송.
      this.stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    },
    showGreeting() {
      this.greetings.append("<tr><td>" + this.message + "</td></tr>");
    },
    sendChat() {
	    this.stompClient.send("/app/chat", {}, JSON.stringify({'name': $("#name").val(), 'message': $("#chatMessage").val()}));
    },
    showChat(chat) {
      this.greetings.append("<tr><td>" + chat.name + " : " + chat.message + "</td></tr>");
    }
  },
  function() {
    this.form.on('submit', function(e) {
      this.e.preventDefault()
    })
    this.connect.click(this.connect())
    this.disconnect.click(this.disconnect())
    this.send.click(this.sendName())
    this.chatSend.click(this.sendChat())
  }
}
</script>
