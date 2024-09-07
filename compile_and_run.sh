if ! which mvn > /dev/null 2>&1; then
  echo "Maven is not installed on your machine."
  echo "Install maven to compile and run this project."
  exit 1
fi

if ! mvn clean compile package; then
  exit 1
fi

target_name=$(grep "artifactId" pom.xml                   \
  | head -n 1                                             \
  | sed 's/.*<artifactId>\([a-zA-Z]*\)<\/artifactId>/\1/' \
  )
target_version=$(grep "version" pom.xml                   \
  | head -n 1                                             \
  | sed 's/.*<version>\([-0-9a-zA-Z.]*\)<\/version>/\1/'  \
  )
target_file="$target_name-$target_version.jar"

java -jar target/$target_file