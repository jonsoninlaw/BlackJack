#!/usr/bin/env bash
clear
javac -cp :lib/* PlayFx.java 2> error.log
if [[ $(< error.log) != "" ]]; then
    cat error.log
else
    java -cp :lib/* --module-path lib --add-modules javafx.controls PlayFx &
fi