<template>
  <div class="chatbot">
    <div class="messages" ref="messagesContainer">
      <div
        v-for="(msg, index) in messages"
        :key="index"
        :class="['message', msg.sender === 'You' ? 'you' : 'bot']"
      >
        <div class="bubble">
          <span>{{ msg.text }}</span>
        </div>
      </div>
      <div v-if="botTyping" class="message bot">
        <div class="bubble typing">
          <span class="dot"></span>
          <span class="dot"></span>
          <span class="dot"></span>
        </div>
      </div>
    </div>

    <div class="input-area">
      <input
        v-model="inputMessage"
        @keyup.enter="sendMessage"
        placeholder="Écris un message..."
        :disabled="!wsConnected"
      />
      <button @click="sendMessage" :disabled="!wsConnected || inputMessage.trim() === ''">Envoyer</button>
    </div>

    <div v-if="!wsConnected" class="error">Connexion perdue. Reconnexion en cours...</div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, nextTick } from 'vue';

interface ChatMessage {
  sender: 'You' | 'Bot';
  text: string;
}

const props = defineProps<{
  bookTitle?: string;
}>();

const messages = ref<ChatMessage[]>([]);
const inputMessage = ref<string>('');
const botTyping = ref<boolean>(false);
const wsConnected = ref<boolean>(false);
let ws: WebSocket | null = null;
let reconnectTimeout: number | null = null;

const messagesContainer = ref<HTMLDivElement | null>(null);

function scrollToBottom(): void {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTo({
        top: messagesContainer.value.scrollHeight,
        behavior: 'smooth'
      });
    }
  });
}

function connectWebSocket(): void {
  ws = new WebSocket('ws://localhost:8080/ws/chat');

  ws.onopen = () => {
    console.log('WebSocket connected');
    wsConnected.value = true;
  };

  ws.onmessage = (event: MessageEvent<string>) => {
    botTyping.value = false;
    messages.value.push({ sender: 'Bot', text: event.data });
    scrollToBottom();
  };

  ws.onclose = () => {
    console.log('WebSocket disconnected');
    wsConnected.value = false;
    botTyping.value = false;
    reconnectTimeout = window.setTimeout(connectWebSocket, 3000);
  };

  ws.onerror = (err: Event) => {
    console.error('WebSocket error', err);
    ws?.close();
  };
}

function sendMessage(): void {
  if (!inputMessage.value.trim() || !wsConnected.value || !ws) return;

  messages.value.push({
    sender: 'You',
    text: inputMessage.value
  });
  scrollToBottom();

  botTyping.value = true;
  ws.send("Please answer in french Context : Book is " + props.bookTitle + " user question is : " + inputMessage.value);
  inputMessage.value = '';
}

onMounted(() => {
  messages.value.push({
    sender: 'Bot',
    text: 'Des questions à propos de ce livre ? Posez-les moi !'
  });

  connectWebSocket();
});

onBeforeUnmount(() => {
  ws?.close();
  if (reconnectTimeout) clearTimeout(reconnectTimeout);
});
</script>

<style scoped>
.chatbot {
  width: 400px;
  max-width: 90vw;
  border: 1px solid #ddd;
  border-radius: 12px;
  display: flex;
  flex-direction: column;
  font-family: 'Helvetica Neue', Arial, sans-serif;
  background: #f9f9f9;
  overflow: hidden;
}

.messages {
  flex: 1;
  padding: 10px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 8px;
  background: #e5ddd5;
}

.message {
  display: flex;
}

.message.you {
  justify-content: flex-end;
}

.message.bot {
  justify-content: flex-start;
}

.bubble {
  max-width: 75%;
  padding: 10px 14px;
  border-radius: 20px;
  line-height: 1.4;
  word-wrap: break-word;
}

.message.you .bubble {
  background-color: #0084ff;
  color: white;
  border-bottom-right-radius: 2px;
  border-bottom-left-radius: 20px;
}

.message.bot .bubble {
  background-color: #f1f0f0;
  color: #000;
  border-bottom-left-radius: 2px;
  border-bottom-right-radius: 20px;
}

.bubble.typing {
  font-style: italic;
  color: #555;
}

.input-area {
  display: flex;
  padding: 10px;
  border-top: 1px solid #ddd;
  gap: 8px;
  background: #fff;
}

input {
  flex: 1;
  padding: 10px 14px;
  border-radius: 20px;
  border: 1px solid #ccc;
  outline: none;
  font-size: 14px;
}

input:disabled {
  background: #eee;
}

button {
  padding: 10px 16px;
  border-radius: 20px;
  border: none;
  background-color: #0084ff;
  color: white;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.2s;
}

button:disabled {
  background-color: #999;
  cursor: not-allowed;
}

button:hover:not(:disabled) {
  background-color: #006bbf;
}

.error {
  color: red;
  font-size: 0.85em;
  padding: 5px 10px;
  background: #ffe5e5;
}

.bubble.typing {
  display: flex;
  gap: 4px;
  align-items: center;
  height: 20px;
}

.dot {
  width: 6px;
  height: 6px;
  background-color: #555;
  border-radius: 50%;
  animation: blink 1.4s infinite both;
}

.dot:nth-child(1) { animation-delay: 0s; }
.dot:nth-child(2) { animation-delay: 0.2s; }
.dot:nth-child(3) { animation-delay: 0.4s; }

@keyframes blink {
  0%, 80%, 100% { opacity: 0; }
  40% { opacity: 1; }
}
</style>
