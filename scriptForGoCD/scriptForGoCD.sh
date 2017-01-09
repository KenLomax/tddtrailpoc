#!/bin/bash


# Try getting with 
# awk -F "</*code>" '/<\/*code>.*[a-z]/ {print $2}' ../src/main/webapp/index.html | bash
# mvn -Dtest=TrailSetupTest#testTddTrailPrerequisites test


#Configure the Suite with a recipe
rm -rf ~/Trail/hybris-commerce-suite-6.2.0.1
unzip ~/Trail/hybris-commerce-suite-6.2.0.1.zip -d ~/Trail/hybris-commerce-suite-6.2.0.1
cd ~/Trail/hybris-commerce-suite-6.2.0.1/Installer; ./install.sh -r b2b_acc setup

#Initialize the commerce suite with the B2B Accelerator Recipe
./install.sh -r b2b_acc initialize; ./install.sh -r b2b_acc start

#Create a new extension
cd ../hybris/bin/platform;  . ./setantenv.sh; ant extgen -Dinput.template="yempty" -Dinput.name="tddtrail" -Dinput.package="com.hybris.tddtrail"

#Add the new extension to your Suite
sed -i '' 's/<\/extensions>/<extension dir="..\/custom\/tddtrail"\/><\/extensions>/' ../../config/localextensions.xml
ant clean all
pwd
ls
