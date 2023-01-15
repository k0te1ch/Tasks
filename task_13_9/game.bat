@echo off

set JAVA_HOME=C:\Program Files\Java\jdk-16.0.1

set JAVAW=javaw
if not "%JAVA_HOME%"=="" (
  set JAVAW="%JAVA_HOME%\bin\%JAVAW%"
)

start "" %JAVAW% -Dfile.encoding=UTF-8 -classpath "D:\Programming\Java\Tasks\out\production\task_13_9" Main
