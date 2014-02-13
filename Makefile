CC = javac

all: compile

compile:
	rm -rf classes
	mkdir classes
	javac -d classes src/*.java

# chatServer: chatServer.java
# 	$(CC) chatServer.java

# chatClient: chatClient.java
# 	$(CC) chatClient.java

# copy:
# 	cp ~/Documents/workspace/cs490Project1/src/liveThread.java ./
# 	cp ~/Documents/workspace/cs490Project1/src/recieveChatMessage.java ./
# 	cp ~/Documents/workspace/cs490Project1/src/checkTime.java ./
# 	cp ~/Documents/workspace/cs490Project1/src/handleRequest.java ./
# 	cp ~/Documents/workspace/cs490Project1/src/users.java ./
# 	cp ~/Documents/workspace/cs490Project1/src/chatServer.java ./
# 	cp ~/Documents/workspace/cs490Project1/src/chatClient.java ./


clean:
	rm -rf classes