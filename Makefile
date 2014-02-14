CC = javac

all:copy chatServer chatClient

chatServer: chatServer.java
	$(CC) chatServer.java

chatClient: chatClient.java
	$(CC) chatClient.java

copy:
	cp ~/Documents/workspace/cs490Project1/src/liveThread.java ./	
	cp ~/Documents/workspace/cs490Project1/src/recieveChatMessage.java ./
	cp ~/Documents/workspace/cs490Project1/src/checkTime.java ./
	cp ~/Documents/workspace/cs490Project1/src/remoteUser.java ./
	cp ~/Documents/workspace/cs490Project1/src/users.java ./
	cp ~/Documents/workspace/cs490Project1/src/chatServer.java ./
	cp ~/Documents/workspace/cs490Project1/src/chatClient.java ./


clean:
	rm *.class