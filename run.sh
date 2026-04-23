echo "Starting. Please wait..."
mvn -q compile
mvn -q exec:java -Dexec.mainClass="se.yrgo.client.SimpleClient"
echo "Done!"
