cd /Users/d061192/trail
unzip hybris-commerce-suite-6.2.0.1.zip -d hybris-commerce-suite-6.2.0.1
cd /Users/d061192/trail/TddTrail
mvn -Dtest=TrailSetupTest#testTddTrailPrerequisites test|| { echo "Test testTddTrailPrerequisites Unsuccessful" }
