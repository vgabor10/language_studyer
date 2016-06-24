#!/bin/bash

if (( $# != 1 )); then
		echo "exactly one argument needed"
		echo "0 - terminal interface"
		echo "1 - graphic user interface"
	else
		cd Java
		java LanguageStudyer $1
		cd ..
fi
