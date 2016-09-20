d:
cd D:\Programme\java\workvaadin\
pause
rem mvn -e -B archetype:generate -DarchetypeGroupId=com.vaadin -DarchetypeArtifactId=vaadin-archetype-touchkit -DarchetypeVersion=7.7.1 -DgroupId=de.lichtmagnet -DartifactId=saft -Dversion=1.0-SNAPSHOT

mvn archetype:generate  -DarchetypeGroupId=com.vaadin    -DarchetypeArtifactId=vaadin-archetype-touchkit    -DarchetypeVersion=LATEST    -DgroupId=com.pany    -DartifactId=project-name    -Dversion=0.1    -Dpackaging=war

pause
cd vaadin-app
mvn package jetty:run
pause