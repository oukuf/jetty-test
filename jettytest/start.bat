echo off
cd %~dp0
copy .\target\jettytest.war .\
java -jar jettytest.war
