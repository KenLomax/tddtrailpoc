#!/bin/bash
rm ../../hybris-commerce-suite-6.2.0.1GO
unzip ../../hybris-commerce-suite-6.2.0.1.zip -d ../../hybris-commerce-suite-6.2.0.1
#cp -r ../../hybris-commerce-suite-6.2.0.1ORIG ../../hybris-commerce-suite-6.2.0.1
cd ../../hybris-commerce-suite-6.2.0.1/Installer; ./install.sh -r b2b_acc
cd ../hybris/bin/platform;  . ./setantenv.sh; ant extgen -Dinput.template="yempty" -Dinput.name="tddtrail" -Dinput.package="com.hybris.tddtrail"
sed -i '' 's/<\/extensions>/<extension dir="..\/custom\/tddtrail"\/><\/extensions>/' ../../config/localextensions.xml
ant clean all


pwd
ls
