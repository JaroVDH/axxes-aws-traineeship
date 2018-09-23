#!/bin/bash
sudo yum -y install java-1.8.0
sudo yum -y remove java-1.7.0-openjdk
aws s3 sync s3://traineeship-axxes-aws/app /home/ec2-user
java -jar /home/ec2-user/traineeship-aws-app-0.0.1-SNAPSHOT.jar