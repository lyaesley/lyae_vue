echo "jar copy start" 
cp -f $(pwd)/target/*.jar /home/lyae/dev/war/.
echo "jar copy end"
java -version
java -jar /home/lyae/dev/war/lyae-*.jar -Denv=prod