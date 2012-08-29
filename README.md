gajaba
======

Dynamic rule based load balancer


#############################################################################################################################################

How to build and run the project
=================================

Step 1 : Build the project
----------------------------

This project needs Java version 7 to run. Check your java version using the following command
java -version

If you have Java 7 installed in your computer, then move on to the next step to build the project

1. Go into the “gajaba” directory
	cd gajaba
2. Now you need to download all the dependencies and build the project using following command.
	mvn clean install -Pserver,agent,sample

This will create the following .jar files
	/gajaba/gajaba-server/target/server-1.0-jar-with-dependencies.jar
	/gajaba/sample-agent/target/agent-sample-1.0-jar-with-dependencies.jar


Step 2 : Run the server
---------------------------

Now that you have built the project, you can run the server using the following steps

1. First you need to copy the following .jars to the location (gajaba/gajaba-server/target) where server-1.0-jar-with-dependencies.jar has been created.
	gajaba/gajaba-group/lib/shoal-gms.jar
	gajaba/gajaba-group/lib/jxta.jar

2. Then go to gajaba/gajaba-server/target directory
	cd gajaba-server/target/

3. Run the following command to start the server
	$JAVA_HOME/bin/java -cp * org.gajaba.server.Main

Step 3 : Run the sample-agent
--------------------------------

Running the sample-agent is similar to running the server in the previous step

1. First you need to copy the following .jars to the location (gajaba/sample-agent/target) where server-1.0-jar-with-dependencies.jar has been created.
	gajaba/gajaba-group/lib/shoal-gms.jar
	gajaba/gajaba-group/lib/jxta.jar

2. Then go to gajaba/sample-agent/target directory
	cd gajaba/sample-agent/target

3. Run the following command to start the sample-agent
	$JAVA_HOME/bin/java -cp '*' org.gajaba.sample.agent.Main agentName
	(Here you can give any name for “agentName” in the above command)

#############################################################################################################################################