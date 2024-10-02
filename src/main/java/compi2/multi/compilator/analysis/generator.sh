echo "   Jflex generating"
#java -jar /home/blue-dragon/Documents/compilator_java_resources/jflex.jar jflex.jflex
java -jar /home/blue-dragon/Documents/compilator_java_resources/jflex-full-1.9.1.jar jflex.jflex

echo "\n    Cup generating"
java -jar /home/blue-dragon/Documents/compilator_java_resources/java-cup-11b.jar -parser Parser parser.cup 

